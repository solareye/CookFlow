package mobile.solareye.cookflow.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import mobile.solareye.cookflow.data.api.NetworkDataSource
import mobile.solareye.cookflow.data.model.convert
import mobile.solareye.cookflow.data.recipe_detail.RecipeDetailItem


interface RecipeListRepository {
    suspend fun getRecipeList(): Flow<List<RecipeDetailItem>>
}

class RecipeListRepositoryImpl(
    private val dataSource: NetworkDataSource
) : RecipeListRepository {

    override suspend fun getRecipeList(): Flow<List<RecipeDetailItem>> =
        dataSource::getRecipeList
            .asFlow()
            .map { it.convert() }

}