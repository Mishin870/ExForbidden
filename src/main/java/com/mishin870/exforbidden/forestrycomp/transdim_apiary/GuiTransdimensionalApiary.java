package com.mishin870.exforbidden.forestrycomp.transdim_apiary;

import org.lwjgl.opengl.GL11;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.api.gui.EFGuiBase;
import com.mishin870.exforbidden.api.gui.widgets.ClimateWidget;
import com.mishin870.exforbidden.api.gui.widgets.SimpleWidget;
import com.mishin870.exforbidden.api.gui.widgets.VisWidget;
import com.mishin870.exforbidden.gui.PuzzleWidget;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiTransdimensionalApiary extends EFGuiBase {
	private static final ResourceLocation res = new ResourceLocation(Main.MODID + ":textures/gui/transdimensional_apiary.png");
	private InventoryPlayer playerInv;
	private TileEntityTransdimensionalApiary apiary;
	private static final int LIFE_WID = 2;
	private static final int LIFE_HEI = 46;
	private static final int LIFE_X = 21;
	private static final int LIFE_Y = 37;
	private static final int LIFE_LEVELS_Y = 0;
	private static final int[] LIFE_LEVELS_X = new int[] {176, 178, 180, 182, 184};
	
	public GuiTransdimensionalApiary(InventoryPlayer playerInv, TileEntityTransdimensionalApiary apiary) {
		super(new ContainerTransdimensionalApiary(playerInv, apiary), apiary, 176, 190);
		
		this.playerInv = playerInv;
		this.apiary = apiary;
	}
	
	@Override
	protected void initWidgers(IInventory inventory) {
		this.widgetManager.add(new ClimateWidget(this, (TileEntityTransdimensionalApiary) tile));
		this.widgetManager.add(new VisWidget(this, (TileEntityTransdimensionalApiary) tile));
		this.widgetManager.add(new PuzzleWidget(this));
		//this.addSimpleWidget("test", true);
		//this.addSimpleWidget("test2", true);
		//this.addSimpleWidget("testerr", false);
	}
	
	private void addSimpleWidget(String key, boolean isRight) {
		this.widgetManager.add(new SimpleWidget(this, "efwidget." + key, isRight));
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		//Это на случай, если мне понадобится выводить название блока (не понадобится)
		/*String s = this.te.getDisplayName().getUnformattedText();
	    this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);            //#404040
	    this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);      //#404040*/
	}
	
	private void drawLifebar() {
		int value = 46 - apiary.getHealthScaled(46);
		int lifebarSrc = getLifeLevelX();
		drawTexturedModalRect(this.guiLeft + 21, this.guiTop + value + 37, lifebarSrc, 0, 2, 46 - value);
	}
	
	private int getLifeLevelX() {
		int val = apiary.getHealthScaled(5);
		if (val >= 5) {
			return LIFE_LEVELS_X[4];
		} else if (val >= 4) {
			return LIFE_LEVELS_X[3];
		} else if (val >= 3) {
			return LIFE_LEVELS_X[2];
		} else if (val >= 2) {
			return LIFE_LEVELS_X[1];
		} else {
			return LIFE_LEVELS_X[0];
		}
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(res);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		drawLifebar();
	}
	
}