package me.asuramagica;

import me.asuramagica.lists.ItemList;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class AsuraMagicaItemGroup extends ItemGroup{

	public AsuraMagicaItemGroup() {
		super("asuramagica");

	}

	@Override
	public ItemStack createIcon() {

		return new ItemStack(ItemList.mana);
	}

}
