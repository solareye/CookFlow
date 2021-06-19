package mobile.solareye.cookflow.data.api

import mobile.solareye.cookflow.data.model.RecipeResponse
import retrofit2.http.GET

interface NetworkDataSource {
    @GET("recipes")
    suspend fun getRecipeList(): RecipeResponse
}