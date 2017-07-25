package com.mishin870.exforbidden.forestrycomp.frame_analyzer;

import org.lwjgl.opengl.GL11;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.api.gui.EFGuiBase;
import com.mishin870.exforbidden.gui.PuzzleWidget;

import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiFrameAnalyzer extends EFGuiBase {
	private static final ResourceLocation res = new ResourceLocation(Main.MODID + ":textures/gui/frame_analyzer.png");
	private InventoryPlayer playerInv;
	private TileEntityFrameAnalyzer analyzer;
	
	public GuiFrameAnalyzer(InventoryPlayer playerInv, TileEntityFrameAnalyzer analyzer) {
		super(new ContainerFrameAnalyzer(playerInv, analyzer), analyzer, 176, 222);
		
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
		ItemStack stack = analyzer.getStackInSlot(0);
		if ((stack != null) && (stack.getItem() instanceof IHiveFrame)) {
			IBeeModifier frame = ((IHiveFrame) stack.getItem()).getBeeModifier();
			FontRenderer fr = mc.fontRenderer;
			
			int cy = 10;
			
			String btrue = StatCollector.translateToLocal("efgui.yes");
			String bfalse = StatCollector.translateToLocal("efgui.no");
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.name") + stack.getDisplayName(), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.damage") + stack.getMaxDamage(), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.territory") + frame.getTerritoryModifier(null, 1.0f), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.mutation") + frame.getMutationModifier(null, null, 1.0f), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.life") + frame.getLifespanModifier(null, null, 1.0f), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.production") + frame.getProductionModifier(null, 1.0f), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.flowering") + frame.getFloweringModifier(null, 1.0f), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.decay") + frame.getGeneticDecay(null, 1.0f), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.sealed") + (frame.isSealed() ? btrue : bfalse), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.lit") + (frame.isSelfLighted() ? btrue : bfalse), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.sunlit") + (frame.isSunlightSimulated() ? btrue : bfalse), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
			
			fr.drawString(StatCollector.translateToLocal("efgui.frame_analyzer.hellish") + (frame.isHellish() ? btrue : bfalse), 10, cy, 0xffffff, true);
			cy += fr.FONT_HEIGHT;
		}
		
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}
	
}