package com.mishin870.exforbidden.api.gui.widgets;

import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

/**
 * Интерфейс предоставления информации о температуре и влажности
 * Может применяться чем угодно и где угодно
 */
public abstract interface IClimateProvider {
	public abstract EnumTemperature getTemperature();
	public abstract EnumHumidity getHumidity();
	public abstract float getExactTemperature();
	public abstract float getExactHumidity();
}
