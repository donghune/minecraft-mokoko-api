package io.github.donghune.api.extensions

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent

fun String.toComponent() : TextComponent {
    return Component.text(this)
}