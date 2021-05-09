package mobile.solareye.cookflow

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate
import mobile.solareye.cookflow.Destinations.ReceiptDetail

object Destinations {
    const val ReceiptList = "receiptList"
    const val ReceiptDetail = "receiptDetail"

    object ReceiptDetailArgs {
        const val ReceiptId = "receiptId"
    }
}

class Actions(navController: NavHostController) {
    val openReceipt: (Int) -> Unit = { receiptId ->
        navController.navigate("$ReceiptDetail/$receiptId")
    }
    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }
}