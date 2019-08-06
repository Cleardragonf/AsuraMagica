package me.asuramagica.blocks.inventory;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;

import static me.asuramagica.blocks.ModBlocks.MCMCONTAINER;
import me.asuramagica.blocks.ModBlocks;
import me.asuramagica.blocks.tileentities.MCM_Tile;
import me.asuramagica.lists.ItemList;
import me.asuramagica.tools.CustomEnergyStorage;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

//The Container is practically the actuall "math behind the scene of a GUI" //Server Side kind of stuff...
public class MCM_Container extends Container{
	
	private TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	
	public MCM_Container(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player ) {
		super(MCMCONTAINER, windowId);
		
		
		//TESTING
		
		
		tileEntity = world.getTileEntity(pos);
		MCM_Tile test = (MCM_Tile) tileEntity;
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(playerInventory);
		//Machine Slots (Mana Insertions and their phyical locations)
			addSlot(new SlotItemHandler(test.inventory,0,8,19));
			addSlot(new SlotItemHandler(test.inventory,1,56,19));
			//addSlot(new SlotItemHandler(h,1,56,19));
			addSlot(new SlotItemHandler(test.inventory,2,104,19));
			addSlot(new SlotItemHandler(test.inventory,3,152,19));

			//addSlotBox(test.inventory, 4, 8, 140, 9, 18, 4, 18);
			addSlotBox(test.inventory, 4,8,86,9,18,1,18);
			addSlotBox(test.inventory, 13,8,104,9,18,1,18);
			addSlotBox(test.inventory, 22,8,122,9,18,1,18);
			addSlotBox(test.inventory, 31,8,140,9,18,1,18);
			addSlotBox(test.inventory, 40,8,158,9,18,1,18);
			addSlotBox(test.inventory, 49,8,176,9,18,1,18);
			
			layoutPlayerInventorySlots(8, 140);
		
		//energy storage
		func_216958_a(new IntReferenceHolder() {
			
			@Override
			public void set(int value) {
				tileEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> ((CustomEnergyStorage)h).setEnergy(value));
				
			}
			
			@Override
			public int get() {
				return getEnergy();
			}
		});

		
		test = (MCM_Tile) tileEntity;
	}
	
	public Item getItemA() {
		return this.inventorySlots.get(0).inventory.getStackInSlot(0).getItem();
	}
	
	private void func_216958_a(IntReferenceHolder intReferenceHolder) {}

	public int getEnergy() {
		return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
	}
	
	
	public IEnergyStorage getWater() {
		MCM_Tile test = (MCM_Tile) tileEntity;
		return test.waterEnergy;
	}
	public IEnergyStorage getFire() {
		MCM_Tile test = (MCM_Tile) tileEntity;
		return test.fireEnergy;
	}
	public IEnergyStorage getEarth() {
		MCM_Tile test = (MCM_Tile) tileEntity;
		return test.earthEnergy;
	}
	public IEnergyStorage getWind() {
		MCM_Tile test = (MCM_Tile) tileEntity;
		return test.windEnergy;
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ModBlocks.MCMBlock);
	} 

	public void addSlot(IItemHandler handler, int index, int x, int y) {
		addSlot(new SlotItemHandler(handler, index, x, y));
	}
	
	private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
		for(int i = 0 ; i < amount; i++) {
			addSlot(handler, index, x, y);
			x += dx;
			index++;
		}
		return index;
	}
	
	private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
		for(int j = 0 ; j < verAmount; j++) {
			index = addSlotRange(handler, index, x, y, horAmount, dx);
		}
		return index;
	}
	
	private void layoutPlayerInventorySlots(int leftCol, int topRow) {
		//player inventory
		//addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
		
		//player hotbar
		topRow = 198;
		addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if(slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();
			if(index == 0) {
				if(!this.mergeItemStack(stack, 1, 37, true)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(stack, itemstack);
			}else {
				if(stack.getItem() == ItemList.fire_mana_ore) {
					if(!this.mergeItemStack(stack, 0, 1, false)) {
						return ItemStack.EMPTY;
					}else if(index < 28) {
						if(!this.mergeItemStack(stack, 28, 37, false)) {
							return ItemStack.EMPTY;
						}
					}else if(index  <37 && !this.mergeItemStack(stack, 1, 28, false)) {
						return ItemStack.EMPTY;
					}
				}
			}
			if(stack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			}else {
				slot.onSlotChanged();
			}
			
			if(stack.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			slot.onTake(playerIn, stack);
		}
		return itemstack;
	}
	
	public int getEssence1(int pixels) {
		int i = 0;
		if (i != 0) {
			return this.getEnergy() * pixels /i;
		}
		return this.getEnergy() * 0;
	}
}