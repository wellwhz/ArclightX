package io.izzel.arclight.common.mixin.core.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DetectorRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_20_R1.block.CraftBlock;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(DetectorRailBlock.class)
public class DetectorRailBlockMixin {

    private transient boolean arclight$flag;

    @Inject(method = "checkPressed", locals = LocalCapture.CAPTURE_FAILHARD, at = @At(value = "JUMP", ordinal = 1, opcode = Opcodes.IFEQ))
    public void arclight$blockRedstone(Level worldIn, BlockPos pos, BlockState state, CallbackInfo ci, boolean flag, boolean flag1) {
        if (flag != flag1) {
            Block block = CraftBlock.at(worldIn, pos);

            BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(block, flag ? 15 : 0, flag1 ? 15 : 0);
            Bukkit.getPluginManager().callEvent(eventRedstone);

            arclight$flag = eventRedstone.getNewCurrent() > 0;
        }
    }

    // todo 注入顺序
    @ModifyVariable(method = "checkPressed", index = 5, name = "flag1", at = @At(value = "JUMP", ordinal = 1, opcode = Opcodes.IFEQ))
    public boolean arclight$blockRedstone(boolean flag1) {
        return arclight$flag;
    }
}
