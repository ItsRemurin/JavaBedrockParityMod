package com.javabedrockparity.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.spawner.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PatrolSpawner.class)
public class PatrolSpawnerMixin {

    @Inject(
            method = "spawnPillager",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;", shift = At.Shift.BEFORE),
            cancellable = true)
    public void spawnPillagerInject(ServerWorld world, BlockPos pos, Random random, boolean captain, CallbackInfoReturnable<Boolean> cir) {
        if(world.random.nextInt(3) == 0) {
            PatrolEntity patrolEntity = EntityType.VINDICATOR.create(world);
            if (patrolEntity != null) {
                if (captain) {
                    patrolEntity.setPatrolLeader(true);
                    patrolEntity.setRandomPatrolTarget();
                }
                patrolEntity.setPosition(pos.getX(), pos.getY(), pos.getZ());
                patrolEntity.initialize(world, world.getLocalDifficulty(pos), SpawnReason.PATROL, null, null);
                world.spawnEntityAndPassengers(patrolEntity);
                cir.setReturnValue(true);
            }
            cir.setReturnValue(false);
        }
    }
}
