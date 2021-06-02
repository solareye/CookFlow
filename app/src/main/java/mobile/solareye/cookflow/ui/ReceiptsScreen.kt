package mobile.solareye.cookflow.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import mobile.solareye.cookflow.data.MockDataSource
import mobile.solareye.cookflow.data.receipt_detail.ReceiptDetailItem
import mobile.solareye.cookflow.ui.receipts.ReceiptListIntent
import mobile.solareye.cookflow.ui.receipts.ReceiptsListState

object ReceiptsScreen {
    @Composable
    fun ReceiptsScreen(
        stateLiveData: LiveData<ReceiptsListState>,
        // fixme might be replaced with channel/subject
        dispatchIntent: (ReceiptListIntent) -> Unit,
    ) {
        Scaffold {
            val initialState = remember {
                dispatchIntent(ReceiptListIntent.LoadInitialPageIntent)
                ReceiptsListState.initialState()
            }

            val state = stateLiveData.observeAsState(initialState).value
            if (state.receipts.isNotEmpty()) {
                SimpleList(
                    state.receipts,
                    { receipt -> dispatchIntent(ReceiptListIntent.OpenReceiptIntent(receipt)) })
            }
            if (state.isLoading) {
                ListLoading()
            }
            if (state.error?.isNotEmpty() == true) {
                ListError(state.error)
            }
        }
    }

    @Composable
    private fun ListLoading() {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally))
            }
        }
    }

    @Composable
    private fun ListError(message: String) {
        Column(
            modifier = Modifier
                .padding(vertical = 64.dp, horizontal = 24.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = message)
            }
        }
    }

    @Composable
    internal fun SimpleList(
        receipts: List<ReceiptDetailItem>,
        openReceipt: (ReceiptDetailItem) -> Unit
    ) {
        val scrollState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = scrollState
        ) {
            for (receipt in receipts) {
                item {
                    SimpleItem(receipt, openReceipt)
                }
            }
        }
    }

    @Composable
    private fun SimpleItem(receipt: ReceiptDetailItem, openReceipt: (ReceiptDetailItem) -> Unit) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { openReceipt(receipt) }) {
            Text(
                text = "Рецепт #${receipt.id}",
                style = TextStyle(
                    fontSize = 24.sp,
                ),
                modifier = Modifier
                    .padding(horizontal = 32.dp, vertical = 8.dp),
            )
        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun ReceiptsScreenPreview() {
    ReceiptsScreen.SimpleList(MockDataSource.receiptList()) { println("Clicked #$it") }
}