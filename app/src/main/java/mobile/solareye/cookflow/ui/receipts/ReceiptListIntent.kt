package mobile.solareye.cookflow.ui.receipts

import mobile.solareye.cookflow.data.receipt_detail.ReceiptDetailItem

sealed class ReceiptListIntent {
    object LoadInitialPageIntent : ReceiptListIntent()
    object PullToRefreshIntent : ReceiptListIntent()
    class OpenReceiptIntent(val receipt: ReceiptDetailItem) : ReceiptListIntent()
}