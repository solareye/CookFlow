package mobile.solareye.cookflow

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object RecipeList : NavKey

@Serializable
data class RecipeDetail(val recipeId: String) : NavKey

class Actions(private val backStack: MutableList<NavKey>) {
    val openRecipe: (String) -> Unit = { recipeId ->
        backStack.add(RecipeDetail(recipeId))
    }
    val navigateBack: () -> Unit = {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }
}
