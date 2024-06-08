package io.izzel.arclight.common.mixin.core.world.entity.ai.behavior;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_20_R1.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

@Mixin(StartAttacking.class)
public class StartAttackingMixin {

    /**
     * @author IzzelAliz
     * @reason
     */
    @Overwrite
    public static <E extends Mob> BehaviorControl<E> create(Predicate<E> p_259618_, Function<E, Optional<? extends LivingEntity>> p_259435_) {
        return BehaviorBuilder.create((p_258782_) -> {
            return p_258782_.group(p_258782_.absent(MemoryModuleType.ATTACK_TARGET), p_258782_.registered(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE)).apply(p_258782_, (p_258778_, p_258779_) -> {
                return (p_258773_, p_258774_, p_258775_) -> {
                    if (!p_259618_.test(p_258774_)) {
                        return false;
                    } else {
                        Optional<? extends LivingEntity> optional = p_259435_.apply(p_258774_);
                        if (optional.isEmpty()) {
                            return false;
                        } else {
                            LivingEntity livingentity = optional.get();
                            if (!p_258774_.canAttack(livingentity)) {
                                return false;
                            } else {
                                LivingChangeTargetEvent changeTargetEvent = ForgeHooks.onLivingChangeTarget(p_258774_, livingentity, LivingChangeTargetEvent.LivingTargetType.BEHAVIOR_TARGET);
                                if (changeTargetEvent.isCanceled())
                                    return false;
                                // CraftBukkit start
                                EntityTargetEvent event = CraftEventFactory.callEntityTargetLivingEvent(p_258774_, livingentity, (livingentity instanceof ServerPlayer) ? EntityTargetEvent.TargetReason.CLOSEST_PLAYER : EntityTargetEvent.TargetReason.CLOSEST_ENTITY);
                                if (event.isCancelled()) {
                                    return false;
                                }
                                if (event.getTarget() == null) {
                                    p_258778_.erase();
                                    return true;
                                }
                                livingentity = ((CraftLivingEntity) event.getTarget()).getHandle();
                                // CraftBukkit end
                                p_258778_.set(changeTargetEvent.getNewTarget());
                                p_258779_.erase();
                                return true;
                            }
                        }
                    }
                };
            });
        });
    }
}
