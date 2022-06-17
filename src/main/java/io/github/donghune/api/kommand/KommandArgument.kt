package io.github.donghune.api.kommand

import io.github.monun.kommand.node.LiteralNode
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit

fun LiteralNode.offlinePlayer() = dynamic { _, input ->
    Bukkit.getOfflinePlayers().find { it.name == input }
}.apply {
    suggests { context ->
        suggest(
            Bukkit.getOfflinePlayers()
                .filter { offlinePlayer -> (offlinePlayer.name ?: "").contains(context.input) }
                .mapNotNull { offlinePlayer -> offlinePlayer.name }
                .toList()
        ) {
            Component.text(it)
        }
    }
}