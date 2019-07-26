package me.asuramagica.gui;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.tools.customGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;

import net.minecraft.util.ResourceLocation;




public class Temperature extends IngameGui{


	public Temperature(Minecraft mcIn) {
		super(mcIn);
		// TODO Auto-generated constructor stub
	}

	public static final Minecraft mc = Minecraft.getInstance();
	public static ResourceLocation GUI2 = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/tempgage.png");
	public static ResourceLocation GUI = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/bar2.png");

	
	public static void draw() {	
	      customGUI.drawModalRectWithCustomSizedTexture(100, 300, (double)mc.mainWindow.getScaledHeight()-10, (double)mc.mainWindow.getFramebufferHeight() - 30, GUI2);
	      customGUI.drawModalRectWithCustomSizedTexture(100, 250,(double)mc.mainWindow.getScaledHeight()-10, (double)mc.mainWindow.getFramebufferHeight() - 30, GUI);
	      
	  }
	

}
