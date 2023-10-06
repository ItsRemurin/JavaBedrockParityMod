package com.javabedrockparity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BabySquidEntity extends SquidEntity {
    public BabySquidEntity(EntityType<? extends SquidEntity> entityType, World world) {
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
        return new ItemStack(Objects.requireNonNull(SpawnEggItem.forEntity(EntityType.SQUID)));
    }
}
