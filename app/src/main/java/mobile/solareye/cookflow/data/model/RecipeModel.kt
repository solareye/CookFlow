package mobile.solareye.cookflow.data.model

import mobile.solareye.cookflow.R
import mobile.solareye.cookflow.data.MockDataSource
import mobile.solareye.cookflow.data.recipe_detail.*

data class RecipeResponse(
    val recipes: List<RecipeModel>
)

data class RecipeModel(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String
)

fun RecipeResponse.convert(): List<RecipeDetailItem> {
    return this.recipes
        .map {
            RecipeDetailItem(
                it.id,
                listOf(
                    RecipeDetailTitle(it.name),
                    RecipeDetailBigImage(R.drawable.image_big),
                    RecipeDetailDescription(it.description),
                    RecipeDetailThumbnails(MockDataSource.thumbnails),
                )
            )
        }
}