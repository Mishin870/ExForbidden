package com.mishin870.exforbidden.api.gui.widgets;

import com.mishin870.exforbidden.api.utils.StringUtils;
import com.mishin870.exforbidden.props.EFTextures;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class SimpleWidget extends Widget {
	private IIcon icon;
	
	/**
	 * Простая подсказка
	 * @param gui ссылка на интерфейс 
	 * @param key ключ в lang файле
	 */
	public SimpleWidget(GuiContainer gui, String key, boolean isRight, IIcon icon) {
		super(gui, getColorScheme(key + ".type"), key, isRight);
		this.icon = icon; //getIcon(key);
		Minecraft mc = FMLClientHandler.instance().getClient().getMinecraft();
		int lineHeight = StringUtils.getTextHeigth(mc.fontRenderer, this.maxTextWidth,
				new String[] {StatCollector.translateToLocal(key + ".title"), StatCollector.translateToLocal(key + ".desc")});
		this.maxHeight = lineHeight + 20;
	}
	
	public SimpleWidget(GuiContainer gui, String key, IIcon icon) {
		this(gui, key, true, icon);
	}
	
	/*private static IIcon getIcon(String key) {
		if (StatCollector.canTranslate(key + ".img")) {
			IIcon ret = EFTextures.getImage(StatCollector.translateToLocal(key + ".img"));
			if (ret == null) {
				//после вынесения в апи
				//EFLogger.warn("Warning! null IIcon in widget at: " + key + ".img. Setting to DEFAULT");
				System.out.println("Warning! null IIcon in widget at: " + key + ".img. Setting to DEFAULT");
				return EFTextures.getImage(key);
			} else {
				return ret;
			}
		} else {
			return EFTextures.getImage(key);
		}
	}*/
	
	private static WidgetColors getColorScheme(String key) {
		if (StatCollector.canTranslate(key)) {
			WidgetColors ret = WidgetColors.valueOf(StatCollector.translateToLocal(key));
			if (ret == null) {
				//EFLogger.warn("Warning! Unknown type of widget at: " + key + ". Setting to DEFAULT");
				System.out.println("Warning! Unknown type of widget at: " + key + ". Setting to DEFAULT");
				return WidgetColors.DEFAULT;
			} else {
				return ret;
			}
		} else {
			return WidgetColors.DEFAULT;
		}
	}
	
	@Override
	public void draw(int x, int y) {
		this.drawBackground(x, y);
		y += 3;
		this.drawIcon(icon, x + (isRight ? 3 : 4), y);
		y += 4;
		if (isFullyOpened()) {
			y += drawHeader(StatCollector.translateToLocal(key + ".title"), x + 24, y);
			y += 4;
			drawText(StatCollector.translateToLocal(key + ".desc"), x + 14, y, this.maxTextWidth);
		}
	}
	
	@Override
	public String getTooltip() {
		return StatCollector.translateToLocal(key + ".title");
	}
	
}