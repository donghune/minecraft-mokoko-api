package io.github.donghune.api.serialization.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object IntRangeSerializer : KSerializer<IntRange> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("kotlin.IntRange", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): IntRange {
        val values = decoder.decodeString().split("..")
        return IntRange(values[0].toInt(), values[1].toInt())
    }

    override fun serialize(encoder: Encoder, value: IntRange) {
        encoder.encodeString("${value.first}..${value.last}")
    }

}