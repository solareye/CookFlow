package mobile.solareye.cookflow

import androidx.navigation.NavHostController
import mobile.solareye.cookflow.Destinations.RecipeDetail

object Destinations {
    const val RecipeList = "recipeList"
    const val RecipeDetail = "recipeDetail"

    object RecipeDetailArgs {
        const val RecipeId = "recipeId"
    }
}

class Actions(navController: NavHostController) {
    val openRecipe: (String) -> Unit = { recipeId ->
        navController.navigate("$RecipeDetail/$recipeId")
    }
    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }
}