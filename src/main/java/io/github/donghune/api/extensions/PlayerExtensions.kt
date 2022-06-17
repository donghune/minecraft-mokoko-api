package io.github.donghune.api.extensions

import io.github.donghune.api.economy
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

fun Player.isHasItemInMainHand(player: Player, message: String? = null): Boolean {
    if (player.inventory.itemInMainHand.type == Material.AIR) {
        player.sendMessage((message ?: "&c손에 아이템이 존해하지 않습니다.").chatColor())
        return false
    }
    return true
}

fun String.chatColor(): String {
    return replace("&", "§")
}

fun Pair<Location, Location>.toBlockArray(): List<Block> {
    val blockList = mutableListOf<Block>()

    if (first.world == null) {
        return emptyList()
    }

    if (second.world == null) {
        return emptyList()
    }

    val minX: Int = first.blockX.coerceAtMost(second.blockX)
    val maxX: Int = first.blockX.coerceAtLeast(second.blockX)

    val minY: Int = first.blockY.coerceAtMost(second.blockY)
    val maxY: Int = first.blockY.coerceAtLeast(second.blockY)

    val minZ: Int = first.blockZ.coerceAtMost(second.blockZ)
    val maxZ: Int = first.blockZ.coerceAtLeast(second.blockZ)

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            for (z in minZ..maxZ) {
                blockList.add(first.world!!.getBlockAt(x, y, z))
            }
        }
    }
    return blockList
}


fun Player.hasMoney(money: Double): Boolean {
    return economy.has(player, money)
}

fun Player.withdrawMoney(money: Double) {
    economy.withdrawPlayer(player, money)
}

fun Player.depositMoney(money: Double) {
    economy.depositPlayer(player, money)
}

fun Player.hasMoney(money: Int): Boolean {
    return economy.has(player, money.toDouble())
}

fun Player.getMoney(): Double {
    return economy.getBalance(player)
}

fun Player.withdrawMoney(money: Int) {
    economy.withdrawPlayer(player, money.toDouble())
}

fun Player.depositMoney(money: Int) {
    economy.depositPlayer(player, money.toDouble())
}