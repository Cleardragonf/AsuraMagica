package me.asuramagica.tools.util.Hydration;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerHydrationStorage implements Capability.IStorage<IPlayerHydrationCapability>{
	
	@Nullable

	public INBT writeNBT(Capability<IPlayerHydrationCapability> capability, IPlayerHydrationCapability instance,

			Direction side)

	{

		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("maxthirst", PlayerHydrationCapability.maxThirst);
		nbt.putInt("minthirst", PlayerHydrationCapability.minThirst);
		nbt.putInt("playersthirst", PlayerHydrationCapability.getPlayersThirst());
		return nbt;

	}



	// we don't store anything

	public void readNBT(Capability<IPlayerHydrationCapability> capability, IPlayerHydrationCapability instance, Direction side, INBT nbt)

	{



	}
}
