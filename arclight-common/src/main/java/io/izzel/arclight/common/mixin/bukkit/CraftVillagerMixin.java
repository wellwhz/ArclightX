package io.izzel.arclight.common.mixin.bukkit;

import io.izzel.arclight.common.mod.util.ResourceLocationUtil;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.ForgeRegistries;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftVillager;
import org.bukkit.entity.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = CraftVillager.class, remap = false)
public class CraftVillagerMixin {

    /**
     * @author IzzelAliz
     * @reason
     */
    @Overwrite
    public static Villager.Profession nmsToBukkitProfession(VillagerProfession nms) {
        return Villager.Profession.valueOf(ResourceLocationUtil.standardize(ForgeRegistries.VILLAGER_PROFESSIONS.getKey(nms)));
    }
}
