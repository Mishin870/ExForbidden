package com.mishin870.exforbidden.forestrycomp.frame_analyzer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class FrameAnalyzerBlockItemRenderer implements IItemRenderer {
	private FrameAnalyzerRenderer fr;
	private TileEntityFrameAnalyzer te;
	
	public FrameAnalyzerBlockItemRenderer(FrameAnalyzerRenderer fr, TileEntityFrameAnalyzer te) {
		this.fr = fr;
		this.te = te;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == IItemRenderer.ItemRenderType.ENTITY) GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
		te.blockMetadata = item.getItemDamage();
		TileEntityRendererDispatcher.instance.renderTileEntityAt(te, 0.0D, 0.0D, 0.0D, 0.0F);
	}

}
