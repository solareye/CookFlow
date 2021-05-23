package mobile.solareye.cookflow.data

import kotlinx.coroutines.delay
import mobile.solareye.cookflow.R
import mobile.solareye.cookflow.data.receipt_detail.*

object MockDataSource {

    val thumbnails = listOf(
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
        R.drawable.ic_apple,
    )

    fun receipt(id: Int) = ReceiptDetailItem(
        id,
        listOf(
            ReceiptDetailTitle(Lexems.receiptDetailTitle),
            ReceiptDetailBigImage(R.drawable.image_big),
            ReceiptDetailDescription(Lexems.receiptDetailDescription),
            ReceiptDetailThumbnails(thumbnails),
        ))

    suspend fun loadReceiptList(): List<ReceiptDetailItem> {
        delay(3_000)
        return (1..10).map { receipt(it) }
    }
}