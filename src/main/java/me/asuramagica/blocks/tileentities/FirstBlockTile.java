package me.asuramagica.blocks.tileentities;

import static me.asuramagica.blocks.ModBlocks.FIRSTBLOCKTILE;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.asuramagica.blocks.inventory.FirstBlockContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class FirstBlockTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider{

	
	public FirstBlockTile() {
		super(FIRSTBLOCKTILE);
	}
	
	@Override
	public void tick() {
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void read(CompoundNBT tag) {
		CompoundNBT inv = tag.getCompound("inv");
		inventoryOptional.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(inv));
		super.read(tag);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT tag) {
		inventoryOptional.ifPresent(h -> {
			@SuppressWarnings("unchecked")
			CompoundNBT compound = ((INBTSerializable<CompoundNBT>)h).serializeNBT();
			tag.put("inv", compound);
		});
		return super.write(tag);
	}
	
	public IItemHandler createHandler() {
		return  new ItemStackHandler(300) {
			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				return stack.getItem() == Items.DIAMOND;
			}
			
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				if(stack.getItem() != Items.DIAMOND) {
					return stack;
				}
				return super.insertItem(slot, stack, simulate);
			}
		};
	}
	@Nonnull	
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		// TODO Auto-generated method stub
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			//return handler.cast();
			return inventoryOptional.cast();
		}
		return super.getCapability(cap, side);
	}
	
	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getType().getRegistryName().getPath());
	}
	@Override
	public Container createMenu(int i, PlayerInventory inv, PlayerEntity player) {
		return new FirstBlockContainer(i, world, pos, inv, player);
	}

	public final LazyOptional<IItemHandler> inventoryOptional = LazyOptional.of(() -> this.inventory).cast();
	public final ItemStackHandler inventory = new ItemStackHandler(300) {


		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (stack.getItem() == null) {
				return stack;
			}
			return super.insertItem(slot, stack, simulate);
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			return stack.getItem() != null;
		}

		@Override
		protected void onContentsChanged(int slot) {
			FirstBlockTile.this.markDirty();
		}
	};	
	

}
