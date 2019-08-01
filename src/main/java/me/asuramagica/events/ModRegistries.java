package me.asuramagica.events;

import me.asuramagica.AsuraMagicaItemGroup;
import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.blocks.MCM_Block;
import me.asuramagica.blocks.Mana_Stone;
import me.asuramagica.items.ItemCustomAxe;
import me.asuramagica.items.ItemCustomPickaxe;
import me.asuramagica.items.Food.ItemCustomFood;
import me.asuramagica.lists.ArmorMaterialList;
import me.asuramagica.lists.BlockList;
import me.asuramagica.lists.ItemList;
import me.asuramagica.lists.ToolMaterialList;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRegistries {
	public static final ItemGroup ASURAMAGICA = new AsuraMagicaItemGroup();
	public static ResourceLocation location(String name){
		return new ResourceLocation(AsuraMagicaMod.MODID,name);
	}
	
	public static void init() {
		//Items
		ItemList.mana = registerItem(new Item(new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "mana");
		
		//Blocks
		BlockList.mana_stone = (Mana_Stone) registerBlock(new Mana_Stone((Block.Properties.create(Material.IRON).hardnessAndResistance(1.0f,1.0f))), "mana_stone");
		BlockList.earth_mana_ore = registerBlock(new Block((Block.Properties.create(Material.EARTH).hardnessAndResistance(1.0f,1.0f))), "earth_mana_ore");
		BlockList.fire_mana_ore = registerBlock(new Block((Block.Properties.create(Material.EARTH).hardnessAndResistance(1.0f,1.0f))), "fire_mana_ore");
		BlockList.mana_foci_crystal = registerBlock(new Block((Block.Properties.create(Material.EARTH).hardnessAndResistance(1.0f,1.0f))), "mana_foci_crystal");
		BlockList.mcmblock = (MCM_Block) registerBlock(new MCM_Block((Block.Properties.create(Material.GLASS).hardnessAndResistance(1.0f,1.0f))), "mcmblock");
		BlockList.water_mana_ore = registerBlock(new Block((Block.Properties.create(Material.EARTH).hardnessAndResistance(1.0f,1.0f))), "water_mana_ore");
		BlockList.wind_mana_ore = registerBlock(new Block((Block.Properties.create(Material.EARTH).hardnessAndResistance(1.0f,1.0f))), "wind_mana_ore");

		//Foods
		ItemList.cauliflour = registerItem(new ItemCustomFood(0, 0, 0, 0, false, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "cauliflour");
		ItemList.lettuce = registerItem(new ItemCustomFood(0, 0, 0, 0, false, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "lettuce");
		ItemList.tomato = registerItem(new ItemCustomFood(0, 0, 0, 0, false, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "tomato");
		ItemList.rice = registerItem(new ItemCustomFood(0, 0, 0, 0, false, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "rice");
		ItemList.rice_bowl = registerItem(new ItemCustomFood(0, 0, 0, 0, false, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "rice_bowl");
		ItemList.pizza = registerItem(new ItemCustomFood(0, 0, 0, 0, false, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "pizza");
		
		//Tools
		ItemList.mana_axe = registerItem(new ItemCustomAxe(ToolMaterialList.crystalizedmanatier1, 0, 0, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "mana_axe");
		ItemList.mana_pickaxe = registerItem(new ItemCustomPickaxe(ToolMaterialList.crystalizedmanatier1, 0, 0, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "mana_pickaxe");
		ItemList.ritual_dagger = registerItem(new SwordItem(ToolMaterialList.crystalizedmanatier1, 0, 0, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "ritual_dagger");
		
		//Armor
		ItemList.mana_boots = registerItem(new ArmorItem(ArmorMaterialList.crystalizedmanatier1, EquipmentSlotType.FEET, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "mana_boots");
		ItemList.mana_chestplate = registerItem(new ArmorItem(ArmorMaterialList.crystalizedmanatier1, EquipmentSlotType.CHEST, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "mana_chestplate");
		ItemList.mana_helmet = registerItem(new ArmorItem(ArmorMaterialList.crystalizedmanatier1, EquipmentSlotType.HEAD, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "mana_helmet");
		ItemList.mana_leggings = registerItem(new ArmorItem(ArmorMaterialList.crystalizedmanatier1, EquipmentSlotType.LEGS, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)), "mana_leggings");
		
		
	}
	
	public static Block registerBlock(Block block, String name)
    {
        BlockItem itemBlock = new BlockItem(block, new Item.Properties().group(ASURAMAGICA));
        block.setRegistryName(name);
        itemBlock.setRegistryName(name);
        ForgeRegistries.BLOCKS.register(block);
        ForgeRegistries.ITEMS.register(itemBlock);
        return block;
    }

    public static Block registerBlock(Block block, BlockItem itemBlock, String name)

    {

        block.setRegistryName(name);

        ForgeRegistries.BLOCKS.register(block);



        if (itemBlock != null)

        {

            itemBlock.setRegistryName(name);

            ForgeRegistries.ITEMS.register(itemBlock);

        }



        return block;

    }

    public static Item registerItem(Item item, String name)

    {

        item.setRegistryName(name);

        ForgeRegistries.ITEMS.register(item);

        return item;

    }
}
