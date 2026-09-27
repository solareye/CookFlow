package mobile.solareye.cookflow.data.local

import mobile.solareye.cookflow.data.model.RecipeListItem

fun interface LocalRecipeDataSource {
    suspend fun getRecipeList(): List<RecipeListItem>
}
