package com.mishin870.exforbidden.api.gui.widgets;

import com.mishin870.exforbidden.api.ICVisContainer;
import com.mishin870.exforbidden.props.EFTextures;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class VisWidget extends Widget {
	private static final int AQUA = 0x3cd4fc;
	private static final int IGNIS = 0xff5a01;
	private static final int TERRA = 0x56c000;
	private static final int AER = 0xffff7e;
	private static final int ORDO = 0xd5d4ec;
	private static final int PERDITIO = 0x404040;
	private final ICVisContainer tile;
	private IIcon icon;
	
	public VisWidget(GuiContainer gui, ICVisContainer tile) {
		super(gui, WidgetColors.VIS, "vis", true);
		this.icon = EFTextures.getImage("efwidget.vis");
		this.tile = tile;
		Minecraft mc = FMLClientHandler.instance().getClient().getMinecraft();
		this.maxHeight = 20 + mc.fontRenderer.FONT_HEIGHT * 7;
	}
	
	@Override
	public void draw(int x, int y) {
		drawBackground(x, y);
		drawIcon(icon, x + 3, y + 4);
		if (!isFullyOpened()) return;
		x += 22;
		drawHeader(StatCollector.translateToLocal("efgui.vis"), x, y + 8);
		int currY = y + 22;
		String max = "/" + tile.getMax();
		currY += drawText("Aqua: " + tile.getAqua() + max, x, currY, maxTextWidth);
		currY += drawText("Ignis: " + tile.getIgnis() + max, x, currY, maxTextWidth);
		currY += drawText("Terra: " + tile.getTerra() + max, x, currY, maxTextWidth);
		currY += drawText("Aer: " + tile.getAir() + max, x, currY, maxTextWidth);
		currY += drawText("Ordo: " + tile.getOrdo() + max, x, currY, maxTextWidth);
		drawText("Perditio: " + tile.getPerditio() + max, x, currY, maxTextWidth);
	}
	
	@Override
	public String getTooltip() {
		return StatCollector.translateToLocal("efgui.vis");
	}
}
