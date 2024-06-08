package io.izzel.arclight.common.mixin.core.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.bukkit.craftbukkit.v1_20_R1.event.CraftEventFactory;
import org.bukkit.event.block.TNTPrimeEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TntBlock.class)
public abstract class TntBlockMixin {

    @Redirect(method = "onPlace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean arclight$redstone1(Level instance, BlockPos pos) {
        return instance.hasNeighborSignal(pos) && CraftEventFactory.callTNTPrimeEvent(instance, pos, TNTPrimeEvent.PrimeCause.REDSTONE, null, null);
    }

    @Redirect(method = "neighborChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;hasNeighborSignal(Lnet/minecraft/core/BlockPos;)Z"))
    private boolean arclight$redstone2(Level instance, BlockPos pos, BlockState p_57457_, Level p_57458_, BlockPos p_57459_, Block p_57460_, BlockPos source) {
        return instance.hasNeighborSignal(pos) && CraftEventFactory.callTNTPrimeEvent(instance, pos, TNTPrimeEvent.PrimeCause.REDSTONE, null, source);
    }

    @Redirect(method = "playerWillDestroy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/TntBlock;onCaughtFire(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/entity/LivingEntity;)V"))
    private void arclight$playerBreak(TntBlock instance, BlockState state, Level world, BlockPos pos, Direction face, LivingEntity igniter,
                                      Level p_57445_, BlockPos p_57446_, BlockState p_57447_, Player player) {
        if (CraftEventFactory.callTNTPrimeEvent(world, pos, TNTPrimeEvent.PrimeCause.BLOCK_BREAK, player, null)) {
            instance.onCaughtFire(state, world, pos, face, igniter);
        }
    }

    @Inject(method = "use", cancellable = true, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/TntBlock;onCaughtFire(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/entity/LivingEntity;)V"))
    private void arclight$player(BlockState p_57450_, Level level, BlockPos pos, Player player, InteractionHand p_57454_, BlockHitResult p_57455_, CallbackInfoReturnable<InteractionResult> cir) {
        if (!CraftEventFactory.callTNTPrimeEvent(level, pos, TNTPrimeEvent.PrimeCause.PLAYER, player, null)) {
            cir.setReturnValue(InteractionResult.CONSUME);
        }
    }

    @Inject(method = "onProjectileHit", cancellable = true, at = @At(value = "INVOKE", remap = false, target = "Lnet/minecraft/world/level/block/TntBlock;onCaughtFire(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lnet/minecraft/world/entity/LivingEntity;)V"))
    public void arclight$entityChangeBlock(Level worldIn, BlockState state, BlockHitResult hit, Projectile projectile, CallbackInfo ci) {
        if (!CraftEventFactory.callEntityChangeBlockEvent(projectile, hit.getBlockPos(), Blocks.AIR.defaultBlockState())
                || !CraftEventFactory.callTNTPrimeEvent(worldIn, hit.getBlockPos(), TNTPrimeEvent.PrimeCause.PROJECTILE, projectile, null)) {
            ci.cancel();
        }
    }
}
