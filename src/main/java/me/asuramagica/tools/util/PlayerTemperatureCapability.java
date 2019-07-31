package me.asuramagica.tools.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerTemperatureCapability implements IPlayerTemperatureCapability, INBTSerializable<CompoundNBT>{

	private int playerTemp;
	private int maxTemp;
	private int minTemp;
	
	public void setPlayerTemp(int setPlayerTemp) {
		this.playerTemp = setPlayerTemp;
	}

	
	@Override
	public int playerTemp() {
		return this.playerTemp;
	}
	

	@Override
	public int maxTemp() {
		return this.maxTemp;
	}
	
	@Override
	public int minTemp() {
		return this.minTemp;
	}

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("MaxTemp", this.maxTemp);
		nbt.putInt("MinTemp", minTemp);
		nbt.putInt("PlayerTemp", this.playerTemp);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		this.maxTemp = nbt.getInt("MaxTemp");
		this.minTemp = nbt.getInt("MinTemp");
		this.playerTemp = nbt.getInt("PlayerTemp");
		
	}

}
