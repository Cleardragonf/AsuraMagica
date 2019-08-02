package me.asuramagica.tools.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerTemperatureCapability implements IPlayerTemperatureCapability{

	private static int playerTemp;
	static int maxTemp = 100;
	static int minTemp = -100;
	
	public void setPlayerTemp(int setPlayerTemp) {
		this.setPlayerTemp2(this.getPlayerTemp() + setPlayerTemp);
		if(getPlayerTemp() > maxTemp) {
			this.setPlayerTemp2(100);
		}if(getPlayerTemp() < minTemp) {
			this.setPlayerTemp2(-100);
		}
	}

	
	@Override
	public int playerTemp() {
		return this.getPlayerTemp();
	}
	

	@Override
	public int maxTemp() {
		return this.maxTemp;
	}
	
	@Override
	public int minTemp() {
		return this.minTemp;
	}


	
	public static int getPlayerTemp() {
		return playerTemp;
	}


	public static void setPlayerTemp2(int playerTemp) {
		PlayerTemperatureCapability.playerTemp = playerTemp;
	}



}
