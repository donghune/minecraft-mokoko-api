package io.github.donghune.api.mccoroutine

import io.github.donghune.api.plugin
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext

open class KCoroutineScheduler {

    var job = Job() + plugin.asyncDispatcher

    private var onStart: suspend () -> Unit = {}
    private var onDuringTick: suspend (Long) -> Unit = { _ -> }
    private var onDuringSec: suspend (Int) -> Unit = { _ -> }
    private var onStop: suspend () -> Unit = {}

    private var totalTime = 0
    private var during = 0

    val leftSec: Int
        get() = totalTime - during / 20

    val leftTick: Int
        get() = totalTime * 20 - during

    fun start(time: Int = Int.MAX_VALUE): CoroutineContext {
        totalTime = time
        job += plugin.launch {
            onStart()
            repeat(time * 20) {
                during += 1
                onDuringTick((during % 20).toLong())
                if (during % 20 == 0) {
                    onDuringSec(during / 20)
                }
                delay(25)
            }
            onStop()
        }
        return job
    }

    fun stop() {
        job.cancel()
    }

    fun onStart(function: suspend () -> Unit) {
        onStart = function
    }

    fun onDuringSec(function: suspend (Int) -> Unit) {
        onDuringSec = function
    }

    fun onDuringTick(function: suspend (Long) -> Unit) {
        onDuringTick = function
    }

    fun onStop(function: suspend () -> Unit) {
        onStop = function
    }
}