package me.asuramagica.setup;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.tools.util.MCMValueCapability.MCMValueCapability;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = AsuraMagicaMod.MODID, bus=Bus.FORGE)
public class MCMValueHandler {
	
	@SubscribeEvent
	public static void toolTip(ItemTooltipEvent event) {
		int test = MCMValueCapability.getMCMValue();
		String testA = "MCMValue: " + test;
		event.getToolTip().add(new StringTextComponent(testA));
	}

}
