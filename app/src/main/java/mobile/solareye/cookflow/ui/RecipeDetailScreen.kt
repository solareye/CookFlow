package mobile.solareye.cookflow.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mobile.solareye.cookflow.data.MockDataSource
import mobile.solareye.cookflow.data.recipe_detail.RecipeDetailBigImage
import mobile.solareye.cookflow.data.recipe_detail.RecipeDetailDescription
import mobile.solareye.cookflow.data.recipe_detail.RecipeDetailThumbnails
import mobile.solareye.cookflow.data.recipe_detail.RecipeDetailTitle
import mobile.solareye.cookflow.ui.component.SimpleFlowRow
import kotlin.random.Random

object RecipeDetailScreen {

    @Composable
    fun RecipeDetailScreen(recipeId: String, navigateBack: () -> Unit) {
        Scaffold {
            val scrollState = rememberLazyListState()
            SimpleList(scrollState, recipeId)
        }
    }

    @Composable
    private fun SimpleList(
        scrollState: LazyListState,
        recipeId: String
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = scrollState,
        ) {
            for (recipeItem in MockDataSource.recipe(recipeId).contents) {
                item {
                    when (recipeItem) {
                        is RecipeDetailTitle -> ItemRecipeDetailTitle(recipeItem, recipeId)
                        is RecipeDetailBigImage -> ItemRecipeDetailBigImage(recipeItem)
                        is RecipeDetailDescription -> ItemRecipeDetailDescription(recipeItem)
                        is RecipeDetailThumbnails -> ItemRecipeDetailThumbnails(recipeItem)
                    }
                }
            }
        }
    }

    @Composable
    private fun ItemRecipeDetailTitle(recipeDetailTitle: RecipeDetailTitle, recipeId: String) {
        Text(
            text = "${recipeDetailTitle.text} #$recipeId",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp,
                fontSize = 32.sp
            ),
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
        )
    }

    @Composable
    private fun ItemRecipeDetailBigImage(recipeDetailBigImage: RecipeDetailBigImage) {
        val imageShape = RoundedCornerShape(32.dp)
        Image(
            bitmap = ImageBitmap.imageResource(id = recipeDetailBigImage.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(all = 32.dp)
                .shadow(elevation = 4.dp, shape = imageShape)
                .clip(imageShape)
                .fillMaxWidth()
        )
    }

    @Composable
    private fun ItemRecipeDetailDescription(recipeDetailDescription: RecipeDetailDescription) {
        Text(
            text = recipeDetailDescription.text,
            style = TextStyle(
                lineHeight = 32.sp,
                fontSize = 18.sp,
            ),
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp)
        )
    }

    @Composable
    private fun ItemRecipeDetailThumbnails(recipeDetailThumbnails: RecipeDetailThumbnails) {
        SimpleFlowRow(
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
            horizontalGap = 8.dp,
            verticalGap = 8.dp
        ) {
            for (thumbnail in recipeDetailThumbnails.images) {
                val color = 0xFF000000 or Random.nextLong(0xffffff)
                println("color = $color")
                Image(
                    imageVector = ImageVector.vectorResource(id = thumbnail),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(64.dp),
                    colorFilter = ColorFilter.tint(Color(color))
                )
            }
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun RecipeDetailScreenPreview() {
    RecipeDetailScreen.RecipeDetailScreen("0") { }
}