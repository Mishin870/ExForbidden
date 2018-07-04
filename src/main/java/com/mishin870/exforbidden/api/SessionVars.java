package com.mishin870.exforbidden.api;

/**
 * Сессионные переменные, не обнуляющиеся при перезаходе в мир
 */
public class SessionVars {
	//Текущий открытый виджет (открыт может быть только один в одно время)
	private static Class openedWidget;
	private static String openedWidgetKey;
	
	public static void setOpenedWidget(Class widgetClass, String key) {
		openedWidget = widgetClass;
		openedWidgetKey = key;
	}
	
	public static Class getOpenedWidget() {
		return openedWidget;
	}
	
	public static String getOpenedWidgetKey() {
		return openedWidgetKey;
	}
}