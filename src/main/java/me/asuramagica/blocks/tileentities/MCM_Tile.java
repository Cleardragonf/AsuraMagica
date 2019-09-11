package me.asuramagica.blocks.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import me.asuramagica.blocks.tileentities.Mana_StoneTile;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import static me.asuramagica.blocks.ModBlocks.MCMTILE;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.asuramagica.blocks.Mana_Stone;
import me.asuramagica.blocks.inventory.MCM_Container;
import me.asuramagica.lists.BlockList;
import me.asuramagica.lists.ItemList;
import me.asuramagica.tools.CustomEnergyStorage;
import me.asuramagica.tools.TemperatureContainer;
import me.asuramagica.tools.util.MCMValueCapability.MCMValueCapability;
import me.asuramagica.tools.util.MCMValueCapability.MCMValueProvider;

public class MCM_Tile extends TileEntity implements ITickableTileEntity, INamedContainerProvider{

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
			MCM_Tile.this.markDirty();
		}
	};	
	
	
	public final CustomEnergyStorage waterEnergy = new CustomEnergyStorage(100000, 0);
	public final CustomEnergyStorage fireEnergy = new CustomEnergyStorage(100000, 0); 	
	public final CustomEnergyStorage earthEnergy = new CustomEnergyStorage(100000, 0); 	
	public final CustomEnergyStorage windEnergy = new CustomEnergyStorage(100000, 0); 
	public int slotAType = 0;
	public int slotBType = 0;
	public int slotCType = 0;
	public int slotDType = 0;
	private CompoundNBT test = new CompoundNBT();
    
	public MCM_Tile() {
		super(MCMTILE);
	}
	public int tick = 0;
    
	public List<Block> testing = new LinkedList<>();
	/*
	public BlockPos linkedPowerSource;
	
	public BlockPos getLinkedPower() {
		return linkedPowerSource;
	}
	*/
	@Override
	public void tick() {
		//if(linkedPowerSource != null) {
		//	System.out.println(this.linkedPowerSource.toString());
		//}
		
		try (PooledMutableBlockPos pooledMutableBlockPos = PooledMutableBlockPos.retain()) {
			final int posX = pos.getX();
			final int posY = pos.getY();
			final int posZ = pos.getZ();

			for (int z = -5; z <= 10; ++z) {
				for (int x = -5; x <= 10; ++x) {
					for (int y = -5; y <= 10; ++y) {
						final int dist = (x * x) + (y * y) + (z * z);
						if (dist > 25) {
							continue;
						}
						if (dist < 1) {
							continue;
						}
						pooledMutableBlockPos.setPos(posX + x, posY + y, posZ + z);
						final BlockState blockState = world.getBlockState(pooledMutableBlockPos);
						//final IFluidState fluidState = world.getFluidState(pooledMutableBlockPos);
						final Block block = blockState.getBlock();
						TileEntity test = world.getTileEntity(pooledMutableBlockPos);

						if (block.getClass().equals(Mana_Stone.class)){
							Mana_StoneTile test2 = (Mana_StoneTile) test;
							if(test2.earthEnergy.getEnergyStored() > 0 && this.earthEnergy.getEnergyStored() < 100000) {
								test2.earthEnergy.consumeEnergy(50);
								this.earthEnergy.addEnergy(50);
							}
							if(test2.fireEnergy.getEnergyStored() > 0 && this.fireEnergy.getEnergyStored() < 100000) {
								test2.fireEnergy.consumeEnergy(50);
								this.fireEnergy.addEnergy(50);
							}
							if(test2.waterEnergy.getEnergyStored() > 0 && this.waterEnergy.getEnergyStored() < 100000) {
								test2.waterEnergy.consumeEnergy(50);
								this.waterEnergy.addEnergy(50);
							}
							if(test2.windEnergy.getEnergyStored() > 0 && this.windEnergy.getEnergyStored() < 100000) {
								test2.windEnergy.consumeEnergy(50);
								this.windEnergy.addEnergy(50);
							}
							
						}
						else {
							
						}
						
					}
				}

			}
		}
		//TODO Add a MCMValue Check using the Items Capability MCMValue...to then subract that from the mcm.storedEnergy()...
		//TODO Lookinto combining the four elements into one...for mcm purposes...
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
    				case 1:
    					energyType= this.fireEnergy;
    					break;
    				case 2:
    					energyType= this.waterEnergy;
    					break;
    				case 3:
    					energyType= this.windEnergy;
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
    				case 1:
    					energyType= this.fireEnergy;
    					break;
    				case 2:
    					energyType= this.waterEnergy;
    					break;
    				case 3:
    					energyType= this.windEnergy;
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
    				case 1:
    					energyType= this.fireEnergy;
    					break;
    				case 2:
    					energyType= this.waterEnergy;
    					break;
    				case 3:
    					energyType= this.windEnergy;
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
    				case 1:
    					energyType= this.fireEnergy;
    					break;
    				case 2:
    					energyType= this.waterEnergy;
    					break;
    				case 3:
    					energyType= this.windEnergy;
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
		
	
	public ItemStack getStackinSlot(int index) {
		return(ItemStack)this.inventory.getStackInSlot(index);
	}
	
	@Override
	public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new MCM_Container(i, world, pos, playerInventory, playerEntity);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {


		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return inventoryOptional.cast();
		}
		return super.getCapability(cap, side);
	}
	

	@Override

	public void read(CompoundNBT tag) {
		super.read(tag);
		readRestorableNBT(tag);
		
	}



	@Override

	public CompoundNBT write(CompoundNBT tag) {
		super.write(tag);
		tag.put("inv", inventory.serializeNBT());

		tag.putInt("slotAType", this.slotAType);
		
		tag.putInt("slotBType", this.slotBType);
		
		tag.putInt("slotCType", this.slotCType);
		
		tag.putInt("slotDType", this.slotDType);

		return tag;

	}

	public void readRestorableNBT(CompoundNBT tag) {
			this.inventory.deserializeNBT(tag.getCompound("inv"));
			
			this.slotAType = tag.getInt("slotAType");
			
			this.slotBType = tag.getInt("slotBType");
			
			this.slotCType = tag.getInt("slotCType");
			
			this.slotDType = tag.getInt("slotDType");
		
	}


	
}