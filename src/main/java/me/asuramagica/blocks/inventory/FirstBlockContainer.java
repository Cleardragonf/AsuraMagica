package me.asuramagica.blocks.inventory;

import static me.asuramagica.blocks.ModBlocks.FIRSTBLOCK_CONTAINER;

import me.asuramagica.blocks.ModBlocks;
import me.asuramagica.blocks.tileentities.FirstBlockTile;
import me.asuramagica.lists.ItemList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class FirstBlockContainer extends Container{

	private TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	
	public FirstBlockContainer(int windowId, World world, BlockPos pos, PlayerInventory inv, PlayerEntity player) {
		super(FIRSTBLOCK_CONTAINER, windowId);
		tileEntity = world.getTileEntity(pos);
		tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper(inv);
		FirstBlockTile test = (FirstBlockTile) tileEntity;

		//tiles inventory

			addSlot(new SlotItemHandler(test.createHandler(), 0, 17, 17));
			addSlot(new SlotItemHandler(test.createHandler(), 1, 30, 17));

		
		//players inventory
		LayoutPlayerEnventorySlots(8, 174);
	}
	
	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(IWorldPosCallable.of( tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ModBlocks.FIRSTBLOCK);
	}
	
	private void addSlot(IItemHandler handler, int index, int x, int y) {
		addSlot(new SlotItemHandler(handler, index, x, y));
	}
	
	private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
		for(int i = 0 ; i < amount ; i++) {
			addSlot(handler, index, x, y);
			x += dx;
			index++;
		}
		return index;
	}
	
	private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
		for(int j = 0; j < verAmount ; j++) {
			index = addSlotRange(handler, index, x, y, horAmount, dx);
			y += dy;
		}
		return index;
	}
	
	private void LayoutPlayerEnventorySlots(int leftCol, int topRow) {
		addSlotBox(playerInventory, 9, leftCol, topRow, 9,18,3,18);
		
		topRow += 58;
		addSlotRange(playerInventory, 0, leftCol, topRow, 9,18);
	}
	
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
	
	
}
