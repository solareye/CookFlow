package mobile.solareye.cookflow.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import mobile.solareye.cookflow.R
import mobile.solareye.cookflow.data.model.RecipeCategory
import mobile.solareye.cookflow.data.model.RecipeListItem
import mobile.solareye.cookflow.ui.recipes.RecipeListIntent
import mobile.solareye.cookflow.ui.recipes.RecipesListState

object RecipesScreen {
    @Composable
    fun RecipesScreen(
        stateLiveData: LiveData<RecipesListState>,
        // fixme might be replaced with channel/subject
        dispatchIntent: (RecipeListIntent) -> Unit,
    ) {
        val state = stateLiveData.observeAsState(RecipesListState.initialState()).value
        LaunchedEffect(Unit) {
            dispatchIntent(RecipeListIntent.LoadInitialPageIntent)
        }

        Scaffold(modifier = Modifier.systemBarsPadding()) { contentPadding ->
            Box(modifier = Modifier.fillMaxSize().padding(contentPadding)) {
                when {
                    state.isLoading -> ListLoading()
                    state.error != null -> ListError(state.error)
                    else -> SimpleList(state.recipes) { recipe ->
                        dispatchIntent(RecipeListIntent.OpenRecipeIntent(recipe))
                    }
                }
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
        recipes: List<RecipeListItem>,
        openRecipe: (RecipeListItem) -> Unit
    ) {
        val scrollState = rememberLazyListState()
        val categories = remember(recipes) { recipes.groupBy { it.category } }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = scrollState
        ) {
            for ((category, categoryRecipes) in categories) {
                item(key = "category:${category.name}") {
                    CategoryHeader(category, categoryRecipes.size)
                }
                items(categoryRecipes, key = { "${category.name}:${it.id}" }) { recipe ->
                    SimpleItem(recipe, openRecipe)
                }
            }
        }
    }

    @Composable
    private fun CategoryHeader(category: RecipeCategory, count: Int) {
        val title = when (category) {
            RecipeCategory.GENERAL -> R.string.recipe_category_general
            RecipeCategory.COFFEE -> R.string.recipe_category_coffee
        }
        Column(modifier = Modifier.padding(horizontal = 32.dp, vertical = 24.dp)) {
            Text(
                text = stringResource(title),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.semantics { heading() },
            )
            Text(
                text = stringResource(R.string.recipe_count, count),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }

    @Composable
    private fun SimpleItem(recipe: RecipeListItem, openRecipe: (RecipeListItem) -> Unit) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .then(
                if (recipe.canOpenDetails) Modifier.clickable { openRecipe(recipe) }
                else Modifier
            )) {
            Text(
                text = recipe.name,
                style = TextStyle(
                    fontSize = 20.sp,
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
    MaterialTheme {
        RecipesScreen.SimpleList(
            listOf(
                RecipeListItem(
                    id = "coffee-preview",
                    name = "Бразилия Элинир в Капельной Кофеварке",
                    category = RecipeCategory.COFFEE,
                    canOpenDetails = false,
                ),
            )
        ) {}
    }
}
