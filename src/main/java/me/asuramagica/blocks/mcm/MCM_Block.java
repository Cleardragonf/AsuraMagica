package me.asuramagica.blocks.mcm;

//import me.asuramagica.events.BlockBreakEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
//import net.minecraft.block.DropperBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
//pimport net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.network.NetworkHooks;

public class MCM_Block extends Block {
    public MCM_Block(Block.Properties properties) {
        super(properties);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new MCM_Tile();
    }

    // onBlockPlacedBy method is fine

    // getFacingFromEntity method is fine

    @SuppressWarnings("deprecation")
	@Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (world.isRemote) {
            return true;
        } else {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof INamedContainerProvider) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (MCM_Tile) tileEntity, tileEntity.getPos());
                return true;
            }
            return super.onBlockActivated(state, world, pos, player, hand, result);
        }
    }

}