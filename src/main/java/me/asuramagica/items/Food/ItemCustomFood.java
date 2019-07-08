package me.asuramagica.items.Food;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ItemCustomFood extends Item {
   /** The amount this food item heals the player. */
   private final int healAmount;
   private final float saturationModifier;
   /** Whether wolves like this food (true for raw and cooked porkchop). */
   private final boolean meat;
   /** If this field is true, the food can be consumed even if the player don't need to eat. */
   private boolean alwaysEdible;
   private boolean fastEating;
   /** represents the potion effect that will occurr upon eating this food. Set by setPotionEffect */
   private Effect potionId;
   /** probably of the set potion effect occurring */
   private float potionEffectProbability;

   public ItemCustomFood(int healAmountIn, float saturation, int heatFactor, int coolnessFactor,  boolean meat, Item.Properties builder) {
      super(builder);
      this.healAmount = healAmountIn;
      this.meat = meat;
      this.saturationModifier = saturation;
   }

   /**
    * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
    * the Item before the action is complete.
    * This is also where I'll want to add a Heat Increase or Decrease etc...
    */
   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
      if (entityLiving instanceof PlayerEntity) {
    	  PlayerEntity entityplayer = (PlayerEntity)entityLiving;
         entityplayer.getFoodStats().addStats(this.getHealAmount(stack), this.getSaturationModifier(stack));
         
         worldIn.playSound((PlayerEntity)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
         this.onFoodEaten(stack, worldIn, entityplayer);
         entityplayer.addStat(Stats.ITEM_USED.get(this));
         if (entityplayer instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)entityplayer, stack);
         }
      }

      stack.shrink(1);
      return stack;
   }

   protected void onFoodEaten(ItemStack stack, World worldIn, PlayerEntity player) {
      if (!worldIn.isRemote && this.potionId != null && worldIn.rand.nextFloat() < this.potionEffectProbability) {
         player.addPotionEffect(new EffectInstance(this.potionId));
      }

   }

   /**
    * How long it takes to use or consume an item
    */
   public int getUseDuration(ItemStack stack) {
      return this.fastEating ? 16 : 32;
   }

   /**
    * returns the action that specifies what animation to play when the items is being used
    */
   public UseAction getUseAction(ItemStack stack) {
      return UseAction.EAT;
   }

   /**
    * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
    * {@link #onItemUse}.
    */
   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
      ItemStack itemstack = playerIn.getHeldItem(handIn);
      if (playerIn.canEat(this.alwaysEdible)) {
         playerIn.setActiveHand(handIn);
         return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
      } else {
         return new ActionResult<>(ActionResultType.FAIL, itemstack);
      }
   }

   public int getHealAmount(ItemStack stack) {
      return this.healAmount;
   }

   public float getSaturationModifier(ItemStack stack) {
      return this.saturationModifier;
   }

   /**
    * Whether wolves like this food (true for raw and cooked porkchop).
    */
   public boolean isMeat() {
      return this.meat;
   }

   public ItemCustomFood setPotionEffect(Effect effect, float probability) {
      this.potionId = effect;
      this.potionEffectProbability = probability;
      return this;
   }

   /**
    * Set the field 'alwaysEdible' to true, and make the food edible even if the player don't need to eat.
    */
   public ItemCustomFood setAlwaysEdible() {
      this.alwaysEdible = true;
      return this;
   }

   public ItemCustomFood setFastEating() {
      this.fastEating = true;
      return this;
   }
}