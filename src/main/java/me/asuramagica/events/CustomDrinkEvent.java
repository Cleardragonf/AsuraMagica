package me.asuramagica.events;

import me.asuramagica.tools.util.Hydration.PlayerHydrationCapability;
import me.asuramagica.tools.util.Hydration.PlayerHydrationProvider;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureCapability;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class CustomDrinkEvent {
	public void drink(ItemStack drink, PlayerEntity player) {
		player.getCapability(PlayerHydrationProvider.PlayerThirst).ifPresent(h ->{
			player.getCapability(PlayerTemperatureProvider.PlayerTemperature).ifPresent(t ->{
				if(t.playerTemp() > 0) {
					((PlayerTemperatureCapability)t).setPlayerTemp(-5);
					((PlayerHydrationCapability)h).setPlayersThirst(5);
				}else {
					((PlayerHydrationCapability)h).setPlayersThirst(10);
				}
			});
		});
	}
}
