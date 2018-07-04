package com.mishin870.exforbidden.api.gui.widgets;

/**
 * Цветовые схемы виджетов
 */
public enum WidgetColors {
	DEFAULT(0xffffff, 0xe1c92f, 0x000000), HINT(0xea38ff, 0xe1c92f, 0x000000), ERROR(0xff3535, 0xe1c92f, 0x000000),
	CLIMATE(0x35a4ff, 0xe1c92f, 0x000000, 0xaaafb8), VIS(0xB8F24D, 0xe1c92f, 0x000000, 0xaaafb8);
	public int overlay, header, text, subheader = 0xaaafb8;
	
	WidgetColors(int overlay, int header, int text) {
		this.overlay = overlay;
		this.header = header;
		this.text = text;
	}
	
	WidgetColors(int overlay, int header, int text, int subheader) {
		this(overlay, header, text);
		this.subheader = subheader;
	}
	
}