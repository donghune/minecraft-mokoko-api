package io.github.donghune.api.resources

import io.github.donghune.api.ItemStackFactory
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object Resource {
    object Item {
        val ArrowUp = ItemStackFactory(ItemStack(Material.EMERALD)).setCustomModelData(2972).build()
        val ArrowDown = ItemStackFactory(ItemStack(Material.EMERALD)).setCustomModelData(2911).build()
        val ArrowLeft = ItemStackFactory(ItemStack(Material.EMERALD)).setCustomModelData(2630).build()
        val ArrowRight = ItemStackFactory(ItemStack(Material.EMERALD)).setCustomModelData(2706).build()

        val Ok = ItemStackFactory(ItemStack(Material.EMERALD)).setCustomModelData(3801).build()
        val Cancel = ItemStackFactory(ItemStack(Material.EMERALD)).setCustomModelData(3592).build()

        val ScrollOpen = ItemStackFactory(ItemStack(Material.EMERALD)).setCustomModelData(1797).build()
        val ScrollSeal = ItemStackFactory(ItemStack(Material.EMERALD)).setCustomModelData(1886).build()
    }
}