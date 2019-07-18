package me.asuramagica.blocks.mcm;


import com.mojang.blaze3d.platform.GlStateManager;
import me.asuramagica.AsuraMagicaMod;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;


public class MCM_Screen extends ContainerScreen<MCM_Container>{
	
	private ResourceLocation GUI = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/manastonegui.png");

	public MCM_Screen(MCM_Container container, PlayerInventory inv, ITextComponent name) {
		super(container, inv, name);
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks_) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks_);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.font.drawString("Matter Conversion Block", 8.0f, 6.0F, 4210752);
		this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0f, 129.0f,4210752);
		int relX = (this.width - this.xSize) /2;
		int relY = (this.height - this.ySize) /2;
		this.blit(relX, relY, 0,0,256,256);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int relX = (this.width - this.xSize) /2;	
		int relY = (this.height - this.ySize) /2;
		//size and positioning of the image
		this.blit(relX, relY, 0,0,256,256);
		
	}
	


	protected double getEnergyPercentage1() {

		return ((double) this.container.getFire().getEnergyStored() / (double) this.container.getFire().getMaxEnergyStored() * 100D);

	}
	
	protected double getEnergyPercentage2() {

		return ((double) this.container.getWater().getEnergyStored()/ (double) this.container.getWater().getMaxEnergyStored() * 100D);

	}
	
	protected double getEnergyPercentage3() {

		return ((double) this.container.getEarth().getEnergyStored()/ (double) this.container.getEarth().getMaxEnergyStored() * 100D);

	}
	
	protected double getEnergyPercentage4() {

		return ((double) this.container.getWind().getEnergyStored()/ (double) this.container.getWind().getMaxEnergyStored() * 100D);

	}
	

}
