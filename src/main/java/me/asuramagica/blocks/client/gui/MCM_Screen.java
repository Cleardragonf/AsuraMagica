package me.asuramagica.blocks.client.gui;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mojang.blaze3d.platform.GlStateManager;

import me.asuramagica.AsuraMagicaMod;
import me.asuramagica.blocks.inventory.MCM_Container;
import me.asuramagica.blocks.tileentities.MCM_Tile;
import me.asuramagica.tools.util.EnergyTypePacket;
import me.asuramagica.tools.util.EnergyTypePacketHandler;
import me.asuramagica.tools.util.MCMValueCapability.MCMValueProvider;
import me.asuramagica.tools.util.Packets.MCM.EnergyTypePacketB;
import me.asuramagica.tools.util.Packets.MCM.EnergyTypePacketC;
import me.asuramagica.tools.util.Packets.MCM.EnergyTypePacketD;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;


public class MCM_Screen extends ContainerScreen<MCM_Container>{
	private int slotA;
	private int slotB;
	private int slotC;
	private int slotD;
	public int mcmValueA;
	
	BlockPos postest;
	private ResourceLocation GUI = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/mcmgui.png");
	private ResourceLocation MCMEnergyType = new ResourceLocation(AsuraMagicaMod.MODID, "textures/gui/mcmenergytype.png");
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
		MCM_Tile tileEntity = (MCM_Tile) container.tileEntity;
		this.slotA = tileEntity.slotAType;
		this.slotB = tileEntity.slotBType;
		this.slotC = tileEntity.slotCType;
		this.slotD = tileEntity.slotDType;
		this.font.drawString("Matter Conversion Block", 8.0f, 6.0F, 4210752);
		//this.font.drawString("Int: " + container.getLinked().toString(), 15.0f, 15.0F, 4210752);

		int relX = (this.width - this.xSize) /2;
		int relY = (this.height - this.ySize) /2;
		this.blit(relX, relY, 0,0,256,256);

		changeColor(slotA, 3);
		changeColor(slotB, 51);
		changeColor(slotC, 99);
		changeColor(slotD, 147);

	}
	
	public void changeColor(int slot, int a) {
		switch (slot) {
		case 0:
			this.minecraft.textureManager.bindTexture(MCMEnergyType);
			this.blit(a,42,0,0,26,42);
			break;
		case 1:
			this.minecraft.textureManager.bindTexture(MCMEnergyType);
			this.blit(a,42,31,0,26,42);
			break;
		case 2:
			this.minecraft.textureManager.bindTexture(MCMEnergyType);
			this.blit(a,42,62,0,26,42);
			break;
		case 3:
			this.minecraft.textureManager.bindTexture(MCMEnergyType);
			this.blit(a,42,91,0,26,42);
			break;
		default:
			this.minecraft.textureManager.bindTexture(MCMEnergyType);
			this.blit(a,42,0,0,26,42);
			break;
		}
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
		if(trueZone(relX + 3, relY + 42, 26,42,x,y)) {
			cycleThroug(mouseButton, 0);
			BlockPos pos = this.container.pos;
			EnergyTypePacketHandler.CHANNEL.sendToServer(new EnergyTypePacket(pos, DimensionType.OVERWORLD, slotA));
			MCM_Tile tileEntity = (MCM_Tile) container.tileEntity;
			tileEntity.slotAType = slotA;
		}
		else if(trueZone(relX + 51, relY + 42, 26,42,x,y)) {
			cycleThroug(mouseButton, 1);
			BlockPos pos = this.container.pos;
			EnergyTypePacketHandler.CHANNEL.sendToServer(new EnergyTypePacketB(pos, DimensionType.OVERWORLD, slotB));
			MCM_Tile tileEntity = (MCM_Tile) container.tileEntity;
			tileEntity.slotBType = slotB;
		}
		else if(trueZone(relX + 99, relY + 42, 26,42,x,y)) {
			cycleThroug(mouseButton, 2);
			BlockPos pos = this.container.pos;
			EnergyTypePacketHandler.CHANNEL.sendToServer(new EnergyTypePacketC(pos, DimensionType.OVERWORLD, slotC));
			MCM_Tile tileEntity = (MCM_Tile) container.tileEntity;
			tileEntity.slotCType = slotC;
		}
		else if(trueZone(relX + 147, relY + 42, 26,42,x,y)) {
			cycleThroug(mouseButton, 3);
			BlockPos pos = this.container.pos;
			EnergyTypePacketHandler.CHANNEL.sendToServer(new EnergyTypePacketD(pos, DimensionType.OVERWORLD, slotD));
			MCM_Tile tileEntity = (MCM_Tile) container.tileEntity;
			tileEntity.slotDType = slotD;
		}
		//TODO Once SlotA is done...replicate the above for SlotsB,C and D
		return super.mouseClicked(x, y, mouseButton);
	}

	@Override
	protected void renderHoveredToolTip(int x, int y) {
		int relX = (this.width - this.xSize) /2;
		int relY = (this.height - this.ySize) /2;
		if(trueZone(relX + 3, relY + 42, 26,42,x,y)) {
			tooltipText(slotA, x, y);
		}	
		if(trueZone(relX + 51, relY + 42, 26,42,x,y)) {
			tooltipText(slotB, x, y);
		}	
		if(trueZone(relX + 99, relY + 42, 26,42,x,y)) {
			tooltipText(slotC, x, y);
		}	
		if(trueZone(relX + 147, relY + 42, 26,42,x,y)) {
			tooltipText(slotD, x, y);
		}	
		super.renderHoveredToolTip(x, y);
	}
	
	public void tooltipText(int slot, int x, int y) {
		switch (slot) {
		case 0:
			this.renderTooltip("Earth", x, y);
			break;
		case 1:
			this.renderTooltip("Fire", x, y);
			break;
		case 2:
			this.renderTooltip("Water", x, y);
			break;
		case 3:
			this.renderTooltip("Wind", x, y);
			break;
		default:
			this.renderTooltip("Earth", x, y);
			break;
		}
	}

	private void cycleThroug(int mouseButton, int slot) {
		if(slot == 0) {
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
		}else if(slot == 1) {
			if(mouseButton == 0) {
				if(slotB < 3) {
					slotB += 1;
				}else {
					slotB = 0;
				}
			}else if(mouseButton ==1) {
				if(slotB > 0) {
					slotB -= 1;
				}else {
					slotB = 3;
				}
			}
		}else if(slot == 2) {
			if(mouseButton == 0) {
				if(slotC < 3) {
					slotC += 1;
				}else {
					slotC = 0;
				}
			}else if(mouseButton ==1) {
				if(slotC > 0) {
					slotC -= 1;
				}else {
					slotC = 3;
				}
			}
		}else if(slot == 3) {
			if(mouseButton == 0) {
				if(slotD < 3) {
					slotD += 1;
				}else {
					slotD = 0;
				}
			}else if(mouseButton ==1) {
				if(slotD > 0) {
					slotD -= 1;
				}else {
					slotD = 3;
				}
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
		
		
		this.container.getSlot(0).getStack().getCapability(MCMValueProvider.MCMValue).ifPresent(h ->{
			mcmValueA = h.mcmValue();
		});
		return (double) mcmValueA / (double) this.container.getEarth().getMaxEnergyStored() * 100D;

	}
	
	protected double getEnergyPercentage2() {

		return ((double) this.container.getFire().getEnergyStored()/ (double) this.container.getFire().getMaxEnergyStored() * 100D);

	}
	
	protected double getEnergyPercentage3() {

		return ((double) this.container.getWater().getEnergyStored()/ (double) this.container.getWater().getMaxEnergyStored() * 100D);

	}
	
	protected double getEnergyPercentage4() {

		return ((double) this.container.getWind().getEnergyStored()/ (double) this.container.getWind().getMaxEnergyStored() * 100D);

	}
	

}
