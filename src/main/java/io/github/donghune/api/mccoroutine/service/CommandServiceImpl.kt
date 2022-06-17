package io.github.donghune.api.mccoroutine.service

import io.github.donghune.api.mccoroutine.SuspendingCommandExecutor
import io.github.donghune.api.mccoroutine.SuspendingTabCompleter
import io.github.donghune.api.mccoroutine.launch
import org.bukkit.command.PluginCommand
import org.bukkit.plugin.Plugin

internal class CommandServiceImpl(private val plugin: Plugin) {
    /**
     * Registers a suspend command executor.
     */
    fun registerSuspendCommandExecutor(
        pluginCommand: PluginCommand,
        commandExecutor: SuspendingCommandExecutor
    ) {
        pluginCommand.setExecutor { p0, p1, p2, p3 ->
            // If the result is delayed we can automatically assume it is true.
            var success = true

            // Commands in spigot always arrive synchronously. Therefore, we can simply use the default properties.
            plugin.launch {
                success = commandExecutor.onCommand(p0, p1, p2, p3)
            }

            success
        }
    }

    /**
     * Registers a suspend tab completer.
     */
    fun registerSuspendTabCompleter(pluginCommand: PluginCommand, tabCompleter: SuspendingTabCompleter) {
        pluginCommand.setTabCompleter { sender, command, alias, args ->
            var result = emptyList<String>()

            // Tab Completes in spigot always arrive synchronously. Therefore, we can simply use the default properties.
            plugin.launch {
                result = tabCompleter.onTabComplete(sender, command, alias, args)
            }

            result
        }
    }
}
