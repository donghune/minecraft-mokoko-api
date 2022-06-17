package io.github.donghune.api.worldedit

import com.sk89q.worldedit.WorldEdit
import com.sk89q.worldedit.regions.Region
import com.sk89q.worldedit.regions.RegionSelector
import com.sk89q.worldedit.regions.Regions
import org.bukkit.entity.Player
import org.bukkit.util.BoundingBox

fun Player.getWorldEditSelection(): Region? {
    val session = WorldEdit.getInstance().sessionManager.findByName(player?.name)
    return session?.getSelection(session.selectionWorld)
}

fun Region.toBoundingBox(): BoundingBox {
    return BoundingBox(
        minimumPoint.x.toDouble(),
        minimumPoint.y.toDouble(),
        minimumPoint.z.toDouble(),
        maximumPoint.x.toDouble(),
        maximumPoint.y.toDouble(),
        maximumPoint.z.toDouble()
    )
}