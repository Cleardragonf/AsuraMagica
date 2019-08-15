package me.asuramagica.setup;

import java.util.List;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.gui.Hydration;
import me.asuramagica.gui.Temperature;
import me.asuramagica.tools.util.MCMValueCapability.MCMValueCapability;
import me.asuramagica.tools.util.MCMValueCapability.MCMValueStorage;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(value= {Dist.CLIENT}, modid = AsuraMagicaMod.MODID, bus=Bus.FORGE)
public class clientEventHandler {

	@SubscribeEvent
	//public static void draw(final RenderGameOverlayEvent.Post event) {
	public static void draw(final RenderGameOverlayEvent.Post event) {	
		if ((event.getType() != RenderGameOverlayEvent.ElementType.ALL)) {

			return;

		}
		Hydration.draw();
		Temperature.draw();
	}

}
