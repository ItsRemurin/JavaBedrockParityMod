package com.javabedrockparity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.color.world.BiomeColors;

public class JavaBedrockParityClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ColorProviderRegistry.BLOCK.register(
				(state, world, pos, index) -> {
					return whiten(BiomeColors.getFoliageColor(world, pos),
							(double) state.get(SnowLevel.SNOWLEVEL).intValue() / (SnowLevel.SNOWLEVEL.getValues().size() - 1));
				},
				Blocks.OAK_LEAVES,
				Blocks.SPRUCE_LEAVES,
				Blocks.BIRCH_LEAVES
		);
	}

	private int whiten(int colour, double whiteness) {
		// Extract colour channels
		int red = (colour >> 16 & 0xff);
		int green = (colour >> 8 & 0xff);
		int blue = (colour & 0xff);

		// Find out how far the colour is from white and interpolate based on the provided whiteness
		red += (int) ((0xff - red) * whiteness);
		green += (int) ((0xff - green) * whiteness);
		blue += (int) ((0xff - blue) * whiteness);

		// Combine the colours back
		return red << 16 | green << 8 | blue;
	}
}