package mobile.solareye.cookflow.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mobile.solareye.cookflow.domain.coroutine.CoroutineDispatchers
import mobile.solareye.cookflow.domain.coroutine.UiScope
import mobile.solareye.cookflow.repository.RecipeListRepository

class RecipeListViewModelFactory(
    private val uiScope: UiScope,
    private val dispatchers: CoroutineDispatchers,
    private val repository: RecipeListRepository,
    private val openRecipe: (String) -> Unit
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {
            return RecipeListViewModel(uiScope, dispatchers, repository, openRecipe) as T
        }
        throw IllegalArgumentException("Cannot instantiate recipe list viewmodel")
    }
}