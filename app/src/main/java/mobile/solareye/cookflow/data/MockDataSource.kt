package mobile.solareye.cookflow.data

import mobile.solareye.cookflow.R
import mobile.solareye.cookflow.data.receipt_detail.ReceiptDetailBigImage
import mobile.solareye.cookflow.data.receipt_detail.ReceiptDetailDescription
import mobile.solareye.cookflow.data.receipt_detail.ReceiptDetailThumbnails
import mobile.solareye.cookflow.data.receipt_detail.ReceiptDetailTitle

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

    val receipt: List<ItemViewModel> = listOf(
        ReceiptDetailTitle(Lexems.receiptDetailTitle),
        ReceiptDetailBigImage(R.drawable.image_big),
        ReceiptDetailDescription(Lexems.receiptDetailDescription),
        ReceiptDetailThumbnails(thumbnails),
    )

}