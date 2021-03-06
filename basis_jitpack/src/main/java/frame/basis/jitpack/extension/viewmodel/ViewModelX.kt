package frame.basis.jitpack.extension.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

typealias Scope<T> = suspend (CoroutineScope) -> T
typealias Error = suspend (Exception) -> Unit
typealias Cancel = suspend () -> Unit

fun ViewModel.launchExpand(scope: Scope<Unit>, error: Error? = null, cancel: Cancel? = null): Job {
    return viewModelScope.launch {
        try {
            scope.invoke(this)
        } catch (e: Exception) {
            when (e) {
                is CancellationException -> cancel?.invoke()
                else -> error?.invoke(e)
            }
        }
    }
}

inline fun <T> ViewModel.async(crossinline scope: Scope<T>): Deferred<T> {
    return viewModelScope.async { scope.invoke(this) }
}

fun Job.safeCancel() {
    if (job.isActive && !job.isCompleted && !job.isCancelled) {
        job.cancel()
    }
}