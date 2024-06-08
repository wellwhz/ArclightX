package io.izzel.arclight.common.mod.server.entity;

import net.minecraft.world.entity.Mob;
import org.bukkit.craftbukkit.v1_20_R1.CraftServer;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftMob;

public class ArclightModMob extends CraftMob {

    public ArclightModMob(CraftServer server, Mob entity) {
        super(server, entity);
    }
}
