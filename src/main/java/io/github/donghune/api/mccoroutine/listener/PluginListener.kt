package io.github.donghune.api.mccoroutine.listener

import io.github.donghune.api.mccoroutine.MCCoroutine
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.PluginDisableEvent
import org.bukkit.plugin.Plugin

internal class PluginListener(private val mcCoroutine: MCCoroutine, private val plugin: Plugin) : Listener {
    /**
     * Gets called when the plugin is disabled.
     */
    @EventHandler
    fun onPluginDisable(pluginEvent: PluginDisableEvent) {
        if (pluginEvent.plugin != plugin) {
            return
        }

        mcCoroutine.disable(pluginEvent.plugin)
    }
}
