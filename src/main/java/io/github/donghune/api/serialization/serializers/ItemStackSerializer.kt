package io.github.donghune.api.serialization.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack

object ItemStackSerializer : KSerializer<ItemStack> {

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("org.bukkit.inventory.ItemStack", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ItemStack {
        return stringBlobToItem(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: ItemStack) {
        encoder.encodeString(itemToStringBlob(value))
    }

    fun itemToStringBlob(itemStack: ItemStack): String {
        val config = YamlConfiguration()
        config.set("i", itemStack)
        return config.saveToString()
    }

    fun stringBlobToItem(stringBlob: String): ItemStack {
        val config = YamlConfiguration()
        config.loadFromString(stringBlob)
        return config.getItemStack("i", null)!!
    }
}