package io.github.donghune.api.mccoroutine.impl

import io.github.donghune.api.mccoroutine.CoroutineSession
import io.github.donghune.api.mccoroutine.MCCoroutine
import io.github.donghune.api.mccoroutine.listener.PluginListener
import org.bukkit.plugin.Plugin

/**
 * A singleton implementation which keeps all coroutine sessions of all plugins.
 */
class MCCoroutineImpl : MCCoroutine {
    private val items = HashMap<Plugin, CoroutineSessionImpl>()

    /**
     * Get coroutine session for the given plugin.
     */
    override fun getCoroutineSession(plugin: Plugin): CoroutineSession {
        if (!items.containsKey(plugin)) {
            startCoroutineSession(plugin)
        }

        return items[plugin]!!
    }

    /**
     * Disables coroutine for the given plugin.
     */
    override fun disable(plugin: Plugin) {
        if (!items.containsKey(plugin)) {
            return
        }

        val session = items[plugin]!!
        session.dispose()
        items.remove(plugin)
    }

    /**
     * Starts a new coroutine session.
     */
    private fun startCoroutineSession(plugin: Plugin) {
        if (!plugin.isEnabled) {
            throw RuntimeException("Plugin ${plugin.name} attempt to start a new coroutine session while being disabled. Dispatchers such as plugin.minecraftDispatcher and plugin.asyncDispatcher are already disposed at this point and cannot be used!")
        }

        val pluginListener = PluginListener(this, plugin)
        items[plugin] = CoroutineSessionImpl(plugin)
        plugin.server.pluginManager.registerEvents(pluginListener, plugin)
    }
}
