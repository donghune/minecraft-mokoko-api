package io.github.donghune.api

import io.github.donghune.api.mccoroutine.SuspendingPlugin
import io.github.donghune.api.mccoroutine.mcCoroutine
import kotlinx.coroutines.runBlocking
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.java.JavaPlugin

lateinit var plugin: JavaPlugin
lateinit var economy: Economy

open class BasePlugin : JavaPlugin(), SuspendingPlugin {
    override suspend fun onEnableAsync() {
    }

    override suspend fun onDisableAsync() {
    }

    override suspend fun onLoadAsync() {
    }

    override fun onEnable() {
        plugin = this
        economy = server.servicesManager.getRegistration(Economy::class.java)?.provider ?: throw Exception("Vault!!")

        mcCoroutine.getCoroutineSession(this).isManipulatedServerHeartBeatEnabled = true
        runBlocking {
            onEnableAsync()
        }
        // Disables runBlocking hack to not interfere with other tasks.
        mcCoroutine.getCoroutineSession(this).isManipulatedServerHeartBeatEnabled = false
    }

    override fun onDisable() {
        runBlocking {
            onDisableAsync()
        }
    }

    override fun onLoad() {
        runBlocking {
            onLoadAsync()
        }
    }
}

