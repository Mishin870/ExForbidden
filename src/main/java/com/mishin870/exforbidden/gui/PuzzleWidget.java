package com.mishin870.exforbidden.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.api.gui.widgets.Widget;
import com.mishin870.exforbidden.api.gui.widgets.WidgetColors;
import com.mishin870.exforbidden.props.EFInternalConfig;
import com.mishin870.exforbidden.props.EFTextures;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class PuzzleWidget extends Widget {
	private static final int RESET_DELAY = 5000;
	private static final int SIZE = 64;
	private static final int PART_SIZE = 16;
	private static List<ResourceLocation> imgs = new ArrayList<ResourceLocation>();
	private ResourceLocation current;
	private IIcon icon;
	private byte[] state = new byte[4 * 4];
	private byte sel = -1;
	private int headerHeight = 0;
	private boolean win = false;
	private long resetTime;
	
	@Override
	public boolean handleMouseClicked(int x, int y, int mouseButton) {
		if (!isFullyOpened()) return false;
		x -= currentShiftX + 24;
		y -= currentShiftY + headerHeight;
		if (x >= 0 && y >= 0 && x < SIZE + PART_SIZE && y < SIZE + PART_SIZE) {
			x /= (PART_SIZE + 1);
			y /= (PART_SIZE + 1);
			if (sel == -1) {
				sel = (byte) (y * 4 + x);
			} else {
				byte sel2 = (byte) (y * 4 + x);
				if (sel2 != sel) {
					byte tmp = state[sel];
					state[sel] = state[sel2];
					state[sel2] = tmp;
					sel = -1;
					checkWin();
				}
			}
			//sel = (byte) (y * 4 + x);
			return true;
		} else {
			return false;
		}
	}
	
	private void checkWin() {
		for (byte i = 0; i < 16; i++) {
			//если хоть 1 ячейка не совпадает, то выходим
			if (state[i] != i) return;
		}
		win = true;
		resetTime = System.currentTimeMillis() + RESET_DELAY;
	}

	public PuzzleWidget(GuiContainer gui) {
		super(gui, WidgetColors.DEFAULT, "puzzle", true);
		this.icon = EFTextures.getImage("efwidget.puzzle");
		Minecraft mc = FMLClientHandler.instance().getClient().getMinecraft();
		this.maxHeight = mc.fontRenderer.FONT_HEIGHT + 20 + 80;
	}
	
	private void randomizeImage() {
		win = false;
		Random rnd = new Random();
		current = imgs.get(rnd.nextInt(imgs.size()));
		for (byte i = 0; i < 16; i++) state[i] = i;
		int i1, i2;
		byte tmp;
		for (byte i = 0; i < 8; i++) {
			i1 = rnd.nextInt(16);
			i2 = rnd.nextInt(16);
			tmp = state[i1];
			state[i1] = state[i2];
			state[i2] = tmp;
		}
	}
	
	public static void init() {
		int num = Integer.parseInt(EFInternalConfig.getVal("puzzle.count"));
		for (int i = 0; i < num; i++) imgs.add(new ResourceLocation(Main.MODID + ":textures/puzzles/puzzle" + i + ".png"));
	}
	
	@Override
	public void draw(int x, int y) {
		this.drawBackground(x, y);
		this.drawIcon(icon, x + 3, y + 3);
		if (isFullyOpened()) {
			if (current == null) randomizeImage();
			x += 24;
			if (win) {
				if (System.currentTimeMillis() >= resetTime) randomizeImage();
				headerHeight = drawHeader(StatCollector.translateToLocal("efgui.puzzle_win"), x, y + 7) + 11;
			} else {
				headerHeight = drawHeader(StatCollector.translateToLocal("efgui.puzzle"), x, y + 7) + 11;
			}
			y += headerHeight;
			GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			gui.mc.renderEngine.bindTexture(current);
			for (byte i = 0; i < 16; i++) {
				byte s = state[i];
				drawPart(x + (i % 4) * (PART_SIZE + 1), y + (i / 4) * (PART_SIZE + 1), s % 4, s / 4);
			}
			if (sel != -1) {
				GL11.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
				//gui.drawRect(p_73734_0_, p_73734_1_, p_73734_2_, p_73734_3_, p_73734_4_);
			    int selx = x + (sel % 4) * (PART_SIZE + 1);
			    int sely = y + (sel / 4) * (PART_SIZE + 1);
			    GL11.glBegin(GL11.GL_LINE_LOOP);
			    GL11.glVertex2f(selx - 1, sely - 1);
			    GL11.glVertex2f(selx + PART_SIZE + 1, sely - 1);
			    GL11.glVertex2f(selx + PART_SIZE + 1, sely + PART_SIZE + 1);
			    GL11.glVertex2f(selx - 1, sely + PART_SIZE + 1);
			    GL11.glEnd();
			}
		}
	}
	
	private void drawPart(int x, int y, int px, int py) {
		final float coeff = 0.25f;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + PART_SIZE), 0.0D, px * coeff, (py + 1) * coeff);
		tessellator.addVertexWithUV((double) (x + PART_SIZE), (double) (y + PART_SIZE), 0.0D, (px + 1) * coeff, (py + 1) * coeff);
		tessellator.addVertexWithUV((double) (x + PART_SIZE), (double) (y + 0), 0.0D, (px + 1) * coeff, py * coeff);
		tessellator.addVertexWithUV((double) (x + 0), (double) (y + 0), 0.0D, px * coeff, py * coeff);
		tessellator.draw();
	}
	
	@Override
	public String getTooltip() {
		return StatCollector.translateToLocal("efgui.puzzle");
	}
	
}