package com.mishin870.exforbidden.forestrycomp.frame_analyzer;

import org.lwjgl.opengl.GL11;

import com.mishin870.exforbidden.Main;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class FrameAnalyzerRenderer extends TileEntitySpecialRenderer {
	private static final ResourceLocation res = new ResourceLocation(Main.MODID, "textures/blocks/frame_analyzer.png");
	private ModelFrameAnalyzer m;
	
	public FrameAnalyzerRenderer() {
		m = new ModelFrameAnalyzer();
	}
	
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTick) {
		this.bindTexture(res);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glTranslatef(0.5F, 1.5F, 0.5F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		m.renderAll();
		GL11.glPopMatrix();
	}
	
}