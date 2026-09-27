package mobile.solareye.cookflow.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import mobile.solareye.cookflow.Actions
import mobile.solareye.cookflow.RecipeDetail
import mobile.solareye.cookflow.RecipeList
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
            val backStack = rememberNavBackStack(RecipeList)
            val actions = remember(backStack) { Actions(backStack) }
            val viewModel = remember { recipeListViewModel(actions) }

            NavDisplay(
                backStack = backStack,
                onBack = actions.navigateBack,
                entryProvider = entryProvider {
                    entry<RecipeList> {
                        MaterialTheme {
                            RecipesScreen.RecipesScreen(
                                stateLiveData = viewModel.state,
                                dispatchIntent = viewModel::onViewIntent
                            )
                        }
                    }
                    entry<RecipeDetail> { route ->
                        MaterialTheme {
                            RecipeDetailScreen.RecipeDetailScreen(
                                recipeId = route.recipeId,
                                navigateBack = actions.navigateBack
                            )
                        }
                    }
                }
            )
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
