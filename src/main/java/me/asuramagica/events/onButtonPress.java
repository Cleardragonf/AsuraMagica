package me.asuramagica.events;

import com.mojang.blaze3d.platform.GlStateManager;

import me.asuramagica.blocks.client.gui.MCM_Screen;
import me.asuramagica.blocks.inventory.MCM_Container;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.config.GuiButtonExt;

public class onButtonPress extends MCM_Screen implements Button.IPressable
{
    public onButtonPress(MCM_Container container, PlayerInventory inv, ITextComponent name) {
		super(container, inv, name);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void onPress(final Button button) {
		MCM_Screen on = this;
		on.renderBackground();
    	System.out.print("testing");
    	
		
    }
}
