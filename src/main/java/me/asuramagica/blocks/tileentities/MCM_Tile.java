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
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.ItemStackHandler;
import static me.asuramagica.blocks.ModBlocks.MCMTILE;
import java.util.LinkedList;
import java.util.List;

import me.asuramagica.blocks.Mana_Stone;
import me.asuramagica.blocks.inventory.MCM_Container;
import me.asuramagica.lists.BlockList;
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
						Mana_StoneTile test = (Mana_StoneTile) world.getTileEntity(pooledMutableBlockPos);
						if (block.getClass().equals(Mana_Stone.class)) {
							test.waterSource.ifPresent(h->{
								
							});
							System.out.println(test.waterEnergy.getEnergyStored());
						} 
					}
				}

			}
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