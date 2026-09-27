package mobile.solareye.cookflow.data.model

enum class RecipeCategory {
    GENERAL,
    COFFEE,
}

data class RecipeListItem(
    val id: String,
    val name: String,
    val category: RecipeCategory = RecipeCategory.GENERAL,
    val canOpenDetails: Boolean = true,
)
