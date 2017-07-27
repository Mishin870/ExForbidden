package com.mishin870.exforbidden.gui;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class SimpleFluidTank implements IFluidTank {
	private int capacity;
	private FluidStack fluid;
	
	public SimpleFluidTank(int capacity) {
		this.capacity = capacity;
	}
	
	@Override
	public FluidStack getFluid() {
		return fluid;
	}
	
	@Override
	public int getFluidAmount() {
		return fluid != null ? fluid.amount : 0;
	}
	
	@Override
	public int getCapacity() {
		return capacity;
	}
	
	@Override
	public FluidTankInfo getInfo() {
		return new FluidTankInfo(fluid, capacity);
	}
	
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (resource == null) return 0;
		if (fluid == null) {
			int add = Math.min(capacity, resource.amount);
			if (doFill) fluid = new FluidStack(resource.getFluid(), add);
			return add;
		} else {
			if (fluid.isFluidEqual(resource)) {
				int add = Math.min(capacity - fluid.amount, resource.amount);
				if (doFill) fluid.amount += add;
				return add;
			} else {
				return 0;
			}
		}
	}
	
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		//Должен ли я возвращать null здесь?
		//Комментарий гласит: @return Amount of fluid that was removed from the tank. 
		if (fluid == null) return null;
		if (fluid.amount >= maxDrain) {
			FluidStack ret = fluid.copy();
			ret.amount -= maxDrain;
			if (doDrain) fluid.amount -= maxDrain;
			return ret;
		} else {
			FluidStack ret = fluid.copy();
			if (doDrain) fluid = null;
			return ret;
		}
	}
	
	public FluidStack drain(FluidStack drain, boolean doDrain) {
		if (fluid == null) return null;
		if (fluid.isFluidEqual(drain)) {
			return drain(drain.amount, doDrain);
		} else {
			return null;
		}
	}
	
}