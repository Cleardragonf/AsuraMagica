package me.asuramagica.setup;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.gui.Temperature;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(value= {Dist.CLIENT}, modid = AsuraMagicaMod.MODID, bus=Bus.FORGE)
public class clientEventHandler {

	@SubscribeEvent
	public static void draw(final RenderGameOverlayEvent.Post event) {	
		if ((event.getType() != RenderGameOverlayEvent.ElementType.ALL) || (Minecraft.getInstance().currentScreen != null)) {

			return;

		}
		Temperature.draw();
	}
	
}
