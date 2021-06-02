package mobile.solareye.cookflow.ui.receipts

import mobile.solareye.cookflow.data.receipt_detail.ReceiptDetailItem

data class ReceiptsListState(
    val receipts: List<ReceiptDetailItem> = emptyList(),
    val isLoading: Boolean = false,
    val isPtrLoading: Boolean = false,
    val error: String? = null,
) {
    companion object {
        fun initialState() = ReceiptsListState()
    }
}
