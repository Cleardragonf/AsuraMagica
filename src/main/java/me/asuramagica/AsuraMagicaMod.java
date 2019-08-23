package me.asuramagica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import me.asuramagica.blocks.ModBlocks;
import me.asuramagica.blocks.inventory.ManaStoneContainer;
import me.asuramagica.blocks.Mana_Stone;
import me.asuramagica.blocks.tileentities.Mana_StoneTile;
import me.asuramagica.config.GeneralConfig;
import me.asuramagica.blocks.MCM_Block;
import me.asuramagica.blocks.inventory.MCM_Container;
import me.asuramagica.blocks.tileentities.MCM_Tile;
import me.asuramagica.events.BlockBreakEvent;
import me.asuramagica.events.CustomDrinkEvent;
import me.asuramagica.events.CustomFoodEvent;
import me.asuramagica.items.ItemCustomAxe;
import me.asuramagica.items.WardEnscriber;
import me.asuramagica.items.Food.ItemCustomFood;
import me.asuramagica.lists.ArmorMaterialList;
import me.asuramagica.lists.BlockList;
import me.asuramagica.lists.ItemList;
import me.asuramagica.lists.ToolMaterialList;
import me.asuramagica.setup.ClientProxy;
import me.asuramagica.setup.IProxy;
import me.asuramagica.setup.ServerProxy;
import me.asuramagica.tools.TemperatureContainer;
import me.asuramagica.tools.util.EnergyTypePacketHandler;
import me.asuramagica.tools.util.Hydration.PlayerHydrationCapability;
import me.asuramagica.tools.util.Hydration.PlayerHydrationProvider;
import me.asuramagica.tools.util.MCMValueCapability.MCMValueProvider;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureCapability;
import me.asuramagica.tools.util.Temperature.PlayerTemperatureProvider;
import me.asuramagica.world.OreGeneration;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.UseAction;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod("asuramagica")
public class AsuraMagicaMod {
	

	public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
		
	public static AsuraMagicaMod instance;
	public static final String MODID = "asuramagica";
	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static ResourceLocation location(String name){
		return new ResourceLocation(MODID,name);
	}
	
	public static final ItemGroup ASURAMAGICA = new AsuraMagicaItemGroup();

	
	public AsuraMagicaMod(){
		
		instance = this;
		
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, GeneralConfig.server_config);
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, GeneralConfig.client_config);
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistries);
		
		GeneralConfig.loadConfig(GeneralConfig.client_config, FMLPaths.CONFIGDIR.get().resolve("asuramagica-Client.toml").toString());
		GeneralConfig.loadConfig(GeneralConfig.server_config, FMLPaths.CONFIGDIR.get().resolve("asuramagica-server.toml").toString());
		
		MinecraftForge.EVENT_BUS.register(this);
		
	}
	private void setup(final FMLCommonSetupEvent event){
		proxy.init();
		LOGGER.info("Setup is working");
		OreGeneration.setupOreGeneration();		
		PlayerTemperatureProvider.registerCapability();
		PlayerHydrationProvider.registerCapability();
		MCMValueProvider.registerCapability();
		EnergyTypePacketHandler.register();
	}
	private void clientRegistries(final FMLClientSetupEvent event){
		LOGGER.info("Client is running");
	}
	
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents{
		
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event){
			
			event.getRegistry().registerAll(
					ItemList.mana = new Item(new Item.Properties().group(ASURAMAGICA)).setRegistryName(location("mana")),
							ItemList.ward_enscriber = new WardEnscriber(new Item.Properties().group(ASURAMAGICA)).setRegistryName(location("ward_enscriber")),
					
					ItemList.mana_axe = new ItemCustomAxe(ToolMaterialList.crystalizedmanatier1, 0, 0, new Item.Properties().group(ASURAMAGICA)).setRegistryName(location("mana_axe")),
					ItemList.mana_pickaxe = new ItemCustomAxe(ToolMaterialList.crystalizedmanatier1, 0, 0, new Item.Properties().group(ASURAMAGICA)).setRegistryName(location("mana_pickaxe")),
					
					ItemList.mana_boots = new ArmorItem(ArmorMaterialList.crystalizedmanatier1, EquipmentSlotType.FEET, new Item.Properties().group(ASURAMAGICA)).setRegistryName(location("mana_boots")),
					ItemList.mana_chestplate = new ArmorItem(ArmorMaterialList.crystalizedmanatier1, EquipmentSlotType.CHEST, new Item.Properties().group(ASURAMAGICA)).setRegistryName(location("mana_chestplate")),
					ItemList.mana_helmet = new ArmorItem(ArmorMaterialList.crystalizedmanatier1, EquipmentSlotType.HEAD, new Item.Properties().group(ASURAMAGICA)).setRegistryName(location("mana_helmet")),
					ItemList.mana_leggings = new ArmorItem(ArmorMaterialList.crystalizedmanatier1, EquipmentSlotType.LEGS, new Item.Properties().group(ASURAMAGICA)).setRegistryName(location("mana_leggings")),
					
					ItemList.ritual_dagger = new SwordItem(ToolMaterialList.crystalizedmanatier1, 50, 30.0f, new Item.Properties().group(ASURAMAGICA)).setRegistryName(location("ritual_dagger")),

					
					ItemList.mana_stone = new BlockItem(BlockList.mana_stone, new Item.Properties().group(ASURAMAGICA)).setRegistryName(BlockList.mana_stone.getRegistryName()),
					ItemList.water_mana_ore = new BlockItem(BlockList.water_mana_ore, new Item.Properties().group(ASURAMAGICA)).setRegistryName(BlockList.water_mana_ore.getRegistryName()),
					ItemList.fire_mana_ore = new BlockItem(BlockList.fire_mana_ore, new Item.Properties().group(ASURAMAGICA)).setRegistryName(BlockList.fire_mana_ore.getRegistryName()),
					ItemList.earth_mana_ore = new BlockItem(BlockList.earth_mana_ore, new Item.Properties().group(ASURAMAGICA)).setRegistryName(BlockList.earth_mana_ore.getRegistryName()),
					ItemList.wind_mana_ore = new BlockItem(BlockList.wind_mana_ore, new Item.Properties().group(ASURAMAGICA)).setRegistryName(BlockList.wind_mana_ore.getRegistryName()),
					ItemList.mana_foci_crystal = new BlockItem(BlockList.mana_foci_crystal, new Item.Properties().group(ASURAMAGICA)).setRegistryName(BlockList.mana_foci_crystal.getRegistryName()),
					ItemList.mana_foci_crystal = new BlockItem(BlockList.mcmblock, new Item.Properties().group(ASURAMAGICA)).setRegistryName(BlockList.mcmblock.getRegistryName()),
					
					ItemList.tomato = new ItemCustomFood(3, 0, 0, 0, false, new Item.Properties().maxStackSize(32).group(ASURAMAGICA)).setRegistryName(location("tomato")),
					ItemList.lettuce = new ItemCustomFood(5, 0, 0, 0, false, new Item.Properties().maxStackSize(5).group(ASURAMAGICA)).setRegistryName(location("lettuce")),
					ItemList.rice = new ItemCustomFood(1, 0, 0, 0, false, new Item.Properties().maxStackSize(64).group(ASURAMAGICA)).setRegistryName(location("rice")),
					ItemList.cauliflour = new ItemCustomFood(1, 0, 0, 0, false, new Item.Properties().maxStackSize(64).group(ASURAMAGICA)).setRegistryName(location("cauliflour")),
					ItemList.rice_bowl = new ItemCustomFood(10, 0, 0, 0,  false, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)).setRegistryName(location("rice_bowl")),
					ItemList.pizza = new ItemCustomFood(20, 5, 0, 0, false, new Item.Properties().maxStackSize(1).group(ASURAMAGICA)).setRegistryName(location("pizza"))
					
					);
			
			
			LOGGER.info("Items registe4red.");
		}
		
		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event){
			
			event.getRegistry().registerAll(
					//BlockList.mana_stone = new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(2.0f, 3.0f).lightValue(10).sound(SoundType.METAL)).setRegistryName(location("mana_stone")),
					BlockList.mana_stone = (Mana_Stone) new Mana_Stone(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0f,1.0f)).setRegistryName(location("mana_stone")),
					BlockList.water_mana_ore = new Block(Block.Properties.create(Material.EARTH).hardnessAndResistance(1.0f,6.0f)).setRegistryName(location("water_mana_ore")),
					BlockList.fire_mana_ore = new Block(Block.Properties.create(Material.EARTH).hardnessAndResistance(1.0f,6.0f)).setRegistryName(location("fire_mana_ore")),
					BlockList.earth_mana_ore = new Block(Block.Properties.create(Material.EARTH).hardnessAndResistance(1.0f,6.0f)).setRegistryName(location("earth_mana_ore")),
					BlockList.wind_mana_ore = new Block(Block.Properties.create(Material.EARTH).hardnessAndResistance(1.0f,6.0f)).setRegistryName(location("wind_mana_ore")),
					BlockList.mana_foci_crystal = new Block(Block.Properties.create(Material.GLASS).hardnessAndResistance(1.0f, 10.0f)).setRegistryName(location("mana_foci_crystal")),
					BlockList.mcmblock = (MCM_Block) new MCM_Block(Block.Properties.create(Material.GLASS).hardnessAndResistance(1.0f, 10.0f)).setRegistryName(location("mcmblock"))
					
					);
			
			
			LOGGER.info("B registe4red.");
		}
		
		@SubscribeEvent
		public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
			event.getRegistry().register(TileEntityType.Builder.create(Mana_StoneTile::new, ModBlocks.ManaStone).build(null).setRegistryName(location("mana_stone")));
			event.getRegistry().register(TileEntityType.Builder.create(MCM_Tile::new, ModBlocks.MCMBlock).build(null).setRegistryName(location("mcmblock")));
		}
		
		@SubscribeEvent
		public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) ->{
				BlockPos pos = data.readBlockPos();
				return new ManaStoneContainer(windowId, AsuraMagicaMod.proxy.getClientWorld(), pos, inv, AsuraMagicaMod.proxy.getClientPlayer());
			}).setRegistryName(location("mana_stone")));
			
			event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) ->{
				BlockPos pos = data.readBlockPos();
				return new MCM_Container(windowId, AsuraMagicaMod.proxy.getClientWorld(), pos, inv, AsuraMagicaMod.proxy.getClientPlayer());
			}).setRegistryName(location("mcmblock")));
			
			
		}
		
	}


	
	@SubscribeEvent
 	public void onbreak(BlockEvent.BreakEvent event) {
		BlockBreakEvent trial = new BlockBreakEvent();
		trial.onbreak(event.getState().getBlock(), event.getWorld().getWorld(), event.getPos(), event.getPlayer());
	}
	
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
	public static class RegisterForgeEvents{
		public static int i = 0;
		public static int b = 0;
		@SuppressWarnings("static-access")
		@SubscribeEvent
	    public static void checkPlayersTemp(TickEvent.PlayerTickEvent event) {
			if(event.phase == TickEvent.Phase.START) {
				TemperatureContainer container = new TemperatureContainer();
				PlayerEntity player = event.player;
				World world = event.player.world;
				if((player.isSprinting() == true )|| (player.isSwimming() == true)) {
					if(b == 60) {
						container.onActivity(player, world);	
						b = 0;
					}else {
						b++;
					}
								
				}
		        if(i == 6000) {
		        	container.setTemp(player, world);
					i = 0;
		         }else {
		        	 i++;
		         }
		         
			}
	    }
		@SubscribeEvent
		public static void drinkOrEath(LivingEntityUseItemEvent.Finish event) {

			if(event.getEntityLiving() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) event.getEntityLiving();
				UseAction test = event.getItem().getUseAction().DRINK;
				UseAction test2 = event.getItem().getUseAction().EAT;
				
				if(event.getItem().getUseAction().equals(test)) {
					ItemStack send = event.getItem();
					CustomDrinkEvent drink = new CustomDrinkEvent();
					drink.drink(send, player);
				}
				
				if(event.getItem().getUseAction().equals(test2)) {
					ItemStack send = event.getItem();
					CustomFoodEvent food = new CustomFoodEvent();
					food.eat(send, player);
					
				}
			}

		}
		@SubscribeEvent
		public static void onDeath(LivingDeathEvent event) {
			if(event.getEntity() instanceof PlayerEntity) {
				PlayerEntity player = (PlayerEntity) event.getEntity();
				player.getCapability(PlayerTemperatureProvider.PlayerTemperature).ifPresent(a ->{
					player.getCapability(PlayerHydrationProvider.PlayerThirst).ifPresent(b -> {
						((PlayerTemperatureCapability)a).setPlayerTemp2(0);;
						((PlayerHydrationCapability)b).setPlayersThirst(b.maxThirst());
					});
				});
			}
		}
	}
}
