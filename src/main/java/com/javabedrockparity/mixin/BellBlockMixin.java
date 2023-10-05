package com.javabedrockparity.mixin;
import net.minecraft.block.BellBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BellBlock.class)
public abstract class BellBlockMixin extends BlockWithEntity {
    @Shadow public abstract boolean ring(World world, BlockState state, BlockHitResult hitResult, @Nullable PlayerEntity player, boolean checkHitPos);

    protected BellBlockMixin(Settings settings) {
        super(settings);
    }


    @Override
    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        ring(world, state, hit, projectile.getOwner() != null && projectile.getOwner().isPlayer() ? (PlayerEntity) projectile.getOwner() : null, true);
    }
}
