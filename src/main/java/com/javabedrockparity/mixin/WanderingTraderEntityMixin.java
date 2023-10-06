package com.javabedrockparity.mixin;

import com.javabedrockparity.HoldRandomInHandsGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.HoldInHandsGoal;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderEntityMixin extends MerchantEntity {
    public WanderingTraderEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    public void initGoals(CallbackInfo ci) {
        this.goalSelector.add(5,
                new HoldRandomInHandsGoal<>((WanderingTraderEntity) (Object)this, getWorld(), getOffers(), SoundEvents.ENTITY_WANDERING_TRADER_YES, wanderingTrader -> {
                    PlayerEntity playerEntity = getWorld().getClosestPlayer(this, 6);
                    if(playerEntity != null) {
                        return playerEntity.getStackInHand(playerEntity.getActiveHand()).isOf(Items.EMERALD);
                    }
                    return false;
                }));
    }
}
