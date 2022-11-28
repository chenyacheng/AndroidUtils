package com.chenyacheng.androidutils.library

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * 倒计时
 */
fun countDownCoroutines(
    total: Int,
    onTick: (Int) -> Unit,
    onFinish: () -> Unit,
    scope: CoroutineScope
): Job {
    return flow {
        for (i in total downTo 1) {
            emit(i)
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
        .onEach { onTick.invoke(it) }
        .onCompletion { cause -> if (cause == null) onFinish.invoke() }
        .flowOn(Dispatchers.Main)
        .launchIn(scope)
}