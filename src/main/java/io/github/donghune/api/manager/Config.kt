package io.github.donghune.api.manager

import kotlinx.serialization.Serializable

@Serializable
abstract class Config(
    val primaryKey: String,
) {
    val fileName
        get() = "${primaryKey}.json"

    abstract fun update()
}