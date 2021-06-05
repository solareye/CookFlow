package mobile.solareye.cookflow.ui.receipts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mobile.solareye.cookflow.data.MockDataSource
import mobile.solareye.cookflow.domain.coroutine.CoroutineDispatchers
import mobile.solareye.cookflow.domain.coroutine.UiScope
import mobile.solareye.cookflow.domain.livedata.scan

class ReceiptListViewModel(
    private val uiScope: UiScope,
    private val dispatchers: CoroutineDispatchers,
    private val openReceipt: (Int) -> Unit
) : ViewModel() {
    private val _partialState = MutableLiveData<ReceiptListPartialState>()
    val state: LiveData<ReceiptsListState> = _partialState
        .scan(ReceiptsListState.initialState(), this::reduce)

    fun onViewIntent(intent: ReceiptListIntent) {
        when (intent) {
            ReceiptListIntent.LoadInitialPageIntent -> {
                loadReceiptList()
            }
            ReceiptListIntent.PullToRefreshIntent -> TODO()
            is ReceiptListIntent.OpenReceiptIntent -> {
                openReceipt(intent.receipt.id)
            }
        }
    }

    private fun loadReceiptList() {
        _partialState.value = ReceiptListPartialState.InitialLoadingState.Loading

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            _partialState.value = ReceiptListPartialState.InitialLoadingState.Error(
                exception, "Failed to fetch receipts")
        }
        val receipts = uiScope.async(dispatchers.io + errorHandler) {
            MockDataSource.loadReceiptList()
        }
        uiScope.launch(dispatchers.main) {
            _partialState.value = ReceiptListPartialState.InitialLoadingState.Loaded(receipts.await())
        }
    }

    private fun reduce(
        prevState: ReceiptsListState,
        partialState: ReceiptListPartialState
    ): ReceiptsListState = when (partialState) {
        ReceiptListPartialState.InitialLoadingState.Loading -> prevState.copy(
            isLoading = true,
            receipts = emptyList(),
            error = null,
        )
        is ReceiptListPartialState.InitialLoadingState.Error -> prevState.copy(
            isLoading = false,
            error = partialState.explanation,
        )
        is ReceiptListPartialState.InitialLoadingState.Loaded -> prevState.copy(
            isLoading = false,
            receipts = partialState.receipts,
            error = null,
        )
        ReceiptListPartialState.PtrLoadingState.Loading -> prevState.copy(
            isPtrLoading = true,
            error = null,
        )
        is ReceiptListPartialState.PtrLoadingState.Error -> prevState.copy(
            isPtrLoading = false,
            error = partialState.explanation,
        )
        is ReceiptListPartialState.PtrLoadingState.Loaded -> prevState.copy(
            isPtrLoading = false,
            receipts = partialState.receipts,
            error = null,
        )
    }
}