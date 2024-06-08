package io.izzel.arclight.common.bridge.core.util;

import io.izzel.arclight.common.bridge.core.world.WorldBridge;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;

public interface IWorldPosCallableBridge {

    default Level bridge$getWorld() {
        return ((ContainerLevelAccess) this).evaluate((a, b) -> a).orElse(null);
    }

    default BlockPos bridge$getPosition() {
        return ((ContainerLevelAccess) this).evaluate((a, b) -> b).orElse(null);
    }

    default Location bridge$getLocation() {
        BlockPos blockPos = bridge$getPosition();
        if (blockPos == null) {
            return null;
        } else {
            Level level = bridge$getWorld();
            CraftWorld world = level == null ? null : ((WorldBridge) level).bridge$getWorld();
            return new Location(world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }
    }
}
