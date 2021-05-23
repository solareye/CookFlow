package mobile.solareye.cookflow.ui.receipts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mobile.solareye.cookflow.data.MockDataSource
import mobile.solareye.cookflow.data.receipt_detail.ReceiptDetailItem
import mobile.solareye.cookflow.domain.coroutine.CoroutineDispatchers
import mobile.solareye.cookflow.domain.coroutine.UiScope

class ReceiptListViewModel(
    private val uiScope: UiScope,
    private val dispatchers: CoroutineDispatchers,
    private val openReceipt: (Int) -> Unit
) : ViewModel() {
    private val _state by lazy {
        MutableLiveData<ReceiptsListState>(ReceiptsListState.Loading)
            .also { loadReceiptList() }
    }
    val state: LiveData<ReceiptsListState> = _state

    private fun loadReceiptList() {
        _state.value = ReceiptsListState.Loading

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            _state.value = ReceiptsListState.Error(exception, "Failed to fetch receipts")
        }
        val receipts = uiScope.async(dispatchers.io + errorHandler) {
            MockDataSource.loadReceiptList()
        }
        uiScope.launch(dispatchers.main) {
            _state.value = ReceiptsListState.Loaded(receipts.await())
        }
    }

    fun onReceiptSelected(receipt: ReceiptDetailItem) {
        openReceipt(receipt.id)
    }
}