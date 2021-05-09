package mobile.solareye.cookflow.data.receipt_detail

import androidx.annotation.DrawableRes
import mobile.solareye.cookflow.data.ItemViewModel

data class ReceiptDetailTitle(
    val text: String
): ItemViewModel

data class ReceiptDetailDescription(
    val text: String
): ItemViewModel

data class ReceiptDetailBigImage(
    @DrawableRes val imageRes: Int
): ItemViewModel

data class ReceiptDetailThumbnails(
    val images: List<Int>
): ItemViewModel