package me.asuramagica.gui;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.tools.customGUI;
import me.asuramagica.tools.util.IPlayerTemperatureCapability;
import me.asuramagica.tools.util.PlayerTemperatureCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.common.util.LazyOptional;


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
	      
	      /**0,200 min
	       * 100,300 middle
	       * 200,400 max
	       */
	      
	  }
	
	
	public static void temperatureSetings(PlayerEntity player, World world){
		final IPlayerTemperatureCapability test = new PlayerTemperatureCapability(); 	


		final LazyOptional<IPlayerTemperatureCapability> temperature = LazyOptional.of(() -> test).cast();
		
		temperature.ifPresent(h -> {			
				BlockPos postion = player.getPosition();
				if(player.world.getBiome(postion) == Biomes.PLAINS) {
					((PlayerTemperatureCapability)test).setPlayerTemp(10);
				}else if(player.world.getBiome(postion) == Biomes.DESERT) {
					((PlayerTemperatureCapability)test).setPlayerTemp(100);
				}else {
					((PlayerTemperatureCapability)test).setPlayerTemp(0);
				}
				
			int a = h.playerTemp();
			System.out.println(a);
		});


	}
}
