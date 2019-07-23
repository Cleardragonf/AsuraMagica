package me.asuramagica.tools;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage  implements INBTSerializable<CompoundNBT>{

	public CustomEnergyStorage(int capacity, int maxTransfer) {
		super(capacity, maxTransfer);
	}

    @Override
    public int getEnergyStored()
    {
        return energy;
    }
    
	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
	public void addFireEssence(int energy) {
		this.energy += energy;
		if(this.energy > getMaxEnergyStored()) {
			this.energy = getEnergyStored();
		}
	}
	

	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putInt("energy", getEnergyStored());
		return tag;
	}

	public void consumeEnergy(int energy) {
		this.energy -= energy;
		if (this.energy < 0) {
			this.energy = 0;
		}
	}
    
	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		setEnergy(nbt.getInt("energy"));
		
	}
}
