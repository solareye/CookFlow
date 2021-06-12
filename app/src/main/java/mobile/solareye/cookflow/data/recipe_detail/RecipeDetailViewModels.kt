package mobile.solareye.cookflow.data.recipe_detail

import androidx.annotation.DrawableRes
import mobile.solareye.cookflow.data.ItemViewModel

data class RecipeDetailItem(val id: String, val contents: List<ItemViewModel>)

data class RecipeDetailTitle(
    val text: String
): ItemViewModel

data class RecipeDetailDescription(
    val text: String
): ItemViewModel

data class RecipeDetailBigImage(
    @DrawableRes val imageRes: Int
): ItemViewModel

data class RecipeDetailThumbnails(
    val images: List<Int>
): ItemViewModel