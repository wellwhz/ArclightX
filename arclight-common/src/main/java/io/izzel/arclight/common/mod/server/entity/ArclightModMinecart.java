package io.izzel.arclight.common.mod.server.entity;

import net.minecraft.world.entity.vehicle.AbstractMinecart;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftMinecart;

public class ArclightModMinecart extends CraftMinecart {

    public ArclightModMinecart(CraftServer server, AbstractMinecart entity) {
        super(server, entity);
    }
}
