package me.asuramagica.tools.util.Hydration;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerHydrationCapability implements IPlayerHydrationCapability{

	private static int playersThirst;
	static int maxThirst = 100;
	static int minThirst = 0;
	
	public void setPlayersThirst(int setPlayersThirst) {
		this.setPlayersThirst2(this.getPlayersThirst() + setPlayersThirst);
		if(getPlayersThirst() > maxThirst()) {
			this.setPlayersThirst(100);
		}if(getPlayersThirst() < minThirst) {
			this.setPlayersThirst2(0);
		}
	}

	
	@Override
	public int playersThirst() {
		return this.getPlayersThirst();
	}
	

	@Override
	public int maxThirst() {
		return this.maxThirst;
	}
	
	@Override
	public int minThirst() {
		return this.minThirst;
	}


	
	public static int getPlayersThirst() {
		return playersThirst;
	}


	public static void setPlayersThirst2(int playersThirst) {
		PlayerHydrationCapability.playersThirst = playersThirst;
	}



}
