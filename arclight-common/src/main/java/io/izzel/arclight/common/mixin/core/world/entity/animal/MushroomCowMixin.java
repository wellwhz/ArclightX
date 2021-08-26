package io.izzel.arclight.common.mixin.core.world.entity.animal;

import io.izzel.arclight.common.bridge.core.world.WorldBridge;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collections;
import java.util.List;

@Mixin(MushroomCow.class)
public abstract class MushroomCowMixin extends AnimalMixin {

    @Redirect(method = "shearInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/MushroomCow;discard()V"))
    private void arclight$animalTransformPre(MushroomCow mushroomCow) {
    }

    @Inject(method = "shearInternal", cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    private void arclight$animalTransform(SoundSource p_28924_, CallbackInfoReturnable<List<ItemStack>> cir, Cow cowEntity) {
        if (CraftEventFactory.callEntityTransformEvent((MushroomCow) (Object) this, cowEntity, EntityTransformEvent.TransformReason.SHEARED).isCancelled()) {
            cir.setReturnValue(Collections.emptyList());
        } else {
            ((WorldBridge) this.level).bridge$pushAddEntityReason(CreatureSpawnEvent.SpawnReason.SHEARED);
            this.discard();
        }
    }
}