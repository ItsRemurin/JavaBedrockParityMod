package com.javabedrockparity.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    private static final float weatherAngle = 2;
    private static final float thunderRainAngleMult = 3;

    @Shadow @Final private MinecraftClient client;

    @Shadow private int ticks;

    @Shadow @Final private float[] NORMAL_LINE_DX;

    @Shadow @Final private float[] NORMAL_LINE_DZ;

    @Shadow @Final private static Identifier RAIN;

    @Shadow
    public static int getLightmapCoordinates(BlockRenderView world, BlockPos pos) {
        return 0;
    }
    @Shadow @Final private static Identifier SNOW;

    /**
     * @author Remurin
     * @reason The only real way I can see to replicate this effect is to overwrite the method.
     * I would love to be proven wrong, though.
     */
    @Overwrite
    private void renderWeather(LightmapTextureManager manager, float tickDelta, double cameraX, double cameraY, double cameraZ) {
        float f = this.client.world.getRainGradient(tickDelta);
        if (!(f <= 0.0F)) {
            manager.enable();
            World world = this.client.world;
            int i = MathHelper.floor(cameraX);
            int j = MathHelper.floor(cameraY);
            int k = MathHelper.floor(cameraZ);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            int l = 5;
            if (MinecraftClient.isFancyGraphicsOrBetter()) {
                l = 10;
            }

            RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
            int m = -1;
            float g = (float)this.ticks + tickDelta;
            RenderSystem.setShader(GameRenderer::getParticleProgram);
            BlockPos.Mutable mutable = new BlockPos.Mutable();

            for(int n = k - l; n <= k + l; ++n) {
                for(int o = i - l; o <= i + l; ++o) {
                    int p = (n - k + 16) * 32 + o - i + 16;
                    double d = (double)this.NORMAL_LINE_DX[p] * 0.5;
                    double e = (double)this.NORMAL_LINE_DZ[p] * 0.5;
                    mutable.set((double)o, cameraY, (double)n);
                    Biome biome = (Biome)world.getBiome(mutable).value();
                    if (biome.hasPrecipitation()) {
                        int zTest = (int)(n);
                        if(world.isThundering() && biome.getPrecipitation(mutable) == Biome.Precipitation.RAIN) {
                            zTest += (int) (weatherAngle * 1.5f * thunderRainAngleMult);
                        } else {
                            zTest += (int) (weatherAngle * 1.5f);
                        }
                        int q = world.getTopY(Heightmap.Type.MOTION_BLOCKING, o, zTest);
                        int r = j - l;
                        int s = j + l;
                        if (r < q) {
                            r = q;
                        }

                        if (s < q) {
                            s = q;
                        }

                        int t = q;
                        if (q < j) {
                            t = j;
                        }

                        if (r != s) {
                            Random random = Random.create((long)(o * o * 3121 + o * 45238971 ^ n * n * 418711 + n * 13761));
                            mutable.set(o, r, n);
                            Biome.Precipitation precipitation = biome.getPrecipitation(mutable);
                            float h;
                            float y;
                            if (precipitation == Biome.Precipitation.RAIN) {
                                if (m != 0) {
                                    if (m >= 0) {
                                        tessellator.draw();
                                    }

                                    m = 0;
                                    RenderSystem.setShaderTexture(0, RAIN);
                                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                                }

                                int u = this.ticks + o * o * 3121 + o * 45238971 + n * n * 418711 + n * 13761 & 31;
                                h = -((float)u + tickDelta) / 32.0F * (3.0F + random.nextFloat());
                                double v = (double)o + 0.5 - cameraX;
                                double w = (double)n + 0.5 - cameraZ;
                                float x = (float)Math.sqrt(v * v + w * w) / (float)l;
                                y = ((1.0F - x * x) * 0.5F + 0.5F) * f;
                                mutable.set(o, t, n);
                                int z = getLightmapCoordinates(world, mutable);

                                float zOffset = 0.5f + (world.isThundering() ? weatherAngle * thunderRainAngleMult : weatherAngle);
                                bufferBuilder.vertex((double)o - cameraX - d + 0.5, (double)s - cameraY, (double)n - cameraZ - e + 0.5).texture(0.0F, (float)r * 0.25F + h).color(1.0F, 1.0F, 1.0F, y).light(z).next();
                                bufferBuilder.vertex((double)o - cameraX + d + 0.5, (double)s - cameraY, (double)n - cameraZ + e + 0.5).texture(1.0F, (float)r * 0.25F + h).color(1.0F, 1.0F, 1.0F, y).light(z).next();
                                bufferBuilder.vertex((double)o - cameraX + d + 0.5, (double)r - cameraY, (double)n - cameraZ + e + zOffset).texture(1.0F, (float)s * 0.25F + h).color(1.0F, 1.0F, 1.0F, y).light(z).next();
                                bufferBuilder.vertex((double)o - cameraX - d + 0.5, (double)r - cameraY, (double)n - cameraZ - e + zOffset).texture(0.0F, (float)s * 0.25F + h).color(1.0F, 1.0F, 1.0F, y).light(z).next();
                            } else if (precipitation == Biome.Precipitation.SNOW) {
                                if (m != 1) {
                                    if (m >= 0) {
                                        tessellator.draw();
                                    }

                                    m = 1;
                                    RenderSystem.setShaderTexture(0, SNOW);
                                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
                                }

                                float aa = -((float)(this.ticks & 511) + tickDelta) / 512.0F;
                                h = (float)(random.nextDouble() + (double)g * 0.01 * (double)((float)random.nextGaussian()));
                                float ab = (float)(random.nextDouble() + (double)(g * (float)random.nextGaussian()) * 0.001);
                                double ac = (double)o + 0.5 - cameraX;
                                double ad = (double)n + 0.5 - cameraZ;
                                y = (float)Math.sqrt(ac * ac + ad * ad) / (float)l;
                                float ae = ((1.0F - y * y) * 0.3F + 0.5F) * f;
                                mutable.set(o, t, n);
                                int af = getLightmapCoordinates(world, mutable);
                                int ag = af >> 16 & '\uffff';
                                int ah = af & '\uffff';
                                int ai = (ag * 3 + 240) / 4;
                                int aj = (ah * 3 + 240) / 4;
                                bufferBuilder.vertex((double)o - cameraX - d + 0.5, (double)s - cameraY, (double)n - cameraZ - e + 0.5).texture(0.0F + h, (float)r * 0.25F + aa + ab).color(1.0F, 1.0F, 1.0F, ae).light(aj, ai).next();
                                bufferBuilder.vertex((double)o - cameraX + d + 0.5, (double)s - cameraY, (double)n - cameraZ + e + 0.5).texture(1.0F + h, (float)r * 0.25F + aa + ab).color(1.0F, 1.0F, 1.0F, ae).light(aj, ai).next();
                                bufferBuilder.vertex((double)o - cameraX + d + 0.5 + weatherAngle, (double)r - cameraY, (double)n - cameraZ + e + 1.5).texture(1.0F + h, (float)s * 0.25F + aa + ab).color(1.0F, 1.0F, 1.0F, ae).light(aj, ai).next();
                                bufferBuilder.vertex((double)o - cameraX - d + 0.5 + weatherAngle, (double)r - cameraY, (double)n - cameraZ - e + 1.5).texture(0.0F + h, (float)s * 0.25F + aa + ab).color(1.0F, 1.0F, 1.0F, ae).light(aj, ai).next();
                            }
                        }
                    }
                }
            }

            if (m >= 0) {
                tessellator.draw();
            }

            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            manager.disable();
        }
    }
}
