package me.asuramagica.blocks;

import me.asuramagica.blocks.inventory.ManaStoneContainer;
import me.asuramagica.blocks.Mana_Stone;
import me.asuramagica.blocks.tileentities.Mana_StoneTile;
import me.asuramagica.blocks.MCM_Block;
import me.asuramagica.blocks.inventory.MCM_Container;
import me.asuramagica.blocks.tileentities.MCM_Tile;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {

	@ObjectHolder("asuramagica:mana_stone")
	public static Mana_Stone ManaStone;
	
	@ObjectHolder("asuramagica:mana_stone")
	public static TileEntityType<Mana_StoneTile> MANASTONETILE;

	@ObjectHolder("asuramagica:mana_stone")
	public static ContainerType<ManaStoneContainer> MANASTONECONTAINER;
	
	@ObjectHolder("asuramagica:mcmblock")
	public static MCM_Block MCMBlock;
	
	@ObjectHolder("asuramagica:mcmblock")
	public static ContainerType<MCM_Container> MCMCONTAINER;
	
	@ObjectHolder("asuramagica:mcmblock")
	public static TileEntityType<MCM_Tile> MCMTILE;
}
