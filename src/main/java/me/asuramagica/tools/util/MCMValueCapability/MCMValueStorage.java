package me.asuramagica.tools.util.MCMValueCapability;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class MCMValueStorage implements Capability.IStorage<IMCMValueCapability>{
	
	private int mvmValue;
	private int minValue;
	
	@Nullable

	public INBT writeNBT(Capability<IMCMValueCapability> capability, IMCMValueCapability instance,

			Direction side)

	{

		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("minValue", MCMValueCapability.minValue);
		nbt.putInt("MCMValue", MCMValueCapability.getMCMValue());
		return nbt;

	}


	@Override
	public void readNBT(Capability<IMCMValueCapability> capability, IMCMValueCapability instance, Direction side,
			INBT nbt) {
		CompoundNBT nbt2 = new CompoundNBT();
		
		this.mvmValue = nbt2.getInt("MCMValue");
		this.minValue = nbt2.getInt("minValue");
		
	}
}
