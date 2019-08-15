package me.asuramagica.gui;

import java.util.HashMap;
import java.util.Map;

import me.asuramagica.tools.util.Hydration.PlayerHydrationCapability;
import me.asuramagica.tools.util.Hydration.PlayerHydrationProvider;import me.asuramagica.tools.util.Hydration.PlayerHydrationStorage;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureCapability;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class HydrationContainer extends PlayerHydrationCapability{
		public static void setThirst(PlayerEntity player, World world) {

			
			
			/**
			 * Begin Surrounding Block Checks...Fires...Water etc.	
			 */
			
					int findfireryBlocks = 0;
					int findWateryBlocks = 0;
					int coolingoffBlock = 0;
					
					try (PooledMutableBlockPos pooledMutableBlockPos = PooledMutableBlockPos.retain()) {
						final int posX = player.getPosition().getX();
						final int posY = player.getPosition().getY();
						final int posZ = player.getPosition().getZ();

						for (int z = -2; z <= 3; ++z) {
							for (int x = -2; x <= 2; ++x) {
								for (int y = -2; y <= 2; ++y) {
									final int dist = (x * x) + (y * y) + (z * z);
									if (dist > 25) {
										continue;
									}
									if (dist < 1) {
										continue;
									}
									pooledMutableBlockPos.setPos(posX + x, posY + y, posZ + z);
									final BlockState blockState = world.getBlockState(pooledMutableBlockPos);
									final IFluidState fluidState = world.getFluidState(pooledMutableBlockPos);
									final Block block = blockState.getBlock();
									if (block instanceof FireBlock ||
											block == Blocks.FIRE ||
											(!fluidState.isEmpty() && fluidState.isTagged(FluidTags.LAVA) || block == Blocks.CAMPFIRE)
									) {
										++findfireryBlocks;
									} else if ((!fluidState.isEmpty() && fluidState.isTagged(FluidTags.WATER))
									) {
										if(player.getBlockState().getBlock() == Blocks.WATER) {
											coolingoffBlock = -1;
										}
										
									} else if(block == Blocks.SNOW || block == Blocks.SNOW_BLOCK) {
										--findWateryBlocks;
									}
								}
							}

						}
					}
					
					
					if(player.world.canBlockSeeSky(player.getPosition())) {
						//TODO Calculate the Biomes and the Weather...
						
						BlockPos postion = player.getPosition();
						Biome location = player.world.getBiome(postion);
						HashMap<Biome, Integer> map = new HashMap<Biome, Integer>();
						map.put(Biomes.OCEAN, 0);
						map.put(Biomes.DEFAULT, 0);
						map.put(Biomes.PLAINS, 0);
						map.put(Biomes.DESERT, -1);
						map.put(Biomes.MOUNTAINS, 0);
						map.put(Biomes.FOREST, 0);
						map.put(Biomes.TAIGA, 0);
						map.put(Biomes.SWAMP, 0);
						map.put(Biomes.RIVER, 0);
						map.put(Biomes.NETHER, 0);
						map.put(Biomes.THE_END, 0);
						map.put(Biomes.FROZEN_OCEAN, 0);
						map.put(Biomes.FROZEN_RIVER, 0);
						map.put(Biomes.SNOWY_TUNDRA, 0);
						map.put(Biomes.SNOWY_MOUNTAINS, 0);
						map.put(Biomes.MUSHROOM_FIELDS, 0);
						map.put(Biomes.MUSHROOM_FIELD_SHORE, 0);
						map.put(Biomes.BEACH, 0);
						map.put(Biomes.DESERT_HILLS, -1);
						map.put(Biomes.WOODED_HILLS, 0);
						map.put(Biomes.TAIGA_HILLS, 0);
						map.put(Biomes.MOUNTAIN_EDGE, 0);
						map.put(Biomes.JUNGLE, 0);
						map.put(Biomes.JUNGLE_HILLS, 0);
						map.put(Biomes.JUNGLE_EDGE, 0);
						map.put(Biomes.DEEP_OCEAN, 0);
						map.put(Biomes.STONE_SHORE, 0);
						map.put(Biomes.SNOWY_BEACH, 0);
						map.put(Biomes.BIRCH_FOREST, 0);
						map.put(Biomes.BIRCH_FOREST_HILLS, 0);
						map.put(Biomes.DARK_FOREST, 0);
						map.put(Biomes.SNOWY_TAIGA, 0);
						map.put(Biomes.SNOWY_TAIGA_HILLS, 0);
						map.put(Biomes.GIANT_TREE_TAIGA, 0);
						map.put(Biomes.GIANT_TREE_TAIGA_HILLS, 0);
						map.put(Biomes.WOODED_MOUNTAINS, 0);
						map.put(Biomes.SAVANNA, 0);
						map.put(Biomes.SAVANNA_PLATEAU, 0);
						map.put(Biomes.BADLANDS, 0);
						map.put(Biomes.WOODED_BADLANDS_PLATEAU, 0);
						map.put(Biomes.BADLANDS_PLATEAU, 0);
						map.put(Biomes.SMALL_END_ISLANDS, 0);
						map.put(Biomes.END_MIDLANDS, 0);
						map.put(Biomes.END_HIGHLANDS, 0);
						map.put(Biomes.END_BARRENS, 0);
						map.put(Biomes.WARM_OCEAN, 0);
						map.put(Biomes.LUKEWARM_OCEAN, 0);
						map.put(Biomes.COLD_OCEAN, 0);
						map.put(Biomes.DEEP_WARM_OCEAN, 0);
						map.put(Biomes.DEEP_LUKEWARM_OCEAN, 0);
						map.put(Biomes.DEEP_COLD_OCEAN, 0);
						map.put(Biomes.DEEP_FROZEN_OCEAN, 0);
						map.put(Biomes.THE_VOID, 0);
						map.put(Biomes.SUNFLOWER_PLAINS, 0);
						map.put(Biomes.DESERT_LAKES, -1);
						map.put(Biomes.GRAVELLY_MOUNTAINS, 0);
						map.put(Biomes.FLOWER_FOREST, 0);
						map.put(Biomes.TAIGA_MOUNTAINS, 0);
						map.put(Biomes.SWAMP_HILLS, 0);
						map.put(Biomes.ICE_SPIKES, 0);
						map.put(Biomes.MODIFIED_JUNGLE, 0);
						map.put(Biomes.MODIFIED_JUNGLE_EDGE, 0);
						map.put(Biomes.TALL_BIRCH_FOREST, 0);
						map.put(Biomes.TALL_BIRCH_HILLS, 0);
						map.put(Biomes.DARK_FOREST_HILLS, 0);
						map.put(Biomes.SNOWY_TAIGA_MOUNTAINS, 0);
						map.put(Biomes.GIANT_SPRUCE_TAIGA, 0);
						map.put(Biomes.GIANT_SPRUCE_TAIGA_HILLS, 0);
						map.put(Biomes.MODIFIED_GRAVELLY_MOUNTAINS, 0);
						map.put(Biomes.SHATTERED_SAVANNA, 0);
						map.put(Biomes.SHATTERED_SAVANNA_PLATEAU, 0);
						map.put(Biomes.ERODED_BADLANDS, 0);
						map.put(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, 0);
						map.put(Biomes.MODIFIED_BADLANDS_PLATEAU, 0);
						map.put(Biomes.BAMBOO_JUNGLE, 0);
						for(Map.Entry<Biome, Integer> entry : map.entrySet()) {
							if(location.equals(entry.getKey())) {
								player.getCapability(PlayerHydrationProvider.PlayerThirst).ifPresent(h -> {
									if(((PlayerHydrationCapability)h).getPlayersThirst() >= ((PlayerHydrationCapability)h).minThirst() && ((PlayerHydrationCapability)h).getPlayersThirst() <= ((PlayerHydrationCapability)h).maxThirst() ) {
										((PlayerHydrationCapability)h).setPlayersThirst(entry.getValue());
									}
								});
							}
						}
					}
					
					
		}

}