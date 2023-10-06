package com.javabedrockparity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class HoldRandomInHandsGoal<T extends MobEntity>
        extends Goal {
    private final T actor;
    private final TradeOfferList possibleTrades;
    private final Predicate<? super T> condition;
    @Nullable
    private final SoundEvent sound;

    private float nextCycleInterval;
    private float cycleStopwatch;

    private final World world;

    public HoldRandomInHandsGoal(T actor, World world, TradeOfferList possibleTrades, @Nullable SoundEvent sound, Predicate<? super T> condition) {
        this.actor = actor;
        this.world = world;
        this.possibleTrades = possibleTrades;
        this.sound = sound;
        this.condition = condition;
    }

    public void tick() {

        cycleStopwatch += 2;
        if(cycleStopwatch > nextCycleInterval) {
            cycleStopwatch = 0;
            nextCycleInterval = world.random.nextInt(150) + 100;
            updateWithRandomItem();
        }
    }

    public void updateWithRandomItem() {
        updateItem(possibleTrades.get(world.random.nextInt(possibleTrades.size() - 1)).getSellItem());
    }

    public void updateItem(ItemStack itemStack) {
        ((MobEntity)this.actor).equipStack(EquipmentSlot.MAINHAND, itemStack.copy());
        ((LivingEntity)this.actor).setCurrentHand(Hand.MAIN_HAND);
    }

    @Override
    public boolean canStart() {
        return this.condition.test(this.actor);
    }

    @Override
    public boolean shouldContinue() {
        return ((LivingEntity)this.actor).isUsingItem();
    }

    @Override
    public void start() {
        updateWithRandomItem();
    }

    @Override
    public void stop() {
        ((MobEntity)this.actor).equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        if (this.sound != null) {
            ((Entity)this.actor).playSound(this.sound, 1.0f, ((LivingEntity)this.actor).getRandom().nextFloat() * 0.2f + 0.9f);
        }
    }
}
