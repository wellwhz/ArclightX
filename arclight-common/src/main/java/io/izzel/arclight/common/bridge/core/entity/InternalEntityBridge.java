package io.izzel.arclight.common.bridge.core.entity;

import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;

public interface InternalEntityBridge {

    CraftEntity internal$getBukkitEntity();
}
