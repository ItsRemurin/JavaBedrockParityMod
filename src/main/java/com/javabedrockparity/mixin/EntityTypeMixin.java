package com.javabedrockparity.mixin;

import com.javabedrockparity.JavaBedrockParity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.resource.featuretoggle.ToggleableFeature;
import net.minecraft.util.TypeFilter;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public abstract class EntityTypeMixin<T extends Entity> implements ToggleableFeature,
        TypeFilter<Entity, T> {

    @Shadow @Final private EntityType.EntityFactory<T> factory;

    @Inject(method = "create(Lnet/minecraft/world/World;)Lnet/minecraft/entity/Entity;", at = @At("HEAD"), cancellable = true)
    public void create(World world, CallbackInfoReturnable<@Nullable T> cir) {
        if (!this.isEnabled(world.getEnabledFeatures())) {
            cir.setReturnValue(null);
        }
        EntityType<T> entityType = (EntityType<T>)((Object)this);
        EntityType<T> entityType2 = (EntityType<T>) JavaBedrockParity.PARENT_BABY_RELATION.get(entityType);
        if(entityType2 != null && world.random.nextInt(100) >= 0.05f) {
            entityType = entityType2;
        }

        cir.setReturnValue(this.factory.create(entityType, world));
    }

}
