package mobile.solareye.cookflow.data.api

import kotlinx.coroutines.delay
import mobile.solareye.cookflow.data.Lexems
import mobile.solareye.cookflow.data.model.RecipeModel
import mobile.solareye.cookflow.data.model.RecipeResponse

object MockedNetworkDataSource : NetworkDataSource {

    override suspend fun getRecipeList(): RecipeResponse {
        delay(1_000)

        return RecipeResponse(
            recipes = (1..10).map { id ->
                RecipeModel(
                    id = id.toString(),
                    name = "${Lexems.recipeDetailTitle} #$id",
                    description = Lexems.recipeDetailDescription,
                    imageUrl = ""
                )
            }
        )
    }
}
