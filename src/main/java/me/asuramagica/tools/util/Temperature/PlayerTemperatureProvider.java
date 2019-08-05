package me.asuramagica.tools.util.Temperature;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerTemperatureProvider implements ICapabilityProvider{

	@CapabilityInject(IPlayerTemperatureCapability.class)

	public static final Capability<IPlayerTemperatureCapability> PlayerTemperature = null;
	
	public static void registerCapability() {
		CapabilityManager.INSTANCE.register(IPlayerTemperatureCapability.class, new PlayerTemperatureStorage(), PlayerTemperatureCapability::new);
	}

	

	private IPlayerTemperatureCapability instance = PlayerTemperature.getDefaultInstance();
	

	

	@Override

	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)

	{

		// TODO Auto-generated method stub

		if (cap == PlayerTemperature)

		{

			return LazyOptional.of(() -> instance).cast();

		}

		else

		{

			return LazyOptional.empty();

		}

	}
}
