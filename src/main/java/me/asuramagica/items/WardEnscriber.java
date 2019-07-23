package me.asuramagica.items;

import java.util.LinkedList;
import java.util.List;

import me.asuramagica.blocks.tileentities.MCM_Tile;
import me.asuramagica.lists.BlockList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class WardEnscriber extends Item{

	public List<BlockPos> targetLocation = new LinkedList<>();
	public BlockPos sourceLocation;
	public WardEnscriber(Properties properties) {
		super(properties);
		// TODO Auto-generated constructor stub
	}
	
	   public ActionResultType onItemUse(ItemUseContext context) {
		      World world = context.getWorld();
		      BlockPos blockpos = context.getPos();
		      BlockState blockstate = world.getBlockState(blockpos);
		      Block block = world.getBlockState(blockpos).getBlock();
		      if (block != null) {
		         if(block.equals(BlockList.mana_stone)) {
		        	 sourceLocation = blockpos;
		        	 System.out.print("Working");
		         }
		         if(block.equals(BlockList.mcmblock)) {
		        	 MCM_Tile test = new MCM_Tile();
		        	 //test.linkedPowerSource = blockpos;
		        	 System.out.println(blockpos.toString());
		        	 
		         }
		         return ActionResultType.SUCCESS;
		         
		      } else {
		         return ActionResultType.PASS;
		      }
		   }
	   
	   public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, TileEntity entity) {
		System.out.println("test");
		return false;
	}

}
