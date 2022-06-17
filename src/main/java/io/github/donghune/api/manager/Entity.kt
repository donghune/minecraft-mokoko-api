package io.github.donghune.api.manager

import kotlinx.serialization.Serializable

@Serializable
abstract class Entity(
    val primaryKey: String,
) {
    val fileName
        get() = "${primaryKey}.json"
}