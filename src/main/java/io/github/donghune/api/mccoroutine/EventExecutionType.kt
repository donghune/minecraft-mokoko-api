package io.github.donghune.api.mccoroutine

/**
 * The mode how suspendable events are executed if dispatched manually.
 */
enum class EventExecutionType {
    Consecutive,
    Concurrent
}
