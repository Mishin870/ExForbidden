package com.mishin870.exforbidden.api.gui.widgets;

import forestry.api.core.EnumTemperature;
import forestry.api.genetics.AlleleManager;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.StatCollector;

/**
 * Виджет контроля климата пасеки
 */
public class ClimateWidget extends Widget {
	private final IClimateProvider tile;
	
	public ClimateWidget(GuiContainer gui, IClimateProvider tile) {
		super(gui, WidgetColors.CLIMATE, "climate", true);
		this.tile = tile;
		this.maxHeight = 72;
	}
	
	private String percent(float f) {
		return ((int) (f * 100.0F)) + " %";
	}
	
	@Override
	public void draw(int x, int y) {
		EnumTemperature temperature = this.tile.getTemperature();
		drawBackground(x, y);
		drawIcon(temperature.getIcon(), x + 3, y + 4);
		if (!isFullyOpened()) return;
		drawHeader(StatCollector.translateToLocal("efgui.climate"), x + 22, y + 8);
		drawSubheader(StatCollector.translateToLocal("efgui.temperature") + ':', x + 22, y + 20);
		drawText(AlleleManager.climateHelper.toDisplay(temperature) + ' ' + percent(this.tile.getExactTemperature()), x + 22, y + 32, maxTextWidth);
		drawSubheader(StatCollector.translateToLocal("efgui.humidity") + ':', x + 22, y + 44);
		drawText(AlleleManager.climateHelper.toDisplay(this.tile.getHumidity()) + ' ' + percent(this.tile.getExactHumidity()), x + 22, y + 56, maxTextWidth);
	}
	
	@Override
	public String getTooltip() {
		return "T: " + AlleleManager.climateHelper.toDisplay(this.tile.getTemperature()) + " / H: " + AlleleManager.climateHelper.toDisplay(this.tile.getHumidity());
	}
}
