package mobile.solareye.cookflow.ui.receipts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mobile.solareye.cookflow.domain.coroutine.CoroutineDispatchers
import mobile.solareye.cookflow.domain.coroutine.UiScope

class ReceiptListViewModelFactory(
    private val uiScope: UiScope,
    private val dispatchers: CoroutineDispatchers,
    private val openReceipt: (Int) -> Unit
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReceiptListViewModel::class.java)) {
            return ReceiptListViewModel(uiScope, dispatchers, openReceipt) as T
        }
        throw IllegalArgumentException("Cannot instantiate receipt list viewmodel")
    }
}