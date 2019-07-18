package me.asuramagica.blocks.Mana_Stone;


import com.mojang.blaze3d.platform.GlStateManager;
import me.asuramagica.AsuraMagicaMod;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;


public class Mana_StoneScreen extends ContainerScreen<ManaStoneContainer>{
	
	private ResourceLocation GUI = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/manastonegui.png");
	private ResourceLocation bar1 = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/bar1.png");
	private ResourceLocation bar2 = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/bar2.png");
	private ResourceLocation bar3 = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/bar3.png");
	private ResourceLocation bar4 = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/bar4.png");
	public Mana_StoneScreen(ManaStoneContainer container, PlayerInventory inv, ITextComponent name) {
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
		this.font.drawString("Mana Essence Collector", 8.0f, 6.0F, 4210752);
		this.font.drawString("Energy: " + container.getFire().getEnergyStored(), 8, 115, 0xfffff);
		this.font.drawString("Water: " + container.getWater().getEnergyStored(), 8, 100, 0xfffff);
		this.font.drawString("Sources: " + Mana_StoneTile.getSources(), 8.0f, 20.0f, 0xffBff);
		this.font.drawString("Amplifiers: " + Mana_StoneTile.getAmplifiers(), 8, 30, 0xfffff);
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
		
		//progressbar
		//first part binds the texture that i'm going to 'display'
		//second part takes the relative width and height and offsets the 'bar' by that much both vertically then horizonatally.  
		//then it takes where in the imate the bar should start...and how it should...increase. then it says where they end.
		this.minecraft.getTextureManager().bindTexture(bar1);	
		this.blit(relX + 112, relY + 30, 0,0+(int)getEnergyPercentage1(),9,100);
		
		this.minecraft.getTextureManager().bindTexture(bar2);	
		this.blit(relX + 125, relY + 30, 0,0+(int)getEnergyPercentage2(),9,100);
		
		this.minecraft.getTextureManager().bindTexture(bar3);	
		this.blit(relX + 138, relY + 30, 0,0+(int)getEnergyPercentage3(),9,100);
		
		this.minecraft.getTextureManager().bindTexture(bar4);	
		this.blit(relX + 151, relY + 30, 0,0+(int)getEnergyPercentage4(),9,100);
		
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
