package me.asuramagica.tools.util.MCMValueCapability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class MCMValueCapability implements IMCMValueCapability{

	private static int mcmValue = 10000;

	static int minValue= 0;
	
	public void setMCMValue(int setValue) {
		this.setPlayerTemp2(this.getMCMValue() + setValue);
		if(getMCMValue() < minValue) {
			this.setPlayerTemp2(-100);
		}
	}

	
	@Override
	public int mcmValue() {
		return this.getMCMValue();
	}
	
	@Override
	public int minValue() {
		return this.minValue;
	}


	
	public static int getMCMValue() {
		return mcmValue;
	}


	public void setPlayerTemp2(int mcmValue) {
		MCMValueCapability.mcmValue = mcmValue;
	}



}
