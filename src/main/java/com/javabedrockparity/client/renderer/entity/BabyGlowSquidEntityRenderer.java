package com.javabedrockparity.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.GlowSquidEntityRenderer;
import net.minecraft.client.render.entity.SquidEntityRenderer;
import net.minecraft.client.render.entity.model.SquidEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.GlowSquidEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class BabyGlowSquidEntityRenderer<T extends SquidEntity> extends GlowSquidEntityRenderer {

    public BabyGlowSquidEntityRenderer(EntityRendererFactory.Context ctx, SquidEntityModel model) {
        super(ctx, model);
    }

    @Override
    protected void setupTransforms(GlowSquidEntity squid, MatrixStack poseStack, float f, float g, float h) {
        float i = MathHelper.lerp(h, squid.prevTiltAngle, squid.tiltAngle);
        float j = MathHelper.lerp(h, squid.prevRollAngle, squid.rollAngle);

        poseStack.translate(0.0D, 0.25D, 0.0D);

        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - g));
        poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(i));
        poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(j));

        poseStack.translate(0.0D, -0.6D, 0.0D);
    }

    @Override
    protected void scale(GlowSquidEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(0.5F, 0.5F, 0.5F);
    }
}
