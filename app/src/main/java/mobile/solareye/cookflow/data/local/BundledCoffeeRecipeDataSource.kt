package mobile.solareye.cookflow.data.local

import java.io.InputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import mobile.solareye.cookflow.data.model.RecipeCategory
import mobile.solareye.cookflow.data.model.RecipeListItem

class BundledCoffeeRecipeDataSource(
    private val openSnapshot: () -> InputStream,
) : LocalRecipeDataSource {
    private val json = Json { ignoreUnknownKeys = true }

    override suspend fun getRecipeList(): List<RecipeListItem> = withContext(Dispatchers.IO) {
        val snapshot = openSnapshot().bufferedReader(Charsets.UTF_8).use { reader ->
            json.decodeFromString<CoffeeRecipeSnapshot>(reader.readText())
        }
        check(snapshot.status == "OK") { "The bundled coffee recipe snapshot is invalid" }

        snapshot.value.map { recipe ->
            RecipeListItem(
                id = recipe.id,
                name = recipe.name.trim(),
                category = RecipeCategory.COFFEE,
                canOpenDetails = false,
            )
        }.sortedBy { it.name.lowercase() }
    }

    companion object {
        const val ASSET_NAME = "coffee_recipes.json"
    }
}

@Serializable
private data class CoffeeRecipeSnapshot(
    val status: String,
    val value: List<CoffeeRecipeSummary>,
)

@Serializable
private data class CoffeeRecipeSummary(
    val id: String,
    val name: String,
)
