package me.asuramagica.tools.util.Temperature;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class PlayerTemperatureStorage implements Capability.IStorage<IPlayerTemperatureCapability>{
	
	@Nullable

	public INBT writeNBT(Capability<IPlayerTemperatureCapability> capability, IPlayerTemperatureCapability instance,

			Direction side)

	{

		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("maxtemp", PlayerTemperatureCapability.maxTemp);
		nbt.putInt("mintemp", PlayerTemperatureCapability.minTemp);
		nbt.putInt("playertemp", PlayerTemperatureCapability.getPlayerTemp());
		return nbt;

	}



	// we don't store anything

	public void readNBT(Capability<IPlayerTemperatureCapability> capability, IPlayerTemperatureCapability instance, Direction side, INBT nbt)

	{



	}
}
