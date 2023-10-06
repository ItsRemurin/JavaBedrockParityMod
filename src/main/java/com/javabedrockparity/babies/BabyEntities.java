package com.javabedrockparity.babies;

import com.javabedrockparity.BabyGlowSquidEntity;
import com.javabedrockparity.BabySquidEntity;
import com.javabedrockparity.JavaBedrockParity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class BabyEntities {
    public static final EntityType<BabySquidEntity> BABY_SQUID = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(JavaBedrockParity.MODID, "squid"),
            FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, BabySquidEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.4F)).trackRangeChunks(8).build()
    );
    public static final EntityType<BabyGlowSquidEntity> BABY_GLOW_SQUID = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(JavaBedrockParity.MODID, "glow_squid"),
            FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, BabyGlowSquidEntity::new).dimensions(EntityDimensions.fixed(0.4F, 0.4F)).trackRangeChunks(8).build()
    );
}
