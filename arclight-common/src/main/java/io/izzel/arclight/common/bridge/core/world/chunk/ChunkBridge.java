package io.izzel.arclight.common.bridge.core.world.chunk;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_20_R1.persistence.CraftPersistentDataContainer;

public interface ChunkBridge {

    Chunk bridge$getBukkitChunk();

    BlockState bridge$setType(BlockPos pos, BlockState state, boolean isMoving, boolean doPlace);

    boolean bridge$isMustNotSave();

    void bridge$setMustNotSave(boolean mustNotSave);

    boolean bridge$isNeedsDecoration();

    void bridge$loadCallback();

    void bridge$unloadCallback();

    CraftPersistentDataContainer bridge$getPersistentContainer();
}
