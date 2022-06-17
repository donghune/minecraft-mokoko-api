package io.github.donghune.api.serialization.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.util.BoundingBox

object BoundingBoxSerializer : KSerializer<BoundingBox> {
    private const val LOCATION_SEPARATOR = ";"

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("org.bukkit.BoundingBox", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): BoundingBox {
        val raw = decoder.decodeString()

        val slices = raw.split(LOCATION_SEPARATOR)

        return BoundingBox(
                slices[0].toDouble(),
                slices[1].toDouble(),
                slices[2].toDouble(),
                slices[3].toDouble(),
                slices[4].toDouble(),
                slices[5].toDouble(),
        )
    }

    override fun serialize(encoder: Encoder, value: BoundingBox) {
        encoder.encodeString("${value.minX}$LOCATION_SEPARATOR${value.minY}$LOCATION_SEPARATOR${value.minZ}$LOCATION_SEPARATOR${value.maxX}$LOCATION_SEPARATOR${value.maxY}$LOCATION_SEPARATOR${value.maxZ}")
    }
}