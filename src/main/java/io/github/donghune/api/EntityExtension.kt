package io.github.donghune.api

import org.bukkit.entity.Entity
import org.bukkit.metadata.FixedMetadataValue

fun Entity.setTag(tag: String, value: Byte) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Short) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Int) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Long) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Float) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Double) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: String) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: ByteArray) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: IntArray) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: LongArray) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.setTag(tag: String, value: Any) {
    setMetadata(tag, FixedMetadataValue(plugin, value))
}

fun Entity.getByte(tag: String): Byte {
    return getMetadata(tag)[0].asByte()
}

fun Entity.getShort(tag: String): Short {
    return getMetadata(tag)[0].asShort()
}

fun Entity.getInt(tag: String): Int {
    return getMetadata(tag)[0].asInt()
}

fun Entity.getLong(tag: String): Long {
    return getMetadata(tag)[0].asLong()
}

fun Entity.getFloat(tag: String): Float {
    return getMetadata(tag)[0].asFloat()
}

fun Entity.getDouble(tag: String): Double {
    return getMetadata(tag)[0].asDouble()
}

fun Entity.getString(tag: String): String {
    return getMetadata(tag)[0].asString()
}

inline fun <reified T> Entity.getObject(tag: String): T? {
    return getMetadata(tag)[0].value() as? T
}