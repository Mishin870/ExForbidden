package com.mishin870.exforbidden.forestrycomp.extra_carpenter;

import org.lwjgl.opengl.GL11;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.api.gui.EFGuiBase;
import com.mishin870.exforbidden.gui.PuzzleWidget;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GuiExtraCarpenter extends EFGuiBase {
	private static final ResourceLocation res = new ResourceLocation(Main.MODID + ":textures/gui/extra_carpenter.png");
	private static final ResourceLocation terrain = new ResourceLocation("/terraing.png");
	private InventoryPlayer playerInv;
	private TileEntityExtraCarpenter carpenter;

	public GuiExtraCarpenter(InventoryPlayer playerInv, TileEntityExtraCarpenter carpenter) {
		super(new ContainerExtraCarpenter(playerInv, carpenter), carpenter, 176, 200);

		this.playerInv = playerInv;
		this.carpenter = carpenter;
	}

	@Override
	protected void initWidgers(IInventory inventory) {
		this.widgetManager.add(new PuzzleWidget(this));
		// this.addSimpleWidget("test", true);
		// this.addSimpleWidget("test2", true);
		// this.addSimpleWidget("testerr", false);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(res);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		FluidStack fluid = carpenter.getFluidInTank();
		this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		IIcon icon = fluid.getFluid().getStillIcon();
		int fluidLevel = fluid.amount * 58 / 10000;
		this.drawTexturedModelRectFromIcon(150, 17 + 58 - fluidLevel, icon, 16, fluidLevel);
		
		this.mc.getTextureManager().bindTexture(res);
		this.drawTexturedModalRect(150, 18, 176, 0, 16, 58);
		
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		//this.mc.renderEngine.bindTexture(fluid.getFluid().getSpriteNumber());
		//this.drawTexturedModelRectFromIcon(xOffset + 8, yOffset + 143 - height, LiquidDictionary.getCanonicalLiquid("Water").getRenderingIcon(), 16, height);
	}

}