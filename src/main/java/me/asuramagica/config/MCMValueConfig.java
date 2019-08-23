package me.asuramagica.config;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

public class MCMValueConfig {
	public static ForgeConfigSpec.IntValue mcmValue;
	public static Map<String, ForgeConfigSpec.IntValue> sections = new HashMap<>();
	
	
	public static void init(ForgeConfigSpec.Builder server, ForgeConfigSpec.Builder client) {
		Map<String, Integer> sections = new HashMap<>();
		server.comment("MCM Value Config");
		
        server.comment("MCM Value Config");
        
        for(Item item : ForgeRegistries.ITEMS) {
            mcmValue = server
                    .comment("Value of Each")
                    .defineInRange(item + " MCM Value: ", 500, 0, 1000000000);
        }	
	}
	
	public static void onLoad() {
		
	}

}
