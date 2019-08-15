package me.asuramagica.blocks.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import static me.asuramagica.blocks.ModBlocks.MANASTONETILE;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.asuramagica.blocks.inventory.ManaStoneContainer;
import me.asuramagica.lists.BlockList;
import me.asuramagica.lists.ItemList;
import me.asuramagica.tools.CustomEnergyStorage;
import me.asuramagica.tools.util.EnergyTypePacket;
import me.asuramagica.tools.util.EnergyTypePacketHandler;
import me.asuramagica.tools.util.Packets.ManaStone.ManaEnergyPacket;

public class Mana_StoneTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider{

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
			Mana_StoneTile.this.markDirty();
		}
	};	
	public final CustomEnergyStorage waterEnergy = new CustomEnergyStorage(100000, 0);
	public final CustomEnergyStorage fireEnergy = new CustomEnergyStorage(100000, 0); 	
	public final CustomEnergyStorage earthEnergy = new CustomEnergyStorage(100000, 0); 	
	public final CustomEnergyStorage windEnergy = new CustomEnergyStorage(100000, 0); 	
	
	public List<CustomEnergyStorage> energyList = new LinkedList<CustomEnergyStorage>();


	public final LazyOptional<IItemHandler> inventoryOptional = LazyOptional.of(() -> this.inventory).cast();
	public final LazyOptional<CustomEnergyStorage> waterSource = LazyOptional.of(() -> this.waterEnergy).cast();
    public final LazyOptional<CustomEnergyStorage> fireSource = LazyOptional.of(() -> this.fireEnergy).cast();
    public final LazyOptional<CustomEnergyStorage> earthSource = LazyOptional.of(() -> this.earthEnergy).cast();
    public final LazyOptional<CustomEnergyStorage> windSource = LazyOptional.of(() -> this.windEnergy).cast();
    
	public Mana_StoneTile() {
		super(MANASTONETILE);
		energyList.add(earthEnergy);
		energyList.add(fireEnergy);
		energyList.add(waterEnergy);
		energyList.add(windEnergy);
	}

    
	public List<Block> testing = new LinkedList<>();
	
	
	@SuppressWarnings("unused")
	@Override
	public void tick() {
		EnergyTypePacketHandler.CHANNEL.sendToServer(new ManaEnergyPacket(pos, DimensionType.OVERWORLD, waterEnergy.getEnergyStored()));
		System.out.println("water: " + waterEnergy.getEnergyStored());
		int findFireryBlocks = 0;
		int findWateryBlocks= 0;
		int findEarthyBlocks = 0;
		int findWindyBlocks = 0;
		int findMultiplierBlocks = 1;
		
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
						final IFluidState fluidState = world.getFluidState(pooledMutableBlockPos);
						final Block block = blockState.getBlock();
						if (block instanceof FireBlock ||
								block == Blocks.FIRE ||
								block == BlockList.fire_mana_ore ||
								(!fluidState.isEmpty() && fluidState.isTagged(FluidTags.LAVA))
						) {
							++findFireryBlocks;
						} else if (block == BlockList.water_mana_ore ||
								(!fluidState.isEmpty() && fluidState.isTagged(FluidTags.WATER))
						) {
							++findWateryBlocks;
						} else if (block == BlockList.mana_foci_crystal) {
							++findMultiplierBlocks;
						}else if(block == Blocks.GRASS || block == BlockList.earth_mana_ore || block == Blocks.DIRT) {
							++findEarthyBlocks;
						}else if(block == BlockList.wind_mana_ore) {
							++findWindyBlocks;
						}
					}
				}

			}
		}
		
		final int fireryBlocksFound = findFireryBlocks;
		final int wateryBlocksFound = findWateryBlocks;
		final int earthyBlocksFound = findEarthyBlocks;
		final int windyBlocksFound = findWindyBlocks;
		final int multiBlocksFound = findMultiplierBlocks;
		boolean dirty = false;
		
			if(this.fireEnergy.getEnergyStored() >= 100000) {
				
			}else {
					((CustomEnergyStorage)fireEnergy).addEnergy(fireryBlocksFound * multiBlocksFound);
					this.markDirty();
			}
		
		//Create a Tick Ratio for Water Manna Collection
			if(this.waterEnergy.getEnergyStored() >= 100000) {
				
			}else {
				EnergyTypePacketHandler.CHANNEL.sendToServer(new ManaEnergyPacket(pos, DimensionType.OVERWORLD, waterEnergy.getEnergyStored()));
				((CustomEnergyStorage)waterEnergy).addEnergy(wateryBlocksFound * multiBlocksFound);
				EnergyTypePacketHandler.CHANNEL.sendToServer(new ManaEnergyPacket(pos, DimensionType.OVERWORLD, waterEnergy.getEnergyStored()));
				this.markDirty();
				EnergyTypePacketHandler.CHANNEL.sendToServer(new ManaEnergyPacket(pos, DimensionType.OVERWORLD, waterEnergy.getEnergyStored()));
			}
		
			if(this.earthEnergy.getEnergyStored() >= 100000) {
				
			}else {				
					((CustomEnergyStorage)earthEnergy).addEnergy(earthyBlocksFound * multiBlocksFound);
					this.markDirty();
			}
		
			if(this.windEnergy.getEnergyStored() >= 100000) {
				
			}else {
					((CustomEnergyStorage)windEnergy).addEnergy(windyBlocksFound * multiBlocksFound);
					this.markDirty();
			}
		
		
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

	@Override
	public void read(CompoundNBT tag) {		
		inventory.deserializeNBT(tag.getCompound("inv"));
		EnergyTypePacketHandler.CHANNEL.sendToServer(new ManaEnergyPacket(pos, DimensionType.OVERWORLD, waterEnergy.getEnergyStored()));
		//waterEnergy.setEnergy(tag.getInt("energy"));
		waterEnergy.deserializeNBT(tag.getCompound("energy"));
		EnergyTypePacketHandler.CHANNEL.sendToServer(new ManaEnergyPacket(pos, DimensionType.OVERWORLD, waterEnergy.getEnergyStored()));

		super.read(tag);
		EnergyTypePacketHandler.CHANNEL.sendToServer(new ManaEnergyPacket(pos, DimensionType.OVERWORLD, waterEnergy.getEnergyStored()));
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.put("inv", inventory.serializeNBT());
		EnergyTypePacketHandler.CHANNEL.sendToServer(new ManaEnergyPacket(pos, DimensionType.OVERWORLD, waterEnergy.getEnergyStored()));
		//tag.putInt("energy", waterEnergy.getEnergyStored());
		tag.put("energy", waterEnergy.serializeNBT());
		EnergyTypePacketHandler.CHANNEL.sendToServer(new ManaEnergyPacket(pos, DimensionType.OVERWORLD, waterEnergy.getEnergyStored()));
		return super.write(tag);
		
	}



	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {


		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return inventoryOptional.cast();
		}if(cap == CapabilityEnergy.ENERGY) {
			return waterSource.cast();
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
		return 0;
	}
	

	
	
}