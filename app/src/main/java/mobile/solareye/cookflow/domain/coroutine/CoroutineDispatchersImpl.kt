package mobile.solareye.cookflow.domain.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface CoroutineDispatchers {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}

class CoroutineDispatchersImpl : CoroutineDispatchers {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
}
