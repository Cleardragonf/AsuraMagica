package me.asuramagica.tools.util.Hydration;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerHydrationProvider implements ICapabilityProvider{

	@CapabilityInject(IPlayerHydrationCapability.class)

	public static final Capability<IPlayerHydrationCapability> PlayerThirst = null;
	
	public static void registerCapability() {
		CapabilityManager.INSTANCE.register(IPlayerHydrationCapability.class, new PlayerHydrationStorage(), PlayerHydrationCapability::new);
	}

	

	private IPlayerHydrationCapability instance = PlayerThirst.getDefaultInstance();
	

	

	@Override

	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)

	{

		// TODO Auto-generated method stub

		if (cap == PlayerThirst)

		{

			return LazyOptional.of(() -> instance).cast();

		}

		else

		{

			return LazyOptional.empty();

		}

	}
}
