package com.mishin870.exforbidden.api.gui.widgets;

import com.mishin870.exforbidden.utils.StringUtils;

import forestry.api.core.IErrorState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class ErrorWidget extends Widget {
	private IErrorState state;
	
	public ErrorWidget(GuiContainer gui) {
		super(gui, WidgetColors.ERROR, null, false);
		this.maxHeight = 72;
	}
	
	public void setState(IErrorState state) {
		this.state = state;
		if (state != null) {
			this.key = String.valueOf(state.getID());
			int lineHeight = StringUtils.getTextHeigth(gui.mc.fontRenderer, this.maxTextWidth,
					new String[] {getTooltip(), StatCollector.translateToLocal(state.getHelp())});
			this.maxHeight = (lineHeight + 20);
		}
	}
	
	public void draw(int x, int y) {
		if (this.state == null) return;
		drawBackground(x, y);
		y += 4;
		
		drawIcon(this.state.getIcon(), x + 5, y);
		y += 4;
		if (isFullyOpened()) {
			y += drawHeader(getTooltip(), x + 24, y);
			y += 4;
			
			String helpString = StatCollector.translateToLocal(this.state.getHelp());
			drawText(helpString, x + 14, y, this.maxTextWidth);
		}
	}
	
	public boolean isVisible() {
		return this.state != null;
	}
	
	public String getTooltip() {
		if (!isVisible()) return "";
		return StatCollector.translateToLocal(this.state.getDescription());
	}
	
}