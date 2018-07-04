package com.mishin870.exforbidden.api;

import net.minecraftforge.fluids.FluidStack;

public interface IEFFluidInventory {
	/**
	 * Установить жидкость в слот
	 * @param slot слот
	 * @param fluid жидкость
	 */
	public void setFluidSlotContents(int slot, FluidStack fluid);
	/**
	 * Получить жидкость из слота
	 * @param slot слот
	 * @return
	 */
	public FluidStack getFluidSlotContents(int slot);
}