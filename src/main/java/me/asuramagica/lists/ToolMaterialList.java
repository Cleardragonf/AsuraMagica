package me.asuramagica.lists;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

public enum ToolMaterialList implements IItemTier{

	crystalizedmanatier1(10.0f,9.0f,800,3,25,ItemList.mana);
	//crystalizedmanatier2(20.0f,18.0f,1600,6,50,ItemList.manatier2);
	
	private float attackDamage, efficency;
	private int durability, harvestLevel, enchantability;
	private Item repairMaterial;
	
	private ToolMaterialList(float attackDamage, float efficency, int durability, int harvestLevel, int enchantability, Item repairMaterial) {

		this.attackDamage = attackDamage;
		this.efficency = efficency;
		this.durability = durability;
		this.harvestLevel = harvestLevel;
		this.enchantability = enchantability;
		this.repairMaterial = repairMaterial;
	}

	@Override
	public int getMaxUses() {
		
		return this.durability;
	}

	@Override
	public float getEfficiency() {
		
		return this.efficency;
	}

	@Override
	public float getAttackDamage() {
		
		return this.attackDamage;
	}

	@Override
	public int getHarvestLevel() {
		
		return this.harvestLevel;
	}

	@Override
	public int getEnchantability() {
		
		return this.enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		
		return Ingredient.fromItems(this.repairMaterial);
	}
}
