package mobile.solareye.cookflow.data

import kotlinx.coroutines.delay
import mobile.solareye.cookflow.R
import mobile.solareye.cookflow.data.recipe_detail.*

object MockDataSource {

    val thumbnails = listOf(
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
    )

    fun recipe(id: String) = RecipeDetailItem(
        id,
        listOf(
            RecipeDetailTitle(Lexems.recipeDetailTitle),
            RecipeDetailBigImage(R.drawable.image_big),
            RecipeDetailDescription(Lexems.recipeDetailDescription),
            RecipeDetailThumbnails(thumbnails),
        ))

    suspend fun loadRecipeList(): List<RecipeDetailItem> {
        delay(3_000)
        return recipeList()
    }

    fun recipeList(): List<RecipeDetailItem> = (1..10).map { recipe("$it") }
}