package me.asuramagica.gui;

import static me.asuramagica.blocks.ModBlocks.TempContainer;

import me.asuramagica.blocks.ModBlocks;
import me.asuramagica.blocks.tileentities.Mana_StoneTile;
import me.asuramagica.lists.ItemList;
import me.asuramagica.tools.CustomEnergyStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TemperatureContainer extends Container{

	protected TemperatureContainer(ContainerType<?> type, int id) {
		super(type, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		// TODO Auto-generated method stub
		return false;
	}
	
}