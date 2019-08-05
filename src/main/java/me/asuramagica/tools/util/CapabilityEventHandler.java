package me.asuramagica.tools.util;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.tools.util.MCMValueCapability.MCMValueProvider;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid=AsuraMagicaMod.MODID, bus=Bus.FORGE)

public class CapabilityEventHandler

{

	public static final ResourceLocation PlayerTemperature = new ResourceLocation(AsuraMagicaMod.MODID, "playertemperature");
	public static final ResourceLocation MCMValue = new ResourceLocation(AsuraMagicaMod.MODID, "mcmvalue");
	

	@SubscribeEvent

	public static void onAttachCapabilitiesToEntities(AttachCapabilitiesEvent<Entity> e)

	{

		Entity ent = e.getObject();

		if (ent instanceof PlayerEntity)

		{

			e.addCapability(PlayerTemperature, new PlayerTemperatureProvider());

		}

	}

	@SubscribeEvent
	public static void onAttachCapabilitesToItems(AttachCapabilitiesEvent<Items>a) {
		Items item = a.getObject();
		if(item instanceof Items) {
			a.addCapability(MCMValue, new MCMValueProvider());
		}
	}
}