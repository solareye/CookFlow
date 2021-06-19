package mobile.solareye.cookflow.ui.recipes

import mobile.solareye.cookflow.data.recipe_detail.RecipeDetailItem

sealed class RecipeListIntent {
    object LoadInitialPageIntent : RecipeListIntent()
    object PullToRefreshIntent : RecipeListIntent()
    class OpenRecipeIntent(val recipe: RecipeDetailItem) : RecipeListIntent()
}