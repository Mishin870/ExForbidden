package com.mishin870.exforbidden.api;

import net.minecraftforge.fluids.FluidStack;

public interface IEFFluidChangeCallback {
	/**
	 * ���������� ������ ����� ��� ��������� �������� (��� �������� �������)
	 * @param slot ����
	 */
	public void fluidSlotChanged(int slot);
}