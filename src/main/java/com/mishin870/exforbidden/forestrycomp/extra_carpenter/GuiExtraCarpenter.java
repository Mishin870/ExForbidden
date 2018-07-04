package com.mishin870.exforbidden.forestrycomp.extra_carpenter;

import org.lwjgl.opengl.GL11;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.api.gui.EFGuiBase;
import com.mishin870.exforbidden.gui.PuzzleWidget;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class GuiExtraCarpenter extends EFGuiBase {
	private static final ResourceLocation res = new ResourceLocation(Main.MODID + ":textures/gui/extra_carpenter.png");
	//private static final ResourceLocation terrain = new ResourceLocation("/terraing.png");
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
		FluidStack fluid = carpenter.getFluidInTank();
		if (fluid != null) {
			this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
			int fluidLevel = fluid.amount * 58 / 10000;
			//this.drawTexturedModelRectFromIcon(150, 17 + 58 - fluidLevel, icon, 16, fluidLevel);
			this.drawFluidPillar(150, 17 + 58 - fluidLevel, fluid.getFluid().getIcon(fluid), fluidLevel);
		}
		
		//GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		//this.mc.getTextureManager().bindTexture(res);
		//this.drawTexturedModalRect(150, 17, 176, 0, 16, 58);
		
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
	/**
	 * Рисует столб жидкости с заранее определенной шириной (16)
	 */
	private void drawFluidPillar(int x, int y, IIcon icon, int height) {
		for (int i = 0; i < height; i += 16) {
			drawTexturedModelRectFromIcon(x, y + i, icon, 16, Math.min(height - i, 16));
		}
	}
	
	/*
	public void drawFluid(FluidStack fluid, int level, int x, int y, int width, int height) {
        IIcon icon = fluid.getFluid().getIcon(fluid);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        setGLColorFromInt(fluid.getFluid().getColor(fluid));
        int fullX = width / 16;
        int fullY = height / 16;
        int lastX = width - fullX * 16;
        int lastY = height - fullY * 16;
        int fullLvl = (height - level) / 16;
        int lastLvl = (height - level) - fullLvl * 16;
        for (int i = 0; i < fullX; i++) {
            for (int j = 0; j < fullY; j++) {
                if (j >= fullLvl) {
                    drawCutIcon(icon, x + i * 16, y + j * 16, 16, 16, j == fullLvl ? lastLvl : 0);
                }
            }
        }
        for (int i = 0; i < fullX; i++) {
            drawCutIcon(icon, x + i * 16, y + fullY * 16, 16, lastY, fullLvl == fullY ? lastLvl : 0);
        }
        for (int i = 0; i < fullY; i++) {
            if (i >= fullLvl) {
                drawCutIcon(icon, x + fullX * 16, y + i * 16, lastX, 16, i == fullLvl ? lastLvl : 0);
            }
        }
        drawCutIcon(icon, x + fullX * 16, y + fullY * 16, lastX, lastY, fullLvl == fullY ? lastLvl : 0);
    }
	
    private final void drawCutIcon(IIcon icon, int x, int y, int width, int height, int cut) {
        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();
        tess.addVertexWithUV(x, y + height, 0, icon.getMinU(), icon.getInterpolatedV(height));
        tess.addVertexWithUV(x + width, y + height, 0, icon.getInterpolatedU(width), icon.getInterpolatedV(height));
        tess.addVertexWithUV(x + width, y + cut, 0, icon.getInterpolatedU(width), icon.getInterpolatedV(cut));
        tess.addVertexWithUV(x, y + cut, 0, icon.getMinU(), icon.getInterpolatedV(cut));
        tess.draw();
    }
    
    private static void setGLColorFromInt(int color) {
        GL11.glColor4f((color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, 1.0F);
    }*/
	
}