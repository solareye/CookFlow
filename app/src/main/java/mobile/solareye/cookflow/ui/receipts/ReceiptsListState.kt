package mobile.solareye.cookflow.ui.receipts

import mobile.solareye.cookflow.data.receipt_detail.ReceiptDetailItem

sealed class ReceiptsListState {
    object Loading : ReceiptsListState()
    class Error(val exception: Throwable, val explanation: String): ReceiptsListState()
    class Loaded(val receipts: List<ReceiptDetailItem>) : ReceiptsListState()
}
