package me.asuramagica.events;
import me.asuramagica.lists.BlockList;
import me.asuramagica.lists.ItemList;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class BlockBreakEvent {
	public void onbreak(Block block, World world, BlockPos blockPos, PlayerEntity entityPlayer) {
		if(entityPlayer.getHeldItemMainhand().getItem() == ItemList.mana) {
			if(block == BlockList.water_mana_ore) {
				block.dropXpOnBlockBreak(world, blockPos, 500);
			}
			if(block == BlockList.fire_mana_ore) {
				block.dropXpOnBlockBreak(world, blockPos, 500);
			}
		}
		
	}
}
