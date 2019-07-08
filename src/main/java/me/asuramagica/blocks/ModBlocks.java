package me.asuramagica.blocks;

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
}
