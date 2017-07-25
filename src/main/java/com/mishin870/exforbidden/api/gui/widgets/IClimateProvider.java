package com.mishin870.exforbidden.api.gui.widgets;

import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;

public abstract interface IClimateProvider {
	public abstract EnumTemperature getTemperature();
	public abstract EnumHumidity getHumidity();
	public abstract float getExactTemperature();
	public abstract float getExactHumidity();
}
