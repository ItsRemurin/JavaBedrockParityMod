package com.javabedrockparity.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BellBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.TargetBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BellBlock.class)
public abstract class BellBlockMixin extends BlockWithEntity {
    protected BellBlockMixin(Settings settings) {
        super(settings);
    }

    @Shadow public abstract boolean ring(World world, BlockPos pos, @Nullable Direction direction);

    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {

        ring(world, hit.getBlockPos(), hit.getSide());
        projectile.setVelocity(projectile.getVelocity().x * -1, projectile.getVelocity().y / 2, projectile.getVelocity().z * -1);
    }
}
