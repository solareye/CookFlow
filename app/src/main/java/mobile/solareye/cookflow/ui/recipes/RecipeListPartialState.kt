package mobile.solareye.cookflow.ui.recipes

import mobile.solareye.cookflow.data.recipe_detail.RecipeDetailItem

sealed class RecipeListPartialState {
    sealed class InitialLoadingState : RecipeListPartialState() {
        object Loading : InitialLoadingState()
        class Error(val exception: Throwable, val explanation: String): InitialLoadingState()
        class Loaded(val recipes: List<RecipeDetailItem>) : InitialLoadingState()
    }

    sealed class PtrLoadingState : RecipeListPartialState() {
        object Loading : PtrLoadingState()
        class Error(val exception: Throwable, val explanation: String): PtrLoadingState()
        class Loaded(val recipes: List<RecipeDetailItem>) : PtrLoadingState()
    }
}