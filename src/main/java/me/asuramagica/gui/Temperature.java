package me.asuramagica.gui;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.tools.customGUI;
import me.asuramagica.tools.util.IPlayerTemperatureCapability;
import me.asuramagica.tools.util.PlayerTemperatureCapability;
import me.asuramagica.tools.util.PlayerTemperatureProvider;
import me.asuramagica.tools.util.PlayerTemperatureStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.util.LazyOptional;


public class Temperature extends IngameGui{

	public Temperature(Minecraft mcIn) {
		super(mcIn);
		// TODO Auto-generated constructor stub
	}

	public static final Minecraft mc = Minecraft.getInstance();
	public static ResourceLocation GUI2 = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/tempgage.png");
	public static ResourceLocation GUI = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/tempgagebar.png");

	public static void draw() {	
		int temp = PlayerTemperatureCapability.getPlayerTemp();
	      customGUI.drawModalRectWithCustomSizedTexture(mc.mainWindow.getScaledWidth()+(100-mc.mainWindow.getScaledWidth()), 300, (double)mc.mainWindow.getScaledHeight(), (double)mc.mainWindow.getScaledHeight() - 20, GUI2);
	      customGUI.drawModalRectWithCustomSizedTexture((mc.mainWindow.getScaledWidth()+(100-mc.mainWindow.getScaledWidth())) + temp, 300 + temp,(double)mc.mainWindow.getScaledHeight(), (double)mc.mainWindow.getScaledHeight() - 20, GUI);
	      
	      mc.fontRenderer.drawString("Your Body Temp: " + PlayerTemperatureCapability.getPlayerTemp(), 100, mc.mainWindow.getScaledHeight() -30, 0xfffff);
	      
	      /**0,200 min
	       * 100,300 middle
	       * 200,400 max
	       */
	      
	  }
	
}
