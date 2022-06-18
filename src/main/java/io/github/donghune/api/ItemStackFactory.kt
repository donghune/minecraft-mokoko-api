package io.github.donghune.api

import io.github.donghune.api.extensions.chatColor
import io.github.donghune.api.extensions.translateColor
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.TranslatableComponent
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*

fun ItemStack.edit(isWithClone: Boolean = false, block: ItemStackFactory.() -> ItemStackFactory): ItemStack {
    return block.invoke(ItemStackFactory(this, isWithClone)).build()
}

val ItemStack.displayName: String
    get() = (((displayName() as TranslatableComponent).args()[0] as TextComponent).children()[0] as TextComponent).content()

val ItemStack.displayNameOrLocaleMaterial: Component
    get() {
        return if (itemMeta.hasDisplayName()) {
            (((displayName() as TranslatableComponent).args()[0] as TextComponent).children()[0] as TextComponent)
        }
        else {
            Component.text("&f".translateColor()).children(listOf(Component.translatable(type.translationKey())))
        }
    }

val ItemStack.displayNameOrMaterial: String
    get() {
        return if (itemMeta.hasDisplayName()) {
            (((displayName() as TranslatableComponent).args()[0] as TextComponent).children()[0] as TextComponent).content()
        }
        else {
            type.toString()
        }
    }

class ItemStackFactory(
    itemStack: ItemStack = ItemStack(Material.STONE),
    isWithClone: Boolean = true,
) {

    val itemStack = if (isWithClone) itemStack.clone() else itemStack
    var changedItemMeta: ItemMeta = this.itemStack.itemMeta

    fun setDisplayName(displayName: String): ItemStackFactory {
        changedItemMeta.displayName(Component.text("&f$displayName".chatColor()))
        return this
    }

    fun setDisplayName(component: Component): ItemStackFactory {
        changedItemMeta.displayName(component)
        return this
    }

    fun setCustomModelData(customModelData: Int): ItemStackFactory {
        changedItemMeta.setCustomModelData(customModelData)
        return this
    }

    fun setType(material: Material): ItemStackFactory {
        itemStack.type = material
        changedItemMeta = Bukkit.getItemFactory().getItemMeta(material)
        return this
    }

    fun setAmount(value: Int): ItemStackFactory {
        itemStack.amount = value
        return this
    }

    fun addAmount(value: Int): ItemStackFactory {
        itemStack.amount += value
        return this
    }

    fun addLore(value: String): ItemStackFactory {
        (changedItemMeta.lore() ?: mutableListOf())
            .apply { add(Component.text("&f${value}".chatColor())) }
            .also { changedItemMeta.lore(it) }
        return this
    }

    fun addLore(vararg value: String): ItemStackFactory {
        (changedItemMeta.lore() ?: mutableListOf())
            .apply { value.forEach { add(Component.text("&f${it}".chatColor())) } }
            .also { changedItemMeta.lore(it) }
        return this
    }

    fun addLore(value: List<String>): ItemStackFactory {
        addLore(*value.map { "&f${it}".chatColor() }.toTypedArray())
        return this
    }

    fun removeLore(index: Int): ItemStackFactory {
        (changedItemMeta.lore() ?: mutableListOf())
            .apply { removeAt(index) }
            .also { changedItemMeta.lore(it) }
        return this
    }

    fun editLore(index: Int, value: String): ItemStackFactory {
        (changedItemMeta.lore() ?: mutableListOf())
            .apply { set(index, Component.text(value.chatColor())) }
            .also { changedItemMeta.lore(it) }
        return this
    }

    fun setLore(vararg lore: String): ItemStackFactory {
        changedItemMeta.lore(lore.map { Component.text("&f${it}".chatColor()) })
        return this
    }

    fun setLore(lore: List<String>): ItemStackFactory {
        setLore(*lore.toTypedArray())
        return this
    }

    // # Component

    fun addLore(value: Component): ItemStackFactory {
        (changedItemMeta.lore() ?: mutableListOf())
            .apply { add(value) }
            .also { changedItemMeta.lore(it) }
        return this
    }

    fun addLore(vararg value: Component): ItemStackFactory {
        (changedItemMeta.lore() ?: mutableListOf())
            .apply { value.forEach { add(it) } }
            .also { changedItemMeta.lore(it) }
        return this
    }

    fun editLore(index: Int, value: Component): ItemStackFactory {
        (changedItemMeta.lore() ?: mutableListOf())
            .apply { set(index, value) }
            .also { changedItemMeta.lore(it) }
        return this
    }

    fun setLore(vararg lore: Component): ItemStackFactory {
        changedItemMeta.lore(lore.toList())
        return this
    }

    fun addUnsafeEnchantment(enchantment: Enchantment, level: Int): ItemStackFactory {
        itemStack.addUnsafeEnchantment(enchantment, level)
        return this
    }

    fun removeEnchantment(enchantment: Enchantment): ItemStackFactory {
        itemStack.removeEnchantment(enchantment)
        return this
    }

    fun addItemFlags(vararg itemFlags: ItemFlag): ItemStackFactory {
        changedItemMeta.addItemFlags(*itemFlags)
        return this
    }

    fun removeItemFlags(vararg itemFlags: ItemFlag): ItemStackFactory {
        changedItemMeta.removeItemFlags(*itemFlags)
        return this
    }

    fun setUnbreakable(unbreakable: Boolean): ItemStackFactory {
        changedItemMeta.isUnbreakable = unbreakable
        return this
    }

    fun setBannerMeta(block: BannerMeta.() -> Unit): ItemStackFactory {
        (changedItemMeta as BannerMeta).apply(block)
        return this
    }

    fun setEnchantmentStorageMeta(block: EnchantmentStorageMeta.() -> Unit): ItemStackFactory {
        (changedItemMeta as EnchantmentStorageMeta).apply(block)
        return this
    }

    fun setLeatherArmorMeta(block: LeatherArmorMeta.() -> Unit): ItemStackFactory {
        (changedItemMeta as LeatherArmorMeta).apply(block)
        return this
    }

    fun setPotionMeta(block: PotionMeta.() -> Unit): ItemStackFactory {
        (changedItemMeta as PotionMeta).apply(block)
        return this
    }

    fun setSkullMeta(block: SkullMeta.() -> Unit): ItemStackFactory {
        (changedItemMeta as SkullMeta).apply(block)
        return this
    }

    fun setSuspiciousStewMeta(block: SuspiciousStewMeta.() -> Unit): ItemStackFactory {
        (changedItemMeta as SuspiciousStewMeta).apply(block)
        return this
    }

    fun setTropicalFishBucketMeta(block: TropicalFishBucketMeta.() -> Unit): ItemStackFactory {
        (changedItemMeta as TropicalFishBucketMeta).apply(block)
        return this
    }

    fun setBookMeta(block: BookMeta.() -> Unit): ItemStackFactory {
        (changedItemMeta as BookMeta).apply(block)
        return this
    }

    fun <T> setTag(tag: String, data: T): ItemStackFactory {
        changedItemMeta.tags.set(tag, data)
        return this
    }

    fun build(): ItemStack {
        return itemStack.apply { itemMeta = this@ItemStackFactory.changedItemMeta }
    }

    private fun String.replaceChatColorCode(): String {
        return replace("&", "§")
    }

}




fun ItemStack.getByte(tag: String): Byte? {
    return itemMeta.tags.getByte(tag)
}

fun ItemStack.getShort(tag: String): Short? {
    return itemMeta.tags.getShort(tag)
}

fun ItemStack.getInt(tag: String): Int? {
    return itemMeta.tags.getInt(tag)
}

fun ItemStack.getLong(tag: String): Long? {
    return itemMeta.tags.getLong(tag)
}

fun ItemStack.getFloat(tag: String): Float? {
    return itemMeta.tags.getFloat(tag)
}

fun ItemStack.getDouble(tag: String): Double? {
    return itemMeta.tags.getDouble(tag)
}

fun ItemStack.getString(tag: String): String? {
    return itemMeta.tags.getString(tag)
}

fun ItemStack.getByteArray(tag: String): ByteArray? {
    return itemMeta.tags.getByteArray(tag)
}

fun ItemStack.getIntArray(tag: String): IntArray? {
    return itemMeta.tags.getIntArray(tag)
}

fun ItemStack.getLongArray(tag: String): LongArray? {
    return itemMeta.tags.getLongArray(tag)
}