package io.github.donghune.api.extensions

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Entity

val mainWorld: World = Bukkit.getWorlds()[0]
fun chunk(world: World, x: Int, y: Int) = world.getChunkAt(x, y)
fun chunk(block: Block) = chunk(block.world, block.x shr 4, block.z shr 4)

inline fun <reified T : Entity> World.spawn(location: Location): T {
    return spawn(location, T::class.java)
}

inline fun <reified T : Entity> World.getEntitiesByClass(): Collection<T> {
    return getEntitiesByClass(T::class.java)
}