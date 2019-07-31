package me.asuramagica.tools.util;

import javax.annotation.Nullable;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerTemperatureStorage implements Capability.IStorage<IPlayerTemperatureCapability>{
	
	@Nullable

	public INBT writeNBT(Capability<IPlayerTemperatureCapability> capability, IPlayerTemperatureCapability instance,

			Direction side)

	{

		return null;

	}



	// we don't store anything

	public void readNBT(Capability<IPlayerTemperatureCapability> capability, IPlayerTemperatureCapability instance, Direction side, INBT nbt)

	{



	}
}
