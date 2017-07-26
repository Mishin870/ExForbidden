package com.mishin870.exforbidden.forestrycomp.extra_carpenter;

import org.lwjgl.opengl.GL11;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.api.gui.EFGuiBase;
import com.mishin870.exforbidden.gui.PuzzleWidget;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiExtraCarpenter extends EFGuiBase {
	private static final ResourceLocation res = new ResourceLocation(Main.MODID + ":textures/gui/extra_carpenter.png");
	private InventoryPlayer playerInv;
	private TileEntityExtraCarpenter analyzer;
	
	public GuiExtraCarpenter(InventoryPlayer playerInv, TileEntityExtraCarpenter analyzer) {
		super(new ContainerExtraCarpenter(playerInv, analyzer), analyzer, 176, 200);
		
		this.playerInv = playerInv;
		this.analyzer = analyzer;
	}
	
	@Override
	protected void initWidgers(IInventory inventory) {
		this.widgetManager.add(new PuzzleWidget(this));
		//this.addSimpleWidget("test", true);
		//this.addSimpleWidget("test2", true);
		//this.addSimpleWidget("testerr", false);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(res);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
}