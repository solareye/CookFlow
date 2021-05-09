package mobile.solareye.cookflow.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object ReceiptsScreen {

    @Composable
    fun ReceiptsScreen(openReceipt: (Int) -> Unit) {
        Scaffold {
            val scrollState = rememberLazyListState()
            SimpleList(scrollState, openReceipt)
        }
    }

    @Composable
    private fun SimpleList(
        scrollState: LazyListState,
        openReceipt: (Int) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            state = scrollState
        ) {
            for (index in 0 until 2) {
                item {
                    SimpleItem(index, openReceipt)
                }
            }
        }
    }

    @Composable
    private fun SimpleItem(index: Int, openReceipt: (Int) -> Unit) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable { openReceipt(index) }) {
            Text(
                text = "Рецепт #$index",
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
    ReceiptsScreen.ReceiptsScreen { println("Clicked #$it") }
}