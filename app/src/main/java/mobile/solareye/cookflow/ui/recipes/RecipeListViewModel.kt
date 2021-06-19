package mobile.solareye.cookflow.ui.recipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import mobile.solareye.cookflow.domain.coroutine.CoroutineDispatchers
import mobile.solareye.cookflow.domain.coroutine.UiScope
import mobile.solareye.cookflow.domain.livedata.scan
import mobile.solareye.cookflow.repository.RecipeListRepository

class RecipeListViewModel(
    private val uiScope: UiScope,
    private val dispatchers: CoroutineDispatchers,
    private val repository: RecipeListRepository,
    private val openRecipe: (String) -> Unit
) : ViewModel() {
    private val _partialState = MutableLiveData<RecipeListPartialState>()
    val state: LiveData<RecipesListState> = _partialState
        .scan(RecipesListState.initialState(), this::reduce)

    fun onViewIntent(intent: RecipeListIntent) {
        when (intent) {
            RecipeListIntent.LoadInitialPageIntent -> {
                loadRecipeList()
            }
            RecipeListIntent.PullToRefreshIntent -> TODO()
            is RecipeListIntent.OpenRecipeIntent -> {
                openRecipe(intent.recipe.id)
            }
        }
    }

    private fun loadRecipeList() {
        viewModelScope.launch(dispatchers.main) {
            repository.getRecipeList()
                .onStart { _partialState.postValue(RecipeListPartialState.InitialLoadingState.Loading) }
                .flowOn(dispatchers.io)
                .handleErrors()
                .collect { recipeList ->
                    _partialState.value =
                        RecipeListPartialState.InitialLoadingState.Loaded(recipeList)
                }
        }
    }

    private fun reduce(
        prevState: RecipesListState,
        partialState: RecipeListPartialState
    ): RecipesListState = when (partialState) {
        RecipeListPartialState.InitialLoadingState.Loading -> prevState.copy(
            isLoading = true,
            recipes = emptyList(),
            error = null,
        )
        is RecipeListPartialState.InitialLoadingState.Error -> prevState.copy(
            isLoading = false,
            error = partialState.explanation,
        )
        is RecipeListPartialState.InitialLoadingState.Loaded -> prevState.copy(
            isLoading = false,
            recipes = partialState.recipes,
            error = null,
        )
        RecipeListPartialState.PtrLoadingState.Loading -> prevState.copy(
            isPtrLoading = true,
            error = null,
        )
        is RecipeListPartialState.PtrLoadingState.Error -> prevState.copy(
            isPtrLoading = false,
            error = partialState.explanation,
        )
        is RecipeListPartialState.PtrLoadingState.Loaded -> prevState.copy(
            isPtrLoading = false,
            recipes = partialState.recipes,
            error = null,
        )
    }

    private fun <T> Flow<T>.handleErrors(): Flow<T> =
        catch { e -> showErrorMessage(e) }

    private fun showErrorMessage(throwable: Throwable) {
        throwable.printStackTrace()
        _partialState.value = RecipeListPartialState.InitialLoadingState.Error(
            throwable, "Failed to fetch recipes")
    }

}