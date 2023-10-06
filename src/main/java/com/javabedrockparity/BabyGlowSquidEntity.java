package com.javabedrockparity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BabyGlowSquidEntity extends GlowSquidEntity {
    public BabyGlowSquidEntity(EntityType<? extends GlowSquidEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isBaby() {
        return true;
    }

    @Override
    protected boolean shouldDropLoot() {
        return false;
    }


    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(Objects.requireNonNull(SpawnEggItem.forEntity(EntityType.GLOW_SQUID)));
    }
}
