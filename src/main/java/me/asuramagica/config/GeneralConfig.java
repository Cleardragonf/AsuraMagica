package me.asuramagica.config;

import java.io.File;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import me.asuramagica.AsuraMagicaMod;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

@Mod.EventBusSubscriber
public class GeneralConfig{
	
	private static final ForgeConfigSpec.Builder server_builder = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec server_config;
	
	private static final ForgeConfigSpec.Builder client_builder = new ForgeConfigSpec.Builder();
	public static final ForgeConfigSpec client_config;
	
	static {
		
		MCMValueConfig.init(server_builder, client_builder);
		
		server_config = server_builder.build();
		client_config = client_builder.build();
		
	}
	
	public static void loadConfig(ForgeConfigSpec config, String path) {
		AsuraMagicaMod.LOGGER.info("Loading Config: " + path);
		final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
		AsuraMagicaMod.LOGGER.info("Built Config: " + path);
		file.load();
		AsuraMagicaMod.LOGGER.info("Loaded config: " + path);
		config.setConfig(file);
	}
}
