package mobile.solareye.cookflow.repository

import java.io.IOException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import mobile.solareye.cookflow.data.api.NetworkDataSource
import mobile.solareye.cookflow.data.local.LocalRecipeDataSource
import mobile.solareye.cookflow.data.model.RecipeCategory
import mobile.solareye.cookflow.data.model.RecipeListItem
import mobile.solareye.cookflow.data.model.RecipeModel
import mobile.solareye.cookflow.data.model.RecipeResponse
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class RecipeListRepositoryTest {
    private val coffee = listOf(
        RecipeListItem("coffee-1", "Бразилия в воронке", RecipeCategory.COFFEE, false),
    )
    private val localSource = LocalRecipeDataSource { coffee }

    @Test
    fun successfulNetworkResponseKeepsNamesAndNavigation() = runBlocking {
        val repository = RecipeListRepositoryImpl(
            dataSource = network {
                RecipeResponse(listOf(RecipeModel("42", "Суп", "Описание", "")))
            },
            localDataSource = LocalRecipeDataSource { error("Local source must not be read") },
        )

        val recipe = repository.getRecipeList().single().single()

        assertEquals("42", recipe.id)
        assertEquals("Суп", recipe.name)
        assertEquals(RecipeCategory.GENERAL, recipe.category)
        assertTrue(recipe.canOpenDetails)
    }

    @Test
    fun disconnectedNetworkLoadsBundledRecipes() = runBlocking {
        val repository = RecipeListRepositoryImpl(
            network { throw IOException("Offline") },
            localSource,
        )

        assertEquals(coffee, repository.getRecipeList().single())
    }

    @Test
    fun unavailableServerLoadsBundledRecipes() = runBlocking {
        val repository = RecipeListRepositoryImpl(
            network {
                throw HttpException(Response.error<RecipeResponse>(
                    503, ResponseBody.create(null, "Service unavailable")
                ))
            },
            localSource,
        )

        assertEquals(coffee, repository.getRecipeList().single())
    }

    @Test
    fun emptyNetworkResponseLoadsBundledRecipes() = runBlocking {
        val repository = RecipeListRepositoryImpl(
            network { RecipeResponse(emptyList()) },
            localSource,
        )

        assertEquals(coffee, repository.getRecipeList().single())
    }

    @Test
    fun timeoutCancelsNetworkRequestAndLoadsBundledRecipes() = runBlocking {
        var networkCancelled = false
        val repository = RecipeListRepositoryImpl(
            network {
                try {
                    awaitCancellation()
                } finally {
                    networkCancelled = true
                }
            },
            localSource,
            networkTimeoutMillis = 50,
        )

        assertEquals(coffee, repository.getRecipeList().single())
        assertTrue(networkCancelled)
    }

    @Test(expected = CancellationException::class)
    fun cancellationDoesNotLoadBundledRecipes() = runBlocking<Unit> {
        val repository = RecipeListRepositoryImpl(
            network { throw CancellationException("Screen closed") },
            LocalRecipeDataSource { error("Cancellation must not trigger fallback") },
        )

        repository.getRecipeList().single()
    }

    @Test(expected = IOException::class)
    fun localLoadingFailureIsPropagated() = runBlocking<Unit> {
        val repository = RecipeListRepositoryImpl(
            network { throw IOException("Offline") },
            LocalRecipeDataSource { throw IOException("Cannot read asset") },
        )

        repository.getRecipeList().single()
    }

    private fun network(load: suspend () -> RecipeResponse) = object : NetworkDataSource {
        override suspend fun getRecipeList(): RecipeResponse = load()
    }
}
