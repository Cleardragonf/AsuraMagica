package me.asuramagica.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.tools.customGUI;
import me.asuramagica.tools.util.Hydration.PlayerHydrationCapability;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.util.ResourceLocation;

public class Hydration extends IngameGui{
	public Hydration(Minecraft mcIn) {
		super(mcIn);
		// TODO Auto-generated constructor stub
	}

	public static final Minecraft mc = Minecraft.getInstance();
	public static ResourceLocation GUI = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/hydrationgui.png");
	public static ResourceLocation GUIBar = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/hydrationguibar.png");

	public static void draw() {	
		int temp = PlayerHydrationCapability.getPlayersThirst();
	      customGUI.drawModalRectWithCustomSizedTexture(mc.mainWindow.getScaledWidth()+(10-mc.mainWindow.getScaledWidth()), 30, (double)mc.mainWindow.getScaledHeight(), (double)mc.mainWindow.getScaledHeight() - 30, GUI);
	      customGUI.drawModalRectWithCustomSizedTexture((mc.mainWindow.getScaledWidth()+(10-mc.mainWindow.getScaledWidth())), 30,(double)mc.mainWindow.getScaledHeight(), ((double)mc.mainWindow.getScaledHeight() - 30) + temp, GUIBar);

	      mc.fontRenderer.drawString("Hydration Level: " + PlayerHydrationCapability.getPlayersThirst(), 100, mc.mainWindow.getScaledHeight() -30, 0xfffff);
	      
	      /**0,200 min
	       * 100,300 middle
	       * 200,400 max
	       */
	      
	  }
	
}
