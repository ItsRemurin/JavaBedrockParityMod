package com.javabedrockparity.mixin;

import com.javabedrockparity.JavaBedrockParity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(SpawnEggItem.class)
public class SpawnEggItemMixin {
    @Inject(method = "spawnBaby",
    at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;isBaby()Z", shift = At.Shift.AFTER), cancellable = true)
    public void spawnBaby(PlayerEntity user, MobEntity entity, EntityType<? extends MobEntity> entityType, ServerWorld world, Vec3d pos, ItemStack stack, CallbackInfoReturnable<Optional<MobEntity>> cir) {
       EntityType<? extends MobEntity> entityType2 = JavaBedrockParity.PARENT_BABY_RELATION.get(entityType);
       if(entityType2 != null) {
           entityType = entityType2;

           entity = entityType.create(world);

           entity.refreshPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0.0f, 0.0f);
           world.spawnEntityAndPassengers(entity);
           if (stack.hasCustomName()) {
               entity.setCustomName(stack.getName());
           }
           if (!user.getAbilities().creativeMode) {
               stack.decrement(1);
           }
           cir.setReturnValue(Optional.of(entity));
       }
    }
}
