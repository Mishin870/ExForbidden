package com.mishin870.exforbidden.api;

import net.minecraftforge.fluids.FluidStack;

public interface IEFFluidInventory {
	/**
	 * ���������� �������� � ����
	 * @param slot ����
	 * @param fluid ��������
	 */
	public void setFluidSlotContents(int slot, FluidStack fluid);
	/**
	 * �������� �������� �� �����
	 * @param slot ����
	 * @return
	 */
	public FluidStack getFluidSlotContents(int slot);
}