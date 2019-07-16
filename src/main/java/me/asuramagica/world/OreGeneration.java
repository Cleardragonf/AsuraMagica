package me.asuramagica.world;

import me.asuramagica.lists.BlockList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class OreGeneration {
	
	private static final CountRangeConfig water_ore = new CountRangeConfig(15, 10, 0, 25);
    private static final int water_mana_veinsize = 5;
    private static final CountRangeConfig fire_ore = new CountRangeConfig(25, 10, 0, 128);
    private static final int fire_ore_veinsize = 8;

	public static void setupOreGeneration() {
		
		
		for(Biome biome : ForgeRegistries.BIOMES) {

			
			
			
			biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, 
					BlockList.water_mana_ore.getDefaultState(), water_mana_veinsize), Placement.COUNT_RANGE, water_ore));
			
			biome.addFeature(Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, 
					BlockList.fire_mana_ore.getDefaultState(), fire_ore_veinsize), Placement.COUNT_RANGE, fire_ore));
			//biome.addFeature(Decoration.UNDERGROUND_ORES,  new ConfiguredFeature<OreFeatureConfig>(Feature.ORE, new OreFeatureConfig(FillerBlockType.NATURAL_STONE, BlockList.fire_mana_ore.getDefaultState(), 10)));
			//biome.addFeature(Decoration.UNDERGROUND_ORES, new ConfiguredFeature(OreFeatureConfig, p_i49901_2_)<>(Feature.MINABLE, new MinableConfig(MinableConfig.IS_ROCK, BlockList.water_mana_ore.getDefaultState(), 20), new CountRange(), ore_placement));
			
			//biome.addFeature(Decoration.UNDERGROUND_ORES,  new ConfiguredFeature<OreFeatureConfig>(Feature.ORE, new OreFeatureConfig(FillerBlockType.NATURAL_STONE, BlockList.water_mana_ore.getDefaultState(), 10)));
			
			//biome.addFeature(Decoration.UNDERGROUND_ORES,  new ConfiguredFeature<OreFeatureConfig>(Feature.ORE, new OreFeatureConfig(FillerBlockType.NETHERRACK, BlockList.fire_mana_ore.getDefaultState(), 10)));
			
			
			
		}
	}
}
