package mobile.solareye.cookflow.domain.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import java.util.function.BiFunction

@MainThread
fun <T, P> LiveData<P>.scan(
    initial: T,
    reducer: BiFunction<T, P, T>
): LiveData<T> {
    val result = MediatorLiveData<T>()
    result.addSource(this) { x ->
        val acc = result.value ?: initial
        result.value = reducer.apply(acc, x)
    }
    return result
}