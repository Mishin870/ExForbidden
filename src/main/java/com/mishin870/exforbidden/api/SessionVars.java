package com.mishin870.exforbidden.api;

public class SessionVars {
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