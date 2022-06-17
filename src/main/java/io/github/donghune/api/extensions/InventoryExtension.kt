package io.github.donghune.api.extensions

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.hasSpace(item: ItemStack): Boolean {
    val contents = (storageContents ?: arrayOf())

    // 빈칸이 있는가
    if (contents.find { it == null } == null || contents.filterNotNull().find { it.type == Material.AIR } != null) {
        return true
    }

    val similarItem = contents.filterNotNull().find { it.isSimilar(item) }

    // 동일한 아이템 이면서 추가로 들어갈 갯수가 되는가
    if (similarItem != null && (64 - similarItem.amount) >= item.amount) {
        return true
    }

    return false
}