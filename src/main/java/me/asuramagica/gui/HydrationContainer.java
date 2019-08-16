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
	static Boolean inDesert = false;

	public static void setThirst(PlayerEntity player, World world) {
		int desertMultiplier = 0;
		BlockPos postion = player.getPosition();
		Biome location = player.world.getBiome(postion);
		HashMap<Biome, Integer> map = new HashMap<Biome, Integer>();
		map.put(Biomes.DESERT, -1);
		map.put(Biomes.DESERT_HILLS, -1);
		map.put(Biomes.DESERT_LAKES, -1);
		for(Map.Entry<Biome, Integer> entry : map.entrySet()) {
			if(location.equals(entry.getKey())) {
				inDesert = true;
			}
		}
		if(player.world.canBlockSeeSky(player.getPosition())) {
			if(inDesert = true) {
				desertMultiplier = -1;
			}
		}
		int thirstdue = desertMultiplier - 1;
		player.getCapability(PlayerHydrationProvider.PlayerThirst).ifPresent(h -> {
			if(((PlayerHydrationCapability)h).getPlayersThirst() >= ((PlayerHydrationCapability)h).minThirst() && ((PlayerHydrationCapability)h).getPlayersThirst() <= ((PlayerHydrationCapability)h).maxThirst() ) {
				((PlayerHydrationCapability)h).setPlayersThirst(thirstdue);
			}
		});
		
	}	
	
	public static void onActivity(PlayerEntity player, World world) {
		int playerActivity = 0;
		if(player.isSprinting()) {
			playerActivity -= 1;
		}
		if(player.isSwimming()) {
			playerActivity -= 1;
		}
		int thirstdue = playerActivity;
		player.getCapability(PlayerHydrationProvider.PlayerThirst).ifPresent(h -> {
			if(((PlayerHydrationCapability)h).getPlayersThirst() >= ((PlayerHydrationCapability)h).minThirst() && ((PlayerHydrationCapability)h).getPlayersThirst() <= ((PlayerHydrationCapability)h).maxThirst() ) {
				((PlayerHydrationCapability)h).setPlayersThirst(thirstdue);
			}
		});
	}
}