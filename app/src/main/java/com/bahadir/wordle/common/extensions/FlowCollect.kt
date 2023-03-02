package com.bahadir.wordle.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


fun <T> Flow<T>.collectIn(viewLifecycleOwner: LifecycleOwner, response: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launchWhenResumed {
        collect {
            response(it)
        }
    }
}
fun <T> Flow<T>.collectIn(coroutineScope: CoroutineScope, function: (T) -> Unit) {
    coroutineScope.launch {
        collect {
            function(it)
        }
    }
}
