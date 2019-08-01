package me.asuramagica.tools.util;

import me.asuramagica.AsuraMagicaMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid=AsuraMagicaMod.MODID, bus=Bus.FORGE)

public class CapabilityEventHandler

{

	public static final ResourceLocation PlayerTemperature = new ResourceLocation(AsuraMagicaMod.MODID, "playertemperature");

	

	@SubscribeEvent

	public static void onAttachCapabilitiesToEntities(AttachCapabilitiesEvent<Entity> e)

	{

		Entity ent = e.getObject();

		if (ent instanceof PlayerEntity)

		{

			e.addCapability(PlayerTemperature, new PlayerTemperatureProvider());

		}

	}

}