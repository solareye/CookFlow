package mobile.solareye.cookflow.ui.recipes

import mobile.solareye.cookflow.data.model.RecipeListItem

sealed class RecipeListIntent {
    object LoadInitialPageIntent : RecipeListIntent()
    object PullToRefreshIntent : RecipeListIntent()
    class OpenRecipeIntent(val recipe: RecipeListItem) : RecipeListIntent()
}
