package io.github.donghune.api.inventory

import io.github.donghune.api.extensions.chatColor
import io.github.donghune.api.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

abstract class GUI(
    private val title: String,
    private val size: Int,
) : Listener {

    private val clickEvents: MutableMap<Int, (InventoryClickEvent) -> Unit> = mutableMapOf()
    protected val inventory by lazy { Bukkit.createInventory(null, size, title.chatColor()) }
    lateinit var player: Player

    fun content(block: () -> Unit) {
        content = block
    }

    fun onInventoryClose(block: InventoryCloseEvent.() -> Unit) {
        onInventoryClose = block
    }

    fun onInventoryOpen(block: InventoryOpenEvent.() -> Unit) {
        onInventoryOpen = block
    }

    fun onPlayerInventoryClick(isCancelled: Boolean = false, block: InventoryClickEvent.() -> Unit) {
        onPlayerInventoryClick = isCancelled to block
    }


    private var content: () -> Unit = {}
    private var onInventoryClose: InventoryCloseEvent.() -> Unit = {}
    private var onInventoryOpen: InventoryOpenEvent.() -> Unit = {}
    private var onPlayerInventoryClick: Pair<Boolean, InventoryClickEvent.() -> Unit> = false to {}

    @EventHandler
    fun onInventoryOpenEvent(event: InventoryOpenEvent) {
        if (event.inventory != inventory) {
            return
        }

        onInventoryOpen(event)
    }

    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {
        if (event.clickedInventory == player.inventory && player.openInventory.topInventory == inventory) {
            event.isCancelled = onPlayerInventoryClick.first
            onPlayerInventoryClick.second(event)
            return
        }

        if (event.clickedInventory != inventory) {
            return
        }

        clickEvents[event.rawSlot]?.invoke(event)
    }

    @EventHandler
    fun onInventoryCloseEvent(event: InventoryCloseEvent) {
        if (event.inventory != inventory) {
            return
        }

        onInventoryClose(event)
        InventoryCloseEvent.getHandlerList().unregister(this)
        InventoryClickEvent.getHandlerList().unregister(this)
        InventoryOpenEvent.getHandlerList().unregister(this)
    }

    fun refreshContent() {
        inventory.clear()
        clickEvents.clear()
        InventoryClickEvent.getHandlerList().unregister(this@GUI)
        Bukkit.getPluginManager().registerEvents(this@GUI, plugin)
        content()
    }

    fun open(player: Player) {
        Bukkit.getPluginManager().registerEvents(this@GUI, plugin)
        this@GUI.player = player
        player.openInventory(inventory)
        content()
    }

    fun openLater(player: Player) {
        Bukkit.getScheduler().runTaskLater(plugin, Runnable { open(player) }, 1L)
    }

    fun setItem(index: Int, itemStack: ItemStack, onClick: InventoryClickEvent.() -> Unit = {}) {
        inventory.setItem(index, itemStack)
        clickEvents[index] = onClick
    }

}