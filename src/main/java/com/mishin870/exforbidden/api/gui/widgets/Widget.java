package com.mishin870.exforbidden.api.gui.widgets;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.api.SessionVars;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public abstract class Widget {
	private static final ResourceLocation widgetLeftTexture = new ResourceLocation(Main.MODID + ":textures/gui/widget_left.png");;
	private static final ResourceLocation widgetRightTexture = new ResourceLocation(Main.MODID + ":textures/gui/widget_right.png");;
	public int headerColor, subheaderColor, textColor, overlayColor;
	public int currentShiftX = 0;
	public int currentShiftY = 0;
	protected int limitWidth = 128;
	protected int maxTextWidth;
	protected int maxWidth = 124;
	protected int minWidth = 24;
	protected int currentWidth = minWidth;
	protected int maxHeight = 24;
	protected int minHeight = 24;
	protected int currentHeight = minHeight;
	protected boolean isRight;
	protected GuiContainer gui;
	private ResourceLocation texture;
	private boolean open;
	protected String key;
	
	public Widget(GuiContainer gui, WidgetColors colors, String key, boolean isRight) {
		this.gui = gui;
		this.isRight = isRight;
		texture = isRight ? widgetRightTexture : widgetLeftTexture;
		this.headerColor = colors.header;
		this.subheaderColor = colors.subheader;
		this.textColor = colors.text;
		this.overlayColor = colors.overlay;
		this.key = key;
		maxTextWidth = maxWidth - 18 - 14;
	}
	
	public int getWidth() {
		return Math.round(currentWidth);
	}
	
	public boolean getIsRight() {
		return isRight;
	}
	
	public String getKey() {
		return key;
	}
	
	public void update(long frames) {
		//Ширина
		if (open && currentWidth < maxWidth) {
			currentWidth += 4 * frames;
			if (currentWidth > maxWidth) currentWidth = maxWidth;
		} else if (!open && currentWidth > minWidth) {
			currentWidth -= 4 * frames;
			if (currentWidth < minWidth) currentWidth = minWidth;
		}
		//Высота
		if (open && currentHeight < maxHeight) {
			currentHeight += 4 * frames;
			if (currentHeight > maxHeight) currentHeight = maxHeight;
		} else if (!open && currentHeight > minHeight) {
			currentHeight -= 4 * frames;
			if (currentHeight < minHeight) currentHeight = minHeight;
		}
	}

	public int getHeight() {
		return currentHeight;
	}

	public abstract void draw(int x, int y);

	public abstract String getTooltip();

	public boolean handleMouseClicked(int x, int y, int mouseButton) {
		return false;
	}

	public boolean intersectsWith(int mouseX, int mouseY) {
		return mouseX >= currentShiftX && mouseX <= currentShiftX + currentWidth && mouseY >= currentShiftY
				&& mouseY <= currentShiftY + getHeight();
	}

	public void setFullyOpen() {
		open = true;
		currentWidth = maxWidth;
		currentHeight = maxHeight;
	}

	public void toggleOpen() {
		if (open) {
			open = false;
			SessionVars.setOpenedWidget(null, null);
		} else {
			open = true;
			SessionVars.setOpenedWidget(this.getClass(), this.getKey());
		}
	}

	public boolean isVisible() {
		return true;
	}

	public boolean isOpen() {
		return this.open;
	}

	protected boolean isFullyOpened() {
		return currentWidth >= maxWidth;
	}

	protected void drawBackground(int x, int y) {
		float colorR = (overlayColor >> 16 & 255) / 255.0F;
		float colorG = (overlayColor >> 8 & 255) / 255.0F;
		float colorB = (overlayColor & 255) / 255.0F;
		
		GL11.glColor4f(colorR, colorG, colorB, 1.0F);
		
		gui.mc.renderEngine.bindTexture(texture);
		gui.drawTexturedModalRect(x, y, 0, 256 - currentHeight, 4, currentHeight);
		gui.drawTexturedModalRect(x + 4, y, 256 - currentWidth + 4, 0, currentWidth - 4, 4);
		//Add in top left corner again
		gui.drawTexturedModalRect(x, y, 0, 0, 4, 4);
		gui.drawTexturedModalRect(x + 4, y + 4, 256 - currentWidth + 4, 256 - currentHeight + 4, currentWidth - 4, currentHeight - 4);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
	}
	
	protected void drawIcon(IIcon icon, int x, int y) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		gui.mc.renderEngine.bindTexture(TextureMap.locationItemsTexture);
		gui.drawTexturedModelRectFromIcon(x, y, icon, 16, 16);
	}
	
	protected int drawHeader(String string, int x, int y) {
		return drawMultilineText(string, x, y, this.maxTextWidth, this.headerColor, true);
	}
	
	protected int drawSubheader(String string, int x, int y) {
		return drawMultilineText(string, x, y, this.maxTextWidth, this.subheaderColor, true);
	}
	
	protected int drawText(String string, int x, int y, int width) {
		return drawMultilineText(string, x, y, width, this.textColor, false);
	}
	
	protected int drawColored(String string, int x, int y, int width, int color) {
		return drawMultilineText(string, x, y, width, color, false);
	}
	
	protected int drawMultilineText(String string, int x, int y, int width, int color, boolean shadow) {
		int originalY = y;
		Minecraft mc = gui.mc;
		List strings = mc.fontRenderer.listFormattedStringToWidth(string, width);
		for (Object str : strings) {
			mc.fontRenderer.drawString((String) str, x, y, color, shadow);
			y += mc.fontRenderer.FONT_HEIGHT;
		}
		return y - originalY;
	}
	
	/*protected int drawText(String string, int x, int y) {
		Minecraft mc = gui.mc;
		mc.fontRenderer.drawString(string, x, y, this.textColor);
		return mc.fontRenderer.FONT_HEIGHT;
	}*/
}