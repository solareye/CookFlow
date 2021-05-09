package mobile.solareye.cookflow.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import mobile.solareye.cookflow.Actions
import mobile.solareye.cookflow.Destinations.ReceiptDetail
import mobile.solareye.cookflow.Destinations.ReceiptDetailArgs.ReceiptId
import mobile.solareye.cookflow.Destinations.ReceiptList

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            val actions = remember(navController) { Actions(navController) }

            NavHost(
                navController = navController,
                startDestination = ReceiptList
            ) {
                composable(ReceiptList) {
                    ReceiptsScreen.ReceiptsScreen(
                        openReceipt = actions.openReceipt
                    )
                }
                composable(
                    "${ReceiptDetail}/{$ReceiptId}",
                    arguments = listOf(navArgument(ReceiptId) { type = NavType.IntType })
                ) { backStackEntry ->
                    ReceiptDetailScreen.ReceiptDetailScreen(
                        receiptId = backStackEntry.arguments?.getInt(ReceiptId) ?: -1,
                        navigateBack = actions.navigateBack
                    )
                }
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MainActivityPreview() {

}