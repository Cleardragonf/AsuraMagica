package me.asuramagica.events;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.blocks.ModBlocks;
import me.asuramagica.blocks.inventory.MCM_Container;
import me.asuramagica.blocks.inventory.ManaStoneContainer;
import me.asuramagica.blocks.tileentities.MCM_Tile;
import me.asuramagica.blocks.tileentities.Mana_StoneTile;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;


public class ContainerRegistry {
	public static ResourceLocation location(String name){
		return new ResourceLocation(AsuraMagicaMod.MODID,name);
	}
			
		public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
			event.getRegistry().register(TileEntityType.Builder.create(Mana_StoneTile::new, ModBlocks.ManaStone).build(null).setRegistryName(location("mana_stone")));
			event.getRegistry().register(TileEntityType.Builder.create(MCM_Tile::new, ModBlocks.MCMBlock).build(null).setRegistryName(location("mcmblock")));
		}
		
		public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) ->{
				BlockPos pos = data.readBlockPos();
				return new ManaStoneContainer(windowId, AsuraMagicaMod.proxy.getClientWorld(), pos, inv, AsuraMagicaMod.proxy.getClientPlayer());
			}).setRegistryName(location("mana_stone")));
			
			event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) ->{
				BlockPos pos = data.readBlockPos();
				ResourceLocation dimension = data.readResourceLocation();
				int slotA = data.readInt();
				return new MCM_Container(windowId, AsuraMagicaMod.proxy.getClientWorld(), pos, inv, dimension, slotA, AsuraMagicaMod.proxy.getClientPlayer());
			}).setRegistryName(location("mcmblock")));
			
			
		}
		
}
