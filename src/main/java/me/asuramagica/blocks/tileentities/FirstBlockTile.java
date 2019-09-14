package me.asuramagica.blocks.tileentities;

import static me.asuramagica.blocks.ModBlocks.FIRSTBLOCKTILE;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.asuramagica.blocks.inventory.FirstBlockContainer;
import me.asuramagica.tools.CustomEnergyStorage;
import me.asuramagica.tools.util.MCMValueCapability.MCMValueProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
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
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class FirstBlockTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider{

	public final CustomEnergyStorage earthEnergy = new CustomEnergyStorage(100000, 0); 
	
	public FirstBlockTile() {
		super(FIRSTBLOCKTILE);
	}
	public int tick = 0;
	public int slotAType = 0;
	public int slotBType = 0;
	public int slotCType = 0;
	public int slotDType = 0;
	
	@Override
	public void tick() {
		this.earthEnergy.addEnergy(10000);		
		if(tick == 20) {
        	//System.out.println(this.earthEnergy.getEnergyStored() + " " + this.fireEnergy.getEnergyStored() + " " + this.waterEnergy.getEnergyStored() + " " + this.windEnergy.getEnergyStored());
    		if(!(this.getStackinSlot(0).isEmpty())) {
    			convertMatterIntoItem(0);
    		}
    		if(!(this.getStackinSlot(1).isEmpty())){
    			convertMatterIntoItem(1);
    		}
    		if(!(this.getStackinSlot(2).isEmpty())) {
    			convertMatterIntoItem(2);
    		}
    		if(!(this.getStackinSlot(3).isEmpty())) {
    			convertMatterIntoItem(3);
    		}
    		tick = 0;
         }
        tick++;
	}
	
	public ItemStack getStackinSlot(int index) {
		return(ItemStack)this.inventory.getStackInSlot(index);
	}

	public void convertMatterIntoItem(int slotNumber) {
		
        
        ItemStack Goal = this.getStackinSlot(slotNumber).getStack();
        ItemStack stack = new ItemStack(inventory.getStackInSlot(slotNumber).getStack().getItem());
        Item mcmValueItem = stack.getItem();
        if(slotNumber == 0) {
        	this.getStackinSlot(slotNumber).getStack().getCapability(MCMValueProvider.MCMValue).ifPresent(h ->{
            	CustomEnergyStorage energyType;
            	switch (slotAType) {
    				case 0:
    					energyType= this.earthEnergy;
    					break;
    					
    				default:
    					energyType= this.earthEnergy;
    					break;
    			}
    			if(h.mcmValue() <= energyType.getEnergyStored()) {
    				for(int i = 4; i< 58;i++) {

    	                if(this.inventory.getStackInSlot(i).isEmpty()) {
    	                	stack.setCount(this.inventory.getStackInSlot(i).getCount() + 1);
    	                    inventory.setStackInSlot(i, stack);
    	                    break;
    	                }else {
    	                	if(this.inventory.getStackInSlot(i).getStack().getItem().equals(mcmValueItem)) {
    	                    	stack.setCount(this.inventory.getStackInSlot(i).getCount() + 1);
    	                    	inventory.setStackInSlot(i, stack);
    	                    	break;
    	                	}

    	                }
    	            }
            	}
    			energyType.consumeEnergy(h.mcmValue());
            });
        }
        else if(slotNumber == 1) {
        	this.getStackinSlot(slotNumber).getStack().getCapability(MCMValueProvider.MCMValue).ifPresent(h ->{
            	CustomEnergyStorage energyType;
            	switch (slotBType) {
    				case 0:
    					energyType= this.earthEnergy;
    					break;
    					
    				default:
    					energyType= this.earthEnergy;
    					break;
    			}
    			if(h.mcmValue() <= energyType.getEnergyStored()) {
    				for(int i = 4; i< 58;i++) {

    	                if(this.inventory.getStackInSlot(i).isEmpty()) {
    	                	stack.setCount(this.inventory.getStackInSlot(i).getCount() + 1);
    	                    inventory.setStackInSlot(i, stack);
    	                    break;
    	                }else {
    	                	if(this.inventory.getStackInSlot(i).getStack().getItem().equals(mcmValueItem)) {
    	                    	stack.setCount(this.inventory.getStackInSlot(i).getCount() + 1);
    	                    	inventory.setStackInSlot(i, stack);
    	                    	break;
    	                	}

    	                }
    	            }
            	}
    			energyType.consumeEnergy(h.mcmValue());
            });
        }
        if(slotNumber == 2) {
        	this.getStackinSlot(slotNumber).getStack().getCapability(MCMValueProvider.MCMValue).ifPresent(h ->{
            	CustomEnergyStorage energyType;
            	switch (slotCType) {
    				case 0:
    					energyType= this.earthEnergy;
    					break;
    	
    				default:
    					energyType= this.earthEnergy;
    					break;
    			}
    			if(h.mcmValue() <= energyType.getEnergyStored()) {
    				for(int i = 4; i< 58;i++) {

    	                if(this.inventory.getStackInSlot(i).isEmpty()) {
    	                	stack.setCount(this.inventory.getStackInSlot(i).getCount() + 1);
    	                    inventory.setStackInSlot(i, stack);
    	                    break;
    	                }else {
    	                	if(this.inventory.getStackInSlot(i).getStack().getItem().equals(mcmValueItem)) {
    	                    	stack.setCount(this.inventory.getStackInSlot(i).getCount() + 1);
    	                    	inventory.setStackInSlot(i, stack);
    	                    	break;
    	                	}

    	                }
    	            }
            	}
    			energyType.consumeEnergy(h.mcmValue());
            });
        }
        if(slotNumber == 3) {
        	this.getStackinSlot(slotNumber).getStack().getCapability(MCMValueProvider.MCMValue).ifPresent(h ->{
            	CustomEnergyStorage energyType;
            	switch (slotDType) {
    				case 0:
    					energyType= this.earthEnergy;
    					break;
    	
    				default:
    					energyType= this.earthEnergy;
    					break;
    			}
    			if(h.mcmValue() <= energyType.getEnergyStored()) {
    				for(int i = 4; i< 58;i++) {

    	                if(this.inventory.getStackInSlot(i).isEmpty()) {
    	                	stack.setCount(this.inventory.getStackInSlot(i).getCount() + 1);
    	                    inventory.setStackInSlot(i, stack);
    	                    break;
    	                }else {
    	                	if(this.inventory.getStackInSlot(i).getStack().getItem().equals(mcmValueItem)) {
    	                    	stack.setCount(this.inventory.getStackInSlot(i).getCount() + 1);
    	                    	inventory.setStackInSlot(i, stack);
    	                    	break;
    	                	}

    	                }
    	            }
            	}
    			energyType.consumeEnergy(h.mcmValue());
            });
        }
    }
		

	
	@SuppressWarnings("unchecked")
	@Override
	public void read(CompoundNBT tag) {
		super.read(tag);
		readRestorableNBT(tag);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT tag) {
		super.write(tag);
		tag.put("inv", inventory.serializeNBT());

		tag.putInt("earth", this.earthEnergy.getEnergyStored());

		return tag;
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

	public static class CustomEnergyStorage extends EnergyStorage{
		public CustomEnergyStorage(final int capacity) {

			super(capacity);

		}
		
		public CustomEnergyStorage(final int capacity, final int maxTransfer) {

			super(capacity, maxTransfer);

		}



		public CustomEnergyStorage(final int capacity, final int maxReceive, final int maxExtract) {

			super(capacity, maxReceive, maxExtract);

		}


		public void addEnergy(int energy) {
			this.energy += energy;
			if(this.energy > getMaxEnergyStored()) {
				this.energy = getEnergyStored();
			}
		}
		
		public void consumeEnergy(int energy) {
			this.energy -= energy;
			if (this.energy < 0) {
				this.energy = 0;
			}
		}
		
		public CustomEnergyStorage(final int capacity, final int maxReceive, final int maxExtract, final int energy) {

			super(capacity, maxReceive, maxExtract, energy);

		}



		public int setEnergyStored(final int maxSet) {

			final int energyReceived = Math.min(this.capacity - this.energy, maxSet);

			this.energy += energyReceived;

			return energyReceived;

		}



	}
	

	public void readRestorableNBT(CompoundNBT tag) {
			this.inventory.deserializeNBT(tag.getCompound("inv"));
			
			this.earthEnergy.setEnergyStored(tag.getInt("earth"));
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

	
