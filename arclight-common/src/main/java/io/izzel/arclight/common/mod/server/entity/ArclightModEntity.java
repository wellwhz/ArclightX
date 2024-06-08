package io.izzel.arclight.common.mod.server.entity;

import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;

public class ArclightModEntity extends CraftEntity {

    public ArclightModEntity(CraftServer server, Entity entity) {
        super(server, entity);
    }
}
