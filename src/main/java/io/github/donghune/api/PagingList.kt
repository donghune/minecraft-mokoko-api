package io.github.donghune.api

import kotlin.math.max
import kotlin.math.min

class PagingList<T>(
    private val itemCount: Int,
    private val list: List<T>,
) {
    private var cursor = 0
    private val chunkedList = list.chunked(itemCount)

    fun next() {
        cursor = min(chunkedList.size - 1, cursor + 1)
    }

    fun previous() {
        cursor = max(0, cursor - 1)
    }

    fun page() = chunkedList[cursor]
}

fun <T> List<T>.toPagingList(size: Int) = PagingList(size, this)