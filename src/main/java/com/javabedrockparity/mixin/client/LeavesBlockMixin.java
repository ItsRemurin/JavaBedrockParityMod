package com.javabedrockparity.mixin.client;

import com.javabedrockparity.SnowLevel;
import net.minecraft.block.*;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.javabedrockparity.SnowLevel.SNOWLEVEL;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends Block {

    public LeavesBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(AbstractBlock.Settings settings, CallbackInfo ci) {
        this.setDefaultState(getDefaultState().with(SNOWLEVEL, 0));
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    public void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(SNOWLEVEL);
    }

    @Inject(method = "randomDisplayTick", at = @At("HEAD"))
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        if(random.nextInt(10) == 0) {
            BlockState upperBlockState = world.getBlockState(pos.up());
            if(upperBlockState.getBlock() == Blocks.SNOW || upperBlockState.getBlock() == Blocks.SNOW_BLOCK) {
                ParticleUtil.spawnParticle(world, pos, random, new BlockStateParticleEffect(ParticleTypes.FALLING_DUST, upperBlockState));
            }
        }
    }

    @Inject(method = "randomTick", at = @At("HEAD"))
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if(world.getBiome(pos).value().getPrecipitation(pos) == Biome.Precipitation.SNOW) {
            if(world.isRaining()) {
                if(state.get(SNOWLEVEL) < SNOWLEVEL.getValues().size() - 1) {
                    world.setBlockState(pos, state.with(SNOWLEVEL, state.get(SNOWLEVEL) + 1));
                }

            } else if(state.get(SNOWLEVEL) > 0){
                world.setBlockState(pos,  state.with(SNOWLEVEL, state.get(SNOWLEVEL) - 1));
            }
        }
    }
}
