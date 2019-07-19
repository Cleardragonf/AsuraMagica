package me.asuramagica.blocks.tileentities;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.ItemStackHandler;
import static me.asuramagica.blocks.ModBlocks.MCMTILE;
import java.util.LinkedList;
import java.util.List;
import me.asuramagica.blocks.inventory.MCM_Container;
import me.asuramagica.lists.ItemList;
import me.asuramagica.tools.CustomEnergyStorage;

public class MCM_Tile extends TileEntity implements ITickableTileEntity, INamedContainerProvider{

	public final ItemStackHandler inventory = new ItemStackHandler(1) {
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (stack.getItem() != ItemList.fire_mana_ore) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return stack.getItem() == ItemList.fire_mana_ore;
		}

		@Override
		protected void onContentsChanged(int slot) {
			MCM_Tile.this.markDirty();
		}
	};	
	public final IEnergyStorage waterEnergy = new CustomEnergyStorage(100000, 0);
	public final IEnergyStorage fireEnergy = new CustomEnergyStorage(100000, 0); 	
	public final IEnergyStorage earthEnergy = new CustomEnergyStorage(100000, 0); 	
	public final IEnergyStorage windEnergy = new CustomEnergyStorage(100000, 0); 	
	
    
	public MCM_Tile() {
		super(MCMTILE);
	}

    
	public List<Block> testing = new LinkedList<>();
	public String linkedPowerSource = null;
	
	public String getLinkedPower() {
		return linkedPowerSource;
	}
	
	@Override
	public void tick() {
		if(linkedPowerSource != null) {
			System.out.println(this.linkedPowerSource.toString());
		}
		
	}


	@Override
	public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new MCM_Container(i, world, pos, playerInventory, playerEntity);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getType().getRegistryName().getPath());
	}


	
	
}