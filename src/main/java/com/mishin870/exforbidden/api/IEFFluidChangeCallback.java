package com.mishin870.exforbidden.api;

import net.minecraftforge.fluids.FluidStack;

public interface IEFFluidChangeCallback {
	/**
	 * Вызывается танком когда его состояние меняется (для отправки клиенту)
	 * @param slot слот
	 */
	public void fluidSlotChanged(int slot);
}