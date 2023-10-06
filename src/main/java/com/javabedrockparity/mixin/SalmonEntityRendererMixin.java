package com.javabedrockparity.mixin;

import com.javabedrockparity.ISalmonEntityDuck;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.SalmonEntityRenderer;
import net.minecraft.client.render.entity.model.SalmonEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SalmonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value= EnvType.CLIENT)
@Mixin(SalmonEntityRenderer.class)
public abstract class SalmonEntityRendererMixin
        extends MobEntityRenderer<SalmonEntity, SalmonEntityModel<SalmonEntity>> {


    public SalmonEntityRendererMixin(EntityRendererFactory.Context context, SalmonEntityModel<SalmonEntity> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "setupTransforms(Lnet/minecraft/entity/passive/SalmonEntity;Lnet/minecraft/client/util/math/MatrixStack;FFF)V", at = @At("TAIL"))
    public void setupTransforms(SalmonEntity salmonEntity, MatrixStack matrixStack, float f, float g, float h, CallbackInfo ci) {
        matrixStack.scale(salmonEntity.getScaleFactor(), salmonEntity.getScaleFactor(), salmonEntity.getScaleFactor());
    }
}
