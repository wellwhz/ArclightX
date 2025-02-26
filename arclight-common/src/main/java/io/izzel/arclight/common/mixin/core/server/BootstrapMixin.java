package io.izzel.arclight.common.mixin.core.server;

import io.izzel.arclight.api.Unsafe;
import net.minecraft.server.Bootstrap;
import org.bukkit.craftbukkit.v1_20_R1.util.CraftLegacy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

@Mixin(Bootstrap.class)
public class BootstrapMixin {

    @Inject(method = "bootStrap", at = @At("HEAD"))
    private static void arclight$replaceWhitelist(CallbackInfo ci) {
        // 噢，天哪，看着肮脏的 hack，我i了
        // 我简直是 fucking genius
        if (new LinkageError().getStackTrace()[2].toString().contains("util.CraftLegacy")) {
            try {
                Field field = CraftLegacy.class.getDeclaredField("whitelistedStates");
                Object base = Unsafe.staticFieldBase(field);
                long offset = Unsafe.staticFieldOffset(field);
                Set<String> prev = (Set<String>) Unsafe.getObject(base, offset);
                class TrickSet extends HashSet<String> {

                    @Override
                    public boolean contains(Object o) {
                        this.add((String) o);
                        return super.contains(o);
                    }
                }
                TrickSet set = new TrickSet();
                set.addAll(prev);
                Unsafe.putObject(base, offset, set);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
