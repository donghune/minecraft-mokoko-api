package io.github.donghune.api.serialization

import io.github.donghune.api.serialization.serializers.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

/**
 * All KotlinBukkitAPI already built Serializers for Kotlinx.serialization.
 *
 * Serializers for: Block, Chunk, Location, MaterialData, Material, World.
 */
val BukkitSerialModule = SerializersModule {
    contextual(BlockSerializer)
    contextual(ChunkSerializer)
    contextual(LocationSerializer)
    contextual(MaterialSerializer)
    contextual(WorldSerializer)
    contextual(ItemStackSerializer)
    contextual(UUIDSerializer)
    contextual(BoundingBoxSerializer)
    contextual(IntRangeSerializer)
}