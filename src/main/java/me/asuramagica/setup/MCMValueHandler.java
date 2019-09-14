package me.asuramagica.setup;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.config.Config;
import me.asuramagica.tools.util.MCMValueCapability.MCMValueCapability;
import me.asuramagica.tools.util.MCMValueCapability.MCMValueProvider;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(value= {Dist.CLIENT}, modid = AsuraMagicaMod.MODID, bus=Bus.FORGE)
public class MCMValueHandler {
	public final static MCMValueCapability fireEnergy = new MCMValueCapability(); 
	@SubscribeEvent
	public static void toolTip(ItemTooltipEvent event) {
			//even.getItemStack().getItem().toString()...brings out the "fire_mana_ore"....
			//((MCMValueCapability)fireEnergy).setPlayerTemp2(Config.COMMON_CONFIG.get(event.getItemStack().getItem().getRegistryName().toString()));
		try {
			((MCMValueCapability)fireEnergy).setPlayerTemp2(Config.MCM_VALUE.get(event.getItemStack().getItem().getRegistryName()).get());
			String testA = "MCMValue: " + fireEnergy.mcmValue();
			event.getToolTip().add(new StringTextComponent(testA));
		}catch(NullPointerException e) {

		}


	}

}
