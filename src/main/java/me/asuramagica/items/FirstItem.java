package me.asuramagica.items;

import me.asuramagica.AsuraMagicaMod;
import net.minecraft.item.Item;

public class FirstItem extends Item{
	public FirstItem() {
		super(new Item.Properties()
				.maxStackSize(2)
				.group(AsuraMagicaMod.ASURAMAGICA));
	}
}
