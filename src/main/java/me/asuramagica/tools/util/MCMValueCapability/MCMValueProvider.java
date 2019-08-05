package me.asuramagica.tools.util.MCMValueCapability;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class MCMValueProvider implements ICapabilityProvider{

	@CapabilityInject(IMCMValueCapability.class)

	public static final Capability<IMCMValueCapability> MCMValue = null;
	
	public static void registerCapability() {
		CapabilityManager.INSTANCE.register(IMCMValueCapability.class, new MCMValueStorage(), MCMValueCapability::new);
	}

	

	private IMCMValueCapability instance = MCMValue.getDefaultInstance();
	

	

	@Override

	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)

	{

		// TODO Auto-generated method stub

		if (cap == MCMValue)

		{

			return LazyOptional.of(() -> instance).cast();

		}

		else

		{

			return LazyOptional.empty();

		}

	}
}
