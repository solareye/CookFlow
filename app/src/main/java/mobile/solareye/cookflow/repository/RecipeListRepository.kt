package mobile.solareye.cookflow.repository

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withTimeoutOrNull
import mobile.solareye.cookflow.data.api.NetworkDataSource
import mobile.solareye.cookflow.data.local.LocalRecipeDataSource
import mobile.solareye.cookflow.data.model.RecipeListItem
import mobile.solareye.cookflow.data.model.convert

interface RecipeListRepository {
    suspend fun getRecipeList(): Flow<List<RecipeListItem>>
}

class RecipeListRepositoryImpl(
    private val dataSource: NetworkDataSource,
    private val localDataSource: LocalRecipeDataSource,
    private val networkTimeoutMillis: Long = 5_000,
) : RecipeListRepository {

    override suspend fun getRecipeList(): Flow<List<RecipeListItem>> = flow {
        val remoteRecipes = try {
            withTimeoutOrNull(networkTimeoutMillis) {
                dataSource.getRecipeList().convert()
            }
        } catch (cancelled: CancellationException) {
            throw cancelled
        } catch (_: Exception) {
            null
        }

        emit(remoteRecipes?.takeIf { it.isNotEmpty() } ?: localDataSource.getRecipeList())
    }
}
