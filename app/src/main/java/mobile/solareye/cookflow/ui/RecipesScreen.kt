package mobile.solareye.cookflow.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import mobile.solareye.cookflow.data.MockDataSource
import mobile.solareye.cookflow.data.recipe_detail.RecipeDetailItem
import mobile.solareye.cookflow.ui.recipes.RecipeListIntent
import mobile.solareye.cookflow.ui.recipes.RecipesListState

object RecipesScreen {
    @Composable
    fun RecipesScreen(
        stateLiveData: LiveData<RecipesListState>,
        // fixme might be replaced with channel/subject
        dispatchIntent: (RecipeListIntent) -> Unit,
    ) {
        Scaffold {
            val initialState = remember {
                dispatchIntent(RecipeListIntent.LoadInitialPageIntent)
                RecipesListState.initialState()
            }

            val state = stateLiveData.observeAsState(initialState).value
            if (state.recipes.isNotEmpty()) {
                SimpleList(state.recipes) { recipe ->
                    dispatchIntent(
                        RecipeListIntent.OpenRecipeIntent(
                            recipe
                        )
                    )
                }
            }
            if (state.isLoading) {
                ListLoading()
            }
            if (state.error?.isNotEmpty() == true) {
                ListError(state.error)
            }
        }
    }

    @Composable
    private fun ListLoading() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }

    @Composable
    private fun ListError(message: String) {
        Column(
            modifier = Modifier
                .padding(vertical = 64.dp, horizontal = 24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = message)
            }
        }
    }

    @Composable
    internal fun SimpleList(
        recipes: List<RecipeDetailItem>,
        openRecipe: (RecipeDetailItem) -> Unit
    ) {
        val scrollState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = scrollState
        ) {
            for (recipe in recipes) {
                item {
                    SimpleItem(recipe, openRecipe)
                }
            }
        }
    }

    @Composable
    private fun SimpleItem(recipe: RecipeDetailItem, openRecipe: (RecipeDetailItem) -> Unit) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { openRecipe(recipe) }) {
            Text(
                text = "Рецепт #${recipe.id}",
                style = TextStyle(
                    fontSize = 24.sp,
                ),
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 8.dp),
            )
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun RecipesScreenPreview() {
    RecipesScreen.SimpleList(MockDataSource.recipeList()) { println("Clicked #$it") }
}