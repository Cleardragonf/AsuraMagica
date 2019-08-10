package me.asuramagica.blocks.client.gui;


import com.mojang.blaze3d.platform.GlStateManager;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.blocks.inventory.MCM_Container;
import me.asuramagica.blocks.tileentities.MCM_Tile;
import me.asuramagica.tools.util.EnergyTypePacket;
import me.asuramagica.tools.util.EnergyTypePacketHandler;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;


public class MCM_Screen extends ContainerScreen<MCM_Container>{
	private int slotA = 0;
	BlockPos postest;
	private ResourceLocation GUI = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/mcmgui.png");
	private ResourceLocation MCMEarthType = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/mcmearthtype.png");
	private ResourceLocation MCMFireType = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/mcmfiretype.png");
	private ResourceLocation MCMWaterType = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/mcmwatertype.png");
	private ResourceLocation MCMWindType = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/mcmwindtype.png");

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
		//this.font.drawString("Int: " + container.getLinked().toString(), 15.0f, 15.0F, 4210752);
		if(this.slotA == 0) {
			this.font.drawString("::Earth::", 6,36,4210752);
		}else if(this.slotA == 1) {
			this.font.drawString("::Fire::", 6,36,4210752);
		}else if(this.slotA == 2) {
			this.font.drawString("::Water::", 6,36,4210752);
		}else if(this.slotA == 3) {
			this.font.drawString("::Wind::", 6,36,4210752);
		}
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
	
	@Override
	public boolean mouseClicked(double x, double y, int mouseButton) {
		int relX = (this.width - this.xSize) /2;
		int relY = (this.height - this.ySize) /2;
		if(trueZone(relX + 3, relY + 43, 26,42,x,y)) {
			
			cycleThroug(mouseButton);
			
			BlockPos pos = this.container.pos;
			EnergyTypePacketHandler.CHANNEL.sendToServer(new EnergyTypePacket(pos, DimensionType.OVERWORLD, slotA));
			MCM_Tile tileEntity = (MCM_Tile) container.tileEntity;
			tileEntity.slotAType = slotA;
			
		}
		//TODO Once SlotA is done...replicate the above for SlotsB,C and D
		return super.mouseClicked(x, y, mouseButton);
	}



	private void cycleThroug(int mouseButton) {
		if(mouseButton == 0) {
			if(slotA < 3) {
				slotA += 1;
			}else {
				slotA = 0;
			}
		}else if(mouseButton ==1) {
			if(slotA > 0) {
				slotA -= 1;
			}else {
				slotA = 3;
			}
		}
		
	}

	private boolean trueZone(int OffsX, int OffsY, int Width, int Height, double MouseX, double MouseY) {
		if(OffsX <= MouseX && MouseX <= OffsX + Width && OffsY <= MouseY && MouseY <= OffsY + Height ){
			return true;
		}
		else{
			return false;	
		}
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
