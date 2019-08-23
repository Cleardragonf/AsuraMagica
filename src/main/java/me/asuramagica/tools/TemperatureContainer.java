package me.asuramagica.tools;

import java.util.HashMap;
import java.util.Map;

import me.asuramagica.tools.util.Hydration.PlayerHydrationCapability;
import me.asuramagica.tools.util.Hydration.PlayerHydrationProvider;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureCapability;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;


public class TemperatureContainer extends PlayerTemperatureCapability{

	private static Integer biomeValue;

	public static void setTemp(PlayerEntity player, World world) {
		
		Boolean isThisShelter = true;
		try (PooledMutableBlockPos pooledMutableBlockPos = PooledMutableBlockPos.retain()) {
			final int posX = player.getPosition().getX();
			final int posY = player.getPosition().getY();
			final int posZ = player.getPosition().getZ();

			for (int z = -2; z <= 2; ++z) {
				for (int x = -2; x <= 2; ++x) {
					for (int y = -2; y <= 2; ++y) {
						final int dist = (x * x) + 0 + (z * z);
						if (dist > 1) {
							continue;
						}
						if (dist < 0) {
							continue;
						}
						pooledMutableBlockPos.setPos(posX + x, posY + y, posZ + z);
						final BlockState blockState = world.getBlockState(pooledMutableBlockPos);
						final IFluidState fluidState = world.getFluidState(pooledMutableBlockPos);
						final Block block = blockState.getBlock();
						if(world.canBlockSeeSky(pooledMutableBlockPos) == true) {
							isThisShelter = false;
						}
					}
				}

			}
		}
		if(isThisShelter == true) {
			
		}else {
			BlockPos postion = player.getPosition();
			Biome location = player.world.getBiome(postion);
			HashMap<Biome, Integer> map = new HashMap<Biome, Integer>();
			map.put(Biomes.OCEAN, 0);
			map.put(Biomes.DEFAULT, 0);
			map.put(Biomes.PLAINS, 0);
			map.put(Biomes.DESERT, 5);
			map.put(Biomes.MOUNTAINS, -1);
			map.put(Biomes.FOREST, 0);
			map.put(Biomes.TAIGA, 2);
			map.put(Biomes.SWAMP, 2);
			map.put(Biomes.RIVER, 0);
			map.put(Biomes.NETHER, 0);
			map.put(Biomes.THE_END, 0);
			map.put(Biomes.FROZEN_OCEAN, -5);
			map.put(Biomes.FROZEN_RIVER, -5);
			map.put(Biomes.SNOWY_TUNDRA, -5);
			map.put(Biomes.SNOWY_MOUNTAINS, -5);
			map.put(Biomes.MUSHROOM_FIELDS, 0);
			map.put(Biomes.MUSHROOM_FIELD_SHORE, 1);
			map.put(Biomes.BEACH, 1);
			map.put(Biomes.DESERT_HILLS, 5);
			map.put(Biomes.WOODED_HILLS, 1);
			map.put(Biomes.TAIGA_HILLS, 2);
			map.put(Biomes.MOUNTAIN_EDGE, -1);
			map.put(Biomes.JUNGLE, 4);
			map.put(Biomes.JUNGLE_HILLS, 4);
			map.put(Biomes.JUNGLE_EDGE, 4);
			map.put(Biomes.DEEP_OCEAN, -2);
			map.put(Biomes.STONE_SHORE, 2);
			map.put(Biomes.SNOWY_BEACH, -3);
			map.put(Biomes.BIRCH_FOREST, 2);
			map.put(Biomes.BIRCH_FOREST_HILLS, 2);
			map.put(Biomes.DARK_FOREST, 2);
			map.put(Biomes.SNOWY_TAIGA, -5);
			map.put(Biomes.SNOWY_TAIGA_HILLS, -5);
			map.put(Biomes.GIANT_TREE_TAIGA, 2);
			map.put(Biomes.GIANT_TREE_TAIGA_HILLS, 2);
			map.put(Biomes.WOODED_MOUNTAINS, -1);
			map.put(Biomes.SAVANNA, 3);
			map.put(Biomes.SAVANNA_PLATEAU, 3);
			map.put(Biomes.BADLANDS, 3);
			map.put(Biomes.WOODED_BADLANDS_PLATEAU, 3);
			map.put(Biomes.BADLANDS_PLATEAU, 3);
			map.put(Biomes.SMALL_END_ISLANDS, 0);
			map.put(Biomes.END_MIDLANDS, 0);
			map.put(Biomes.END_HIGHLANDS, 0);
			map.put(Biomes.END_BARRENS, 0);
			map.put(Biomes.WARM_OCEAN, 2);
			map.put(Biomes.LUKEWARM_OCEAN, 2);
			map.put(Biomes.COLD_OCEAN, -2);
			map.put(Biomes.DEEP_WARM_OCEAN, 2);
			map.put(Biomes.DEEP_LUKEWARM_OCEAN, 2);
			map.put(Biomes.DEEP_COLD_OCEAN, -2);
			map.put(Biomes.DEEP_FROZEN_OCEAN, -4);
			map.put(Biomes.THE_VOID, 0);
			map.put(Biomes.SUNFLOWER_PLAINS, 1);
			map.put(Biomes.DESERT_LAKES, 5);
			map.put(Biomes.GRAVELLY_MOUNTAINS, 2);
			map.put(Biomes.FLOWER_FOREST, 2);
			map.put(Biomes.TAIGA_MOUNTAINS, 0);
			map.put(Biomes.SWAMP_HILLS, 3);
			map.put(Biomes.ICE_SPIKES, -3);
			map.put(Biomes.MODIFIED_JUNGLE, 4);
			map.put(Biomes.MODIFIED_JUNGLE_EDGE, 4);
			map.put(Biomes.TALL_BIRCH_FOREST, 2);
			map.put(Biomes.TALL_BIRCH_HILLS, 2);
			map.put(Biomes.DARK_FOREST_HILLS, 2);
			map.put(Biomes.SNOWY_TAIGA_MOUNTAINS, -4);
			map.put(Biomes.GIANT_SPRUCE_TAIGA, 2);
			map.put(Biomes.GIANT_SPRUCE_TAIGA_HILLS, 2);
			map.put(Biomes.MODIFIED_GRAVELLY_MOUNTAINS, 3);
			map.put(Biomes.SHATTERED_SAVANNA, 3);
			map.put(Biomes.SHATTERED_SAVANNA_PLATEAU, 3);
			map.put(Biomes.ERODED_BADLANDS, 3);
			map.put(Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, 3);
			map.put(Biomes.MODIFIED_BADLANDS_PLATEAU, 3);
			map.put(Biomes.BAMBOO_JUNGLE, 4);
			
			for(Map.Entry<Biome, Integer> entry : map.entrySet()) {
				if(location.equals(entry.getKey())) {
					biomeValue = entry.getValue();
				}
			}
			//checking to see if you're in a cold situation
			if(biomeValue < 0) {
				if(player.getFoodStats().getFoodLevel() == 0) {
					player.getCapability(PlayerTemperatureProvider.PlayerTemperature).ifPresent(h ->{
						((PlayerTemperatureCapability)h).setPlayerTemp(biomeValue);
					});
				}else {
					player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() - 1);
				}
			}
			//checking to seee if you're in a warm situation
			else if(biomeValue > 0) {
				player.getCapability(PlayerHydrationProvider.PlayerThirst).ifPresent(h -> {
					if(h.playersThirst() == 0) {
						player.getCapability(PlayerTemperatureProvider.PlayerTemperature).ifPresent(b ->{
							((PlayerTemperatureCapability)b).setPlayerTemp(biomeValue);
						});
					}else {
						((PlayerHydrationCapability)h).setPlayersThirst(-biomeValue);
					}
				});
			}
			//if you're in an undknown situation
			else {
				
			}
		}
		tempCheck(player, world);
	}
	
	public static void onActivity(PlayerEntity player, World world) {
		int playerActivityHydration = 0;
		if(player.isSprinting()) {
			playerActivityHydration -= 1;
		}
		if(player.isSwimming()) {
			playerActivityHydration -= 1;
		}
		int thirstdue = playerActivityHydration;
		player.getCapability(PlayerHydrationProvider.PlayerThirst).ifPresent(h -> {
			if(((PlayerHydrationCapability)h).getPlayersThirst() >= ((PlayerHydrationCapability)h).minThirst() && ((PlayerHydrationCapability)h).getPlayersThirst() <= ((PlayerHydrationCapability)h).maxThirst() ) {
				((PlayerHydrationCapability)h).setPlayersThirst(thirstdue);
			}
		});
		tempCheck(player, world);
	}
	
	private static void tempCheck(PlayerEntity player, World world) {
		player.getCapability(PlayerTemperatureProvider.PlayerTemperature).ifPresent(h ->{
			if(((PlayerTemperatureCapability)h).getPlayerTemp() == 100 || ((PlayerTemperatureCapability)h).getPlayerTemp() == -100) {
				player.setHealth(player.getHealth() - 1);
			}
		});
	}
}