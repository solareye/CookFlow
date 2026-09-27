package mobile.solareye.cookflow.data.model

data class RecipeResponse(
    val recipes: List<RecipeModel>
)

data class RecipeModel(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String
)

fun RecipeResponse.convert(): List<RecipeListItem> = recipes.map { recipe ->
    RecipeListItem(id = recipe.id, name = recipe.name)
}
