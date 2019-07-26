package me.asuramagica.tools;

import com.mojang.blaze3d.platform.GlStateManager;

import me.asuramagica.AsuraMagicaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class customGUI {
	
	public static final Minecraft mc = Minecraft.getInstance();
	public static void drawModalRectWithCustomSizedTexture(double x1, double x2, double y1, double y2, ResourceLocation texture) {
	      GlStateManager.disableDepthTest();
	      GlStateManager.depthMask(false);
	      GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	      GlStateManager.disableAlphaTest();
	      mc.getTextureManager().bindTexture(texture);
	      Tessellator tessellator = Tessellator.getInstance();
	      BufferBuilder bufferbuilder = tessellator.getBuffer();
	      bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
//botlef  bufferbuilder.pos(0.0D, (double)mc.mainWindow.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
	      bufferbuilder.pos(x1, y1, -90.0D).tex(0.0D, 1.0D).endVertex();
	      
//botr    bufferbuilder.pos((double)mc.mainWindow.getScaledWidth(), (double)mc.mainWindow.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
	      bufferbuilder.pos(x2, y1, -90.0D).tex(1.0D, 1.0D).endVertex();
	      
//topr	  bufferbuilder.pos((double)mc.mainWindow.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
	      bufferbuilder.pos(x2, y2, -90.0D).tex(1.0D, 0.0D).endVertex();
	      
//topl    bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
	      bufferbuilder.pos(x1, y2, -90.0D).tex(0.0D, 0.0D).endVertex();

	      tessellator.draw();
	      GlStateManager.depthMask(true);
	      GlStateManager.enableDepthTest();
	      GlStateManager.enableAlphaTest();
	      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
