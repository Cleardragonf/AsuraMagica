package me.asuramagica.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import static me.asuramagica.blocks.ModBlocks.MANASTONETILE;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.asuramagica.lists.BlockList;
import me.asuramagica.lists.ItemList;
import me.asuramagica.tools.CustomEnergyStorage;

public class Mana_StoneTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider{
	public Mana_StoneTile() {
		super(MANASTONETILE);
	}
	
	private LazyOptional<IItemHandler> handler = LazyOptional.of(this::createHandler).cast();
	private LazyOptional<IEnergyStorage> fireSource = LazyOptional.of(this::createEnergy).cast();
	
	private IEnergyStorage waterEnergy = new CustomEnergyStorage(100000, 0);
    private LazyOptional<IEnergyStorage> waterEnergyOptional = LazyOptional.of(() -> waterEnergy);
    
	private static  int fireEntityList;
	private static  int multiplierList;
	public List<Block> testing = new LinkedList<>();
	private int waterEntityList;
	
	
	@SuppressWarnings("unused")
	@Override
	public void tick() {
		fireEntityList = 0;
		waterEntityList = 0;
		multiplierList = 1;
		testing.clear();
		
		for (int x = -5; x < 10; x++) {
            for (int y = -5; y < 10; y++) {
                for (int z = -5; z < 10; z++)
                {
                    
                    Double newSpawnX = Double.valueOf(pos.getX() + x);
                    Double newSpawnY = Double.valueOf(pos.getY() + y);
                    Double newSpawnZ = Double.valueOf(pos.getZ() + z);
                    if (Math.pow(newSpawnX.doubleValue() - pos.getX(), 2.0D) + Math.pow(newSpawnY.doubleValue() - pos.getY(), 2.0D) + Math.pow(newSpawnZ.doubleValue() - pos.getZ(), 2.0D) <= Math.pow(5,2.0D))
                    {
                        if (Math.pow(newSpawnX.doubleValue() - pos.getX(), 2.0D) + Math.pow(newSpawnY.doubleValue() - pos.getY(), 2.0D) + Math.pow(newSpawnZ.doubleValue() - pos.getZ(), 2.0D) >= Math.pow(1,2.0D)){
                            World world = this.getWorld();
                            BlockPos newSpawnLocation = new BlockPos(newSpawnX.doubleValue(), newSpawnY.doubleValue(), newSpawnZ.doubleValue());
                            testing.add(world.getBlockState(newSpawnLocation).getBlock());
                            
                        }

                    }
                }
            }

        }	
		
		fireSource.ifPresent(h -> {			
			if(h.getEnergyStored() >= 100000) {
				
			}else {
				
				//TODO:: Add A Block Identifier to look all around this entity for...the more blocks...the larger the amount...the faster it creates more		
				
				//numbering all Fire Entities to gain Mana from
				//numbering All Fire Foci to multiply mana for
				for(Block e : testing) {
					if(e == Blocks.FIRE) {
						fireEntityList += 1;
					}else if(e == BlockList.fire_mana_ore){
						fireEntityList += 1;
					}else if(e == BlockList.mana_foci_crystal){
						multiplierList += 1;
					}
                }
					((CustomEnergyStorage)h).addFireEssence(fireEntityList);
					markDirty();
			}
		});
		
		//Create a Tick Ratio for Water Manna Collection
		waterEnergyOptional.ifPresent(e -> {
			if(e.getEnergyStored() >= 100000) {
				
			}else {
				for(Block a : testing) {
					if(a == Blocks.WATER) {
						waterEntityList += 1;
					}else if(a == BlockList.water_mana_ore){
						waterEntityList += 1;
					}else if(a == BlockList.mana_foci_crystal){
						multiplierList += 1;
					}
                }
				((CustomEnergyStorage)e).addFireEssence(waterEntityList);
			}
		
		
		});

		sendOutPower();
		
		//energy.ifPresent(e -> ((CustomEnergyStorage)e).addEarthEssence(10));
		
		/*(
		if(counter > 20) {
			energy.ifPresent(e -> ((CustomEnergyStorage)e).addEarthEssence(10));
			counter = 0;
		}else {
			counter++;
		}
		*/
		
		//TODO: Move the below around so that Energy can be REMOVED via the item slot...
		/*//the below is used for taking items out and putting them into energy...
		if(counter > 0) {
			counter --;
			if (counter <= 0) {
				energy.ifPresent(e -> ((CustomEnergyStorage)e).addEnergy(10));
			}
		}else {
			handler.ifPresent(h -> {
				ItemStack stack = h.getStackInSlot(0);
				if(stack.getItem() == ItemList.fire_mana_ore) {
					h.extractItem(0, 1, false);
					counter = 20;
				}
			});
		}
		*/
	}

	public static int getSources() {
		return fireEntityList;
	}
	
	private void sendOutPower() {
		fireSource.ifPresent(energy ->{
			AtomicInteger capacity = new AtomicInteger(energy.getEnergyStored());
			if (capacity.get() > 0) {
				for(Direction direction: Direction.values()) {
					TileEntity te = world.getTileEntity(pos.offset(direction));
					if(te != null) {
						te.getCapability(CapabilityEnergy.ENERGY, direction).filter(handler -> {
							if(handler.canReceive()) {
								int received = handler.receiveEnergy(Math.min(capacity.get(),  100),  false);
								capacity.addAndGet(-received);
								energy.extractEnergy(received,  false);
								((CustomEnergyStorage)energy).consumeEnergy(received);
								markDirty();
								return capacity.get() > 0;
							}
							return false;
						});
					}
				}		
			}
		});
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(CompoundNBT tag) {
		CompoundNBT invTag = tag.getCompound("inv");
		handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(invTag));
		
		CompoundNBT energyTag = tag.getCompound("energy");
		fireSource.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(energyTag));
		
		super.read(tag);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CompoundNBT write(CompoundNBT tag) {
		handler.ifPresent(h -> {
			CompoundNBT compound = ((INBTSerializable<CompoundNBT>)h).serializeNBT();
			tag.put("inv", compound);
		});
		fireSource.ifPresent(h -> {
			CompoundNBT compound2 = ((INBTSerializable<CompoundNBT>)h).serializeNBT();
			tag.put("energy", compound2);
		});
		
		return super.write(tag);
	}
	
	private ItemStackHandler createHandler() {
			return new ItemStackHandler(1) {
				
				@Override
				protected void onContentsChanged(int slot) {
					markDirty();
				}
				
				@Override
				public boolean isItemValid(int slot, ItemStack stack) {
					return stack.getItem() == ItemList.fire_mana_ore;
				}
				
				@Override
				public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
					if(stack.getItem() != ItemList.fire_mana_ore) {
						return stack;
					}
					return super.insertItem(slot, stack, simulate);
				}
			};
	}

	private IEnergyStorage createEnergy() {
		return new CustomEnergyStorage(100000, 0);
	}
	
	public int getWaterEnergy() {
		return this.waterEnergy.getEnergyStored();
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {


		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handler.cast();
		}
		if(cap == CapabilityEnergy.ENERGY) {
			return fireSource.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new ManaStoneContainer(i, world, pos, playerInventory, playerEntity);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent(getType().getRegistryName().getPath());
	}

	public static int getAmplifiers() {
		return multiplierList;
	}
	

	
	
}