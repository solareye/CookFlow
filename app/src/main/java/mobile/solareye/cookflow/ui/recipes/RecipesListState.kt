package mobile.solareye.cookflow.ui.recipes

import mobile.solareye.cookflow.data.recipe_detail.RecipeDetailItem

data class RecipesListState(
    val recipes: List<RecipeDetailItem> = emptyList(),
    val isLoading: Boolean = false,
    val isPtrLoading: Boolean = false,
    val error: String? = null,
) {
    companion object {
        fun initialState() = RecipesListState()
    }
}
