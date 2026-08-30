package mobile.solareye.cookflow.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import mobile.solareye.cookflow.Actions
import mobile.solareye.cookflow.Destinations.RecipeDetail
import mobile.solareye.cookflow.Destinations.RecipeDetailArgs.RecipeId
import mobile.solareye.cookflow.Destinations.RecipeList
import mobile.solareye.cookflow.data.api.NetworkDataSourceProvider
import mobile.solareye.cookflow.domain.coroutine.CoroutineDispatchersImpl
import mobile.solareye.cookflow.domain.coroutine.UiScope
import mobile.solareye.cookflow.repository.RecipeListRepositoryImpl
import mobile.solareye.cookflow.ui.recipes.RecipeListViewModel
import mobile.solareye.cookflow.ui.recipes.RecipeListViewModelFactory

class MainActivity : ComponentActivity() {

    private val uiScope = UiScope()
    private val repository by lazy {
        RecipeListRepositoryImpl(NetworkDataSourceProvider.networkDataSource)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val actions = remember(navController) { Actions(navController) }
            val viewModel = remember { recipeListViewModel(actions) }

            NavHost(
                navController = navController,
                startDestination = RecipeList
            ) {
                composable(RecipeList) {
                    MaterialTheme {
                        RecipesScreen.RecipesScreen(
                            stateLiveData = viewModel.state,
                            dispatchIntent = viewModel::onViewIntent
                        )
                    }
                }
                composable(
                    "${RecipeDetail}/{$RecipeId}",
                    arguments = listOf(navArgument(RecipeId) { type = NavType.StringType })
                ) { backStackEntry ->
                    MaterialTheme {
                        RecipeDetailScreen.RecipeDetailScreen(
                            recipeId = backStackEntry.arguments?.getString(RecipeId) ?: "-1",
                            navigateBack = actions.navigateBack
                        )
                    }
                }
            }

        }
    }

    private fun recipeListViewModel(actions: Actions): RecipeListViewModel {
        val dispatchers = CoroutineDispatchersImpl()
        return RecipeListViewModelFactory(uiScope, dispatchers, repository, actions.openRecipe)
            .create(RecipeListViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        // fixme coroutine scope for each screen?
        uiScope.destroy()
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainActivityPreview() {

}
