package com.mishin870.exforbidden.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class StringUtils {
	
	/**
	 * Получить высоту заданного текста
	 */
	public static int getTextHeigth(FontRenderer fontRenderer, int maxWidth, String... strings) {
		int lineCount = 0;
		for (String string : strings) lineCount += fontRenderer.listFormattedStringToWidth(string, maxWidth).size();
		return lineCount * fontRenderer.FONT_HEIGHT;
	}
	
}