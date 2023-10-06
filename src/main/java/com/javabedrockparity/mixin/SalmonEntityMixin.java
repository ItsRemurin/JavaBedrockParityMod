package com.javabedrockparity.mixin;

import com.javabedrockparity.ISalmonEntityDuck;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.SalmonEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SalmonEntity.class)
public abstract class SalmonEntityMixin extends SchoolingFishEntity implements ISalmonEntityDuck {
    @Unique
    private int scaleMult = 2;
    public SalmonEntityMixin(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public float getScaleFactor() {
        return 0.5f * javaBedrockParityMod$getSize();
    }


    @Override
    public int javaBedrockParityMod$getSize() {
        return this.scaleMult;
    }

    @Override
    public void javaBedrockParityMod$setSize(int size) {
        this.scaleMult = size;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(EntityType entityType, World world, CallbackInfo ci) {
        int randNum = random.nextInt(100);
        if(randNum <= 15.7f) {
            javaBedrockParityMod$setSize(3);
        } else if(randNum <= 15.7f + 31.5f) {
            javaBedrockParityMod$setSize(1);
        } else {
            javaBedrockParityMod$setSize(2);
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Size", this.javaBedrockParityMod$getSize());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.javaBedrockParityMod$setSize(nbt.getInt("Size"));
    }
}
