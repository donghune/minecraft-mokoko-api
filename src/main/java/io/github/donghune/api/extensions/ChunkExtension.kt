package io.github.donghune.api.extensions

import org.bukkit.Chunk
import kotlin.math.pow
import kotlin.math.sqrt

fun Chunk.distance(chunk: Chunk): Double {
    return sqrt((chunk.x - this.x).toDouble().pow(2) + (chunk.z - this.z).toDouble().pow(2))
}