package mobile.solareye.cookflow.ui.recipes

import mobile.solareye.cookflow.data.model.RecipeListItem

data class RecipesListState(
    val recipes: List<RecipeListItem> = emptyList(),
    val isLoading: Boolean = false,
    val isPtrLoading: Boolean = false,
    val error: String? = null,
) {
    companion object {
        fun initialState() = RecipesListState()
    }
}
