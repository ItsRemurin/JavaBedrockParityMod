package com.javabedrockparity.client.renderer.entity;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.SalmonEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SalmonEntity;

public class BigSalmonEntityRenderer extends SalmonEntityRenderer {
    public BigSalmonEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void scale(SalmonEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(1.5F, 1.5F, 1.5F);
    }
}
