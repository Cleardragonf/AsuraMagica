package me.asuramagica.events;

import me.asuramagica.tools.util.Hydration.PlayerHydrationCapability;
import me.asuramagica.tools.util.Hydration.PlayerHydrationProvider;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureCapability;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class CustomFoodEvent {
	public void eat(ItemStack drink, PlayerEntity player) {
		player.getCapability(PlayerTemperatureProvider.PlayerTemperature).ifPresent(t ->{
			if(t.playerTemp() < 0) {
				((PlayerTemperatureCapability)t).setPlayerTemp(5);
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() +5);
			}else {
				player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + 10);
			}
		});
	}
}
