package me.asuramagica.setup;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements IProxy{
	@Override
	public World getClientWorld() {
		try {
			throw new IllegalAccessException("only run this on the client");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public PlayerEntity getClientPlayer() {
		try {
			throw new IllegalAccessException("only run this on the client");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void init() {
		
	}
}
