package me.asuramagica.blocks.tileentities;



import me.asuramagica.blocks.inventory.ManaStoneContainer;

import me.asuramagica.lists.BlockList;

import me.asuramagica.lists.ItemList;

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

import net.minecraftforge.energy.EnergyStorage;

import net.minecraftforge.energy.IEnergyStorage;

import net.minecraftforge.items.CapabilityItemHandler;

import net.minecraftforge.items.ItemStackHandler;



import javax.annotation.Nonnull;

import javax.annotation.Nullable;



import static me.asuramagica.blocks.ModBlocks.MANASTONETILE;



public class Mana_StoneTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {



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

			super.onContentsChanged(slot);

			Mana_StoneTile.this.markDirty();

		}

	};

	public final CustomEnergyStorage fireEnergy = new CustomEnergyStorage(100000, 0);

	public final CustomEnergyStorage waterEnergy = new CustomEnergyStorage(100000, 0);

	public final CustomEnergyStorage earthEnergy = new CustomEnergyStorage(100000, 0);

	public final CustomEnergyStorage windEnergy = new CustomEnergyStorage(100000, 0);



	private final LazyOptional<ItemStackHandler> inventoryExternalCapability = LazyOptional.of(() -> this.inventory);

//	private final LazyOptional<CustomEnergyStorage> fireExternalCapability = LazyOptional.of(() -> this.fireEnergy);

//	private final LazyOptional<CustomEnergyStorage> waterExternalCapability = LazyOptional.of(() -> this.waterEnergy);

//	private final LazyOptional<CustomEnergyStorage> earthExternalCapability = LazyOptional.of(() -> this.earthEnergy);

//	private final LazyOptional<CustomEnergyStorage> windExternalCapability = LazyOptional.of(() -> this.windEnergy);



	public Mana_StoneTile() {

		super(MANASTONETILE);

	}



	@Override

	public void tick() {



		final BlockPos tilePos = this.pos;

		final World world = this.world;



		if (world == null) {

			return;

		}



		int fireryBlocksFound = 0;

		int wateryBlocksFound = 0;

		int earthyBlocksFound = 0;

		int windyBlocksFound = 0;

		int multiplierBlocksFound = 0;



		try (PooledMutableBlockPos pooledMutableBlockPos = PooledMutableBlockPos.retain()) {



			final int posX = tilePos.getX();

			final int posY = tilePos.getY();

			final int posZ = tilePos.getZ();



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

							++fireryBlocksFound;

						} else if (block == BlockList.water_mana_ore ||

								(!fluidState.isEmpty() && fluidState.isTagged(FluidTags.WATER))

						) {

							++wateryBlocksFound;

						} else if (block == BlockList.mana_foci_crystal) {

							++multiplierBlocksFound;

						} else if (block == Blocks.GRASS_BLOCK ||

								block == BlockList.earth_mana_ore ||

								block == Blocks.DIRT

						) {

							++earthyBlocksFound;

						} else if (block == BlockList.wind_mana_ore) {

							++windyBlocksFound;

						}

					}

				}



			}

		}



		if (multiplierBlocksFound > 0) {

			fireryBlocksFound *= multiplierBlocksFound;

			wateryBlocksFound *= multiplierBlocksFound;

			earthyBlocksFound *= multiplierBlocksFound;

			windyBlocksFound *= multiplierBlocksFound;

		}



		boolean needsSave = false;



		// receiveEnergy returns the amount of energy that was put into the storage.

		// If any energy was put into any storage, the tile needs to be saved.

		if(this.fireEnergy.getEnergyStored() >= 100000) {
			
		}else {
				((CustomEnergyStorage)fireEnergy).addEnergy(fireryBlocksFound * 1);
				needsSave = true;
		}
	
	//Create a Tick Ratio for Water Manna Collection
		if(this.waterEnergy.getEnergyStored() >= 100000) {
			
		}else {
			((CustomEnergyStorage)waterEnergy).addEnergy(wateryBlocksFound * 1);
			needsSave = true;
		}
	
		if(this.earthEnergy.getEnergyStored() >= 100000) {
			
		}else {				
				((CustomEnergyStorage)earthEnergy).addEnergy(earthyBlocksFound * 1);
				needsSave = true;
		}
	
		if(this.windEnergy.getEnergyStored() >= 100000) {
			
		}else {
				((CustomEnergyStorage)windEnergy).addEnergy(windyBlocksFound * 1);
				needsSave = true;
		}



		// If any energy was sent out, the tile needs to be saved

		// Not that we do NOT use the short-circuit OR operator (||).

		// we use the normal OR operator (|) as we always want to send out all types of energy

		if (sendOutEnergy(this.fireEnergy) | sendOutEnergy(this.waterEnergy) | sendOutEnergy(this.earthEnergy) | sendOutEnergy(this.windEnergy))

			needsSave = true;



		if (needsSave)

			this.markDirty();



	}



	/**

	 * Tries to send out 100 energy out to each side

	 *

	 * @return If any energy was sent out

	 */

	private boolean sendOutEnergy(final IEnergyStorage tileEnergy) {

		final int initialEnergyStored = tileEnergy.getEnergyStored();

		if (initialEnergyStored <= 0) { // Optimisation

			return false;

		}

		final World world = this.world;

		if (world == null) {

			return false;

		}

		final BlockPos pos = this.pos;



		// TODO: change Direction.values() to Direction.VALUES once its ATed

		for (Direction direction : Direction.values()) {

			final TileEntity te = world.getTileEntity(pos.offset(direction));

			if (te == null) {

				continue;

			}

			te.getCapability(CapabilityEnergy.ENERGY, direction).ifPresent(otherTileEnergy -> {

				if (!otherTileEnergy.canReceive()) {

					return;

				}

				tileEnergy.extractEnergy(

						otherTileEnergy.receiveEnergy(

								tileEnergy.extractEnergy(100, true),

								false

						),

						false

				);

			});

		}

		return initialEnergyStored != tileEnergy.getEnergyStored();

	}



	@Override

	public void read(CompoundNBT tag) {

		this.inventory.deserializeNBT(tag.getCompound("inv"));

		this.fireEnergy.setEnergyStored(tag.getInt("fire"));

		this.waterEnergy.setEnergyStored(tag.getInt("water"));

		this.earthEnergy.setEnergyStored(tag.getInt("earth"));

		this.windEnergy.setEnergyStored(tag.getInt("wind"));

		super.read(tag);

	}



	@Override

	public CompoundNBT write(CompoundNBT tag) {

		tag.put("inv", inventory.serializeNBT());

		tag.putInt("fire", this.fireEnergy.getEnergyStored());

		tag.putInt("water", this.waterEnergy.getEnergyStored());

		tag.putInt("earth", this.earthEnergy.getEnergyStored());

		tag.putInt("wind", this.windEnergy.getEnergyStored());

		return super.write(tag);

	}



	@Nonnull

	@Override

	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {

			return inventoryExternalCapability.cast();

//		} else if (cap == CapabilityFireEnergy.FIRE_ENERGY) {

//			return fireExternalCapability.cast();

//		} else if (cap == CapabilityWaterEnergy.WATER_ENERGY) {

//			return waterExternalCapability.cast();

//		} else if (cap == CapabilityEarthEnergy.EARTH_ENERGY) {

//			return earthExternalCapability.cast();

//		} else if (cap == CapabilityWindEnergy.WIND_ENERGY) {

//			return windExternalCapability.cast();

		}

		return super.getCapability(cap, side);

	}



	@Override

	public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {

		return new ManaStoneContainer(i, world, pos, playerInventory, playerEntity);

	}



	@Override

	public ITextComponent getDisplayName() {

		return new StringTextComponent(this.getType().getRegistryName().getPath());

	}



	/**

	 * The exact same as {@link EnergyStorage} but also has a way to directly set the energy stored

	 */

	public static class CustomEnergyStorage extends EnergyStorage {



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



}