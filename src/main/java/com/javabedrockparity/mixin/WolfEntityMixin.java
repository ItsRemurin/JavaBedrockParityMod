package com.javabedrockparity.mixin;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public class WolfEntityMixin {

    @Inject(method = "isBreedingItem", at = @At("TAIL"), cancellable = true)
    public void injectIsBreedingItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if(stack.isIn(ItemTags.FISHES) || stack.isOf(Items.RABBIT_STEW)) {
            cir.setReturnValue(true);
        }
    }
}
