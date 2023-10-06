package com.javabedrockparity;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.javabedrockparity.babies.BabyEntities;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class JavaBedrockParity implements ModInitializer {
	public static BiMap<EntityType<? extends MobEntity>, EntityType<? extends MobEntity>> PARENT_BABY_RELATION = HashBiMap.create();
	public static final String MODID = "javabedrockparity";

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	@Override
	public void onInitialize() {

		PARENT_BABY_RELATION.put(EntityType.SQUID, BabyEntities.BABY_SQUID);
		//PARENT_BABY_RELATION.put(EntityType.GLOW_SQUID, BabyEntities.BABY_GLOW_SQUID);
		PARENT_BABY_RELATION.put(EntityType.GLOW_SQUID, BabyEntities.BABY_GLOW_SQUID);
		FabricDefaultAttributeRegistry.register(BabyEntities.BABY_SQUID, BabySquidEntity.createSquidAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.7 * 1.5));
		FabricDefaultAttributeRegistry.register(BabyEntities.BABY_GLOW_SQUID, BabySquidEntity.createSquidAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.7 * 1.5));

	}


}