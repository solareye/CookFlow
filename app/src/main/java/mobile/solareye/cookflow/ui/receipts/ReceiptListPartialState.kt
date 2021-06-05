package mobile.solareye.cookflow.ui.receipts

import mobile.solareye.cookflow.data.receipt_detail.ReceiptDetailItem

sealed class ReceiptListPartialState {
    sealed class InitialLoadingState : ReceiptListPartialState() {
        object Loading : InitialLoadingState()
        class Error(val exception: Throwable, val explanation: String): InitialLoadingState()
        class Loaded(val receipts: List<ReceiptDetailItem>) : InitialLoadingState()
    }

    sealed class PtrLoadingState : ReceiptListPartialState() {
        object Loading : PtrLoadingState()
        class Error(val exception: Throwable, val explanation: String): PtrLoadingState()
        class Loaded(val receipts: List<ReceiptDetailItem>) : PtrLoadingState()
    }
}