package me.asuramagica.setup;

import me.asuramagica.blocks.ModBlocks;
import me.asuramagica.blocks.Mana_Stone.Mana_StoneScreen;
import me.asuramagica.blocks.mcm.MCM_Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ClientProxy implements IProxy{
	
	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}
	
	@Override
	public PlayerEntity getClientPlayer() {
		return Minecraft.getInstance().player;
	}

	@Override
	public void init() {
		ScreenManager.registerFactory(ModBlocks.MANASTONECONTAINER, Mana_StoneScreen::new);
		ScreenManager.registerFactory(ModBlocks.MCMCONTAINER, MCM_Screen::new);
		
	}
}
