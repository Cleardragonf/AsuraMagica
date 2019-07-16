package me.asuramagica.blocks;

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
import net.minecraftforge.common.capabilities.Capability;
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
import me.asuramagica.lists.BlockList;
import me.asuramagica.lists.ItemList;
import me.asuramagica.tools.CustomEnergyStorage;

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
	public final IEnergyStorage waterEnergy = new CustomEnergyStorage(100000, 0);
	public final IEnergyStorage fireEnergy = new CustomEnergyStorage(100000, 0); 	
	public final IEnergyStorage earthEnergy = new CustomEnergyStorage(100000, 0); 	
	public final IEnergyStorage windEnergy = new CustomEnergyStorage(100000, 0); 	


	private final LazyOptional<IItemHandler> inventoryOptional = LazyOptional.of(() -> this.inventory).cast();
    private final LazyOptional<IEnergyStorage> waterSource = LazyOptional.of(() -> this.waterEnergy).cast();
    private final LazyOptional<IEnergyStorage> fireSource = LazyOptional.of(() -> this.fireEnergy).cast();
    private final LazyOptional<IEnergyStorage> earthSource = LazyOptional.of(() -> this.earthEnergy).cast();
    private final LazyOptional<IEnergyStorage> windSource = LazyOptional.of(() -> this.windEnergy).cast();
    
	public Mana_StoneTile() {
		super(MANASTONETILE);
	}

    
	public List<Block> testing = new LinkedList<>();
	
	
	@SuppressWarnings("unused")
	@Override
	public void tick() {
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
		
		fireSource.ifPresent(h -> {			
			if(h.getEnergyStored() >= 100000) {
				
			}else {
					((CustomEnergyStorage)fireEnergy).addFireEssence(fireryBlocksFound * multiBlocksFound);
					this.markDirty();
			}
		});
		
		//Create a Tick Ratio for Water Manna Collection
		waterSource.ifPresent(e -> {
			if(e.getEnergyStored() >= 100000) {
				
			}else {
				((CustomEnergyStorage)waterEnergy).addFireEssence(wateryBlocksFound * multiBlocksFound);
				this.markDirty();
			}
		
		
		});
		
		earthSource.ifPresent(h -> {			
			if(h.getEnergyStored() >= 100000) {
				
			}else {				
					((CustomEnergyStorage)earthEnergy).addFireEssence(earthyBlocksFound * multiBlocksFound);
					this.markDirty();
			}
		});
		
		windSource.ifPresent(h -> {			
			if(h.getEnergyStored() >= 100000) {
				
			}else {
					((CustomEnergyStorage)windEnergy).addFireEssence(windyBlocksFound * multiBlocksFound);
					this.markDirty();
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
		return 0;
	}
	
	private void sendOutPower() {
		final IEnergyStorage fireEnergy = this.fireEnergy;
		final int energyStored = fireEnergy.getEnergyStored();
		if (energyStored > 0) {
			final World world = this.world;
			if (world == null) {
				return;
			}
			final BlockPos pos = this.pos;
			// TODO: change Direction.values to Direction.VALUES once its ATed
			for (Direction direction : Direction.values()) {
				final TileEntity te = world.getTileEntity(pos.offset(direction));
				if (te == null) {
					continue;
				}
				te.getCapability(CapabilityEnergy.ENERGY, direction).ifPresent(teEnergy -> {
					if (!teEnergy.canReceive()) {
						return;
					}
					fireEnergy.extractEnergy(
							teEnergy.receiveEnergy(
									fireEnergy.extractEnergy(100, true),
									false
							),
							false
					);
				});
			}
			if (energyStored != fireEnergy.getEnergyStored()) {
				markDirty();
			}
		}
	}

	@Override
	public void read(CompoundNBT tag) {
		inventory.deserializeNBT(tag.getCompound("inv"));
		super.read(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		tag.put("inv", inventory.serializeNBT());
		return super.write(tag);
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