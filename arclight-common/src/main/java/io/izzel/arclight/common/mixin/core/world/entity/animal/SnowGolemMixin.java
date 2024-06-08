package io.izzel.arclight.common.mixin.core.world.entity.animal;

import io.izzel.arclight.common.bridge.core.util.DamageSourcesBridge;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSources;
import org.bukkit.craftbukkit.v1_20_R1.event.CraftEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import io.izzel.arclight.common.mixin.core.world.entity.PathfinderMobMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowGolem.class)
public abstract class SnowGolemMixin extends PathfinderMobMixin {

    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/DamageSources;onFire()Lnet/minecraft/world/damagesource/DamageSource;"))
    private DamageSource arclight$useMelting(DamageSources instance) {
        return ((DamageSourcesBridge) instance).bridge$melting();
    }

    @Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean arclight$blockForm(Level world, BlockPos pos, BlockState state) {
        return CraftEventFactory.handleBlockFormEvent(world, pos, state, (SnowGolem) (Object) this);
    }

    @Inject(method = "shear", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/SnowGolem;spawnAtLocation(Lnet/minecraft/world/item/ItemStack;F)Lnet/minecraft/world/entity/item/ItemEntity;"))
    private void arclight$forceDropOn(SoundSource pCategory, CallbackInfo ci) {
        this.forceDrops = true;
    }

    @Inject(method = "shear", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/world/entity/animal/SnowGolem;spawnAtLocation(Lnet/minecraft/world/item/ItemStack;F)Lnet/minecraft/world/entity/item/ItemEntity;"))
    private void arclight$forceDropOff(SoundSource pCategory, CallbackInfo ci) {
        this.forceDrops = false;
    }
}
