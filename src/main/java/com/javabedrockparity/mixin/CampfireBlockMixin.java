package com.javabedrockparity.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireBlock.class)
public class CampfireBlockMixin {
    @Shadow @Final public static BooleanProperty LIT;

    @Inject(method = "onEntityCollision", at = @At("TAIL"))
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo ci) {
        if(entity.isOnFire()) {
            if(!state.get(LIT)) {
                state.with(LIT, true);
            }
        }
    }
}
