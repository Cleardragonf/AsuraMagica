package me.asuramagica.gui;

import me.asuramagica.AsuraMagicaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;



public class Temperature {
	public static final Minecraft mc = Minecraft.getInstance();
	public static ResourceLocation GUI2 = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/tempgage.png");
	
	public static void draw() {

		mc.getTextureManager().bindTexture(GUI2);
	    mc.ingameGUI.blit(0,0,0,0,300,114);

	  }
}
