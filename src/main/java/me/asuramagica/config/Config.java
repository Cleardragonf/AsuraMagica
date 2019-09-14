package me.asuramagica.config;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class Config {

	
	private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
	private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
	
	public static ForgeConfigSpec COMMON_CONFIG;
	public static ForgeConfigSpec CLIENT_CONFIG;
	public static ForgeConfigSpec SERVER_CONFIG;
	
	public static Map<ResourceLocation, ForgeConfigSpec.IntValue> MCM_VALUE = new HashMap<ResourceLocation, ForgeConfigSpec.IntValue>();
	
	static {
			for(Item item : ForgeRegistries.ITEMS) {
				SERVER_BUILDER.push(item.getRegistryName().toString());
					
					ForgeConfigSpec.IntValue mcm = SERVER_BUILDER
							.comment("Value")
							.defineInRange("onlyMCM", 100000, 0, Integer.MAX_VALUE);
					MCM_VALUE.put(item.getRegistryName(), mcm);
					
					SERVER_BUILDER.pop();
			}
		
		SERVER_CONFIG = SERVER_BUILDER.build();
		COMMON_CONFIG = COMMON_BUILDER.build();
		CLIENT_CONFIG = CLIENT_BUILDER.build();
	}
	
	  public static void loadConfig(ForgeConfigSpec spec, Path path) {
		  final CommentedFileConfig configData = CommentedFileConfig.builder(path)
				  .sync()
				  .autosave()
				  .writingMode(WritingMode.REPLACE)
				  .build();
		  configData.load();
		  spec.setConfig(configData);
	  }
	
}
