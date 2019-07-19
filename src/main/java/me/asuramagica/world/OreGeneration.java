package me.asuramagica.world;

import me.asuramagica.lists.BlockList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration {
	
	private static final CountRangeConfig water_ore = new CountRangeConfig(15, 10, 0, 25);
    private static final int water_mana_veinsize = 5;
    private static final CountRangeConfig fire_ore = new CountRangeConfig(25, 10, 0, 128);
    private static final int fire_ore_veinsize = 8;
    private static final CountRangeConfig earth_ore = new CountRangeConfig(25, 10, 0, 128);
    private static final int earth_ore_veinsize = 50;
    private static final CountRangeConfig wind_ore = new CountRangeConfig(25, 10, 0, 128);
    private static final int wind_ore_veinsize = 8;
	public static void setupOreGeneration() {
		
		
		for(Biome biome : ForgeRegistries.BIOMES) {
			if(biome.equals(Biomes.PLAINS) || biome.equals(Biomes.DESERT) || biome.equals(Biomes.MOUNTAINS) ) { 
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, 
						BlockList.earth_mana_ore.getDefaultState(), earth_ore_veinsize), Placement.COUNT_RANGE, earth_ore));
				
			}else if(biome.equals(Biomes.COLD_OCEAN) || biome.equals(Biomes.DEEP_COLD_OCEAN) || biome.equals(Biomes.DEEP_FROZEN_OCEAN) || biome.equals(Biomes.DEEP_LUKEWARM_OCEAN)||
					biome.equals(Biomes.DEEP_OCEAN) || biome.equals(Biomes.DEEP_OCEAN) || biome.equals(Biomes.DEEP_WARM_OCEAN) || biome.equals(Biomes.DESERT_LAKES) || biome.equals(Biomes.FROZEN_OCEAN) ||
					biome.equals(Biomes.FROZEN_RIVER) || biome.equals(Biomes.LUKEWARM_OCEAN) || biome.equals(Biomes.OCEAN) || biome.equals(Biomes.RIVER) || biome.equals(Biomes.WARM_OCEAN)) {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, 
						BlockList.water_mana_ore.getDefaultState(), water_mana_veinsize), Placement.COUNT_RANGE, water_ore));
			}else if(biome.equals(Biomes.DESERT) || biome.equals(Biomes.DESERT_HILLS) || biome.equals(Biomes.DESERT_LAKES)) {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, 
						BlockList.fire_mana_ore.getDefaultState(), fire_ore_veinsize), Placement.COUNT_RANGE, fire_ore));
			}else if(biome.equals(Biomes.SNOWY_BEACH) || biome.equals(Biomes.SNOWY_MOUNTAINS) || biome.equals(Biomes.SNOWY_TAIGA)|| biome.equals(Biomes.SNOWY_TAIGA_HILLS)|| 
					biome.equals(Biomes.SNOWY_TAIGA_MOUNTAINS) || biome.equals(Biomes.SNOWY_TUNDRA)) {
				biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, 
						BlockList.wind_mana_ore.getDefaultState(), wind_ore_veinsize), Placement.COUNT_RANGE, wind_ore));
			}
		}
	}
}
