package com.mishin870.exforbidden.forestrycomp.extra_carpenter;

import com.mishin870.exforbidden.gui.SimpleFluidTank;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityExtraCarpenter extends TileEntity implements IInventory, IFluidHandler {
	private ItemStack[] inventory;
	private SimpleFluidTank tank;
	
	public TileEntityExtraCarpenter() {
		inventory = new ItemStack[getSizeInventory()];
		//fluidStack = new FluidStack(FluidRegistry.LAVA, 3500);
		tank = new SimpleFluidTank(10000);
	}
	
	//Заглушка, т.к. танк в плотнике один и можно реализовать такой функцией
	public FluidStack getFluidInTank() {
		return tank.getFluid();
	}
	
	@Override
	public int getSizeInventory() {
		return 21;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}
	
	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack itemStack = getStackInSlot(i);
		if (itemStack != null) {
			if (itemStack.stackSize <= j) {
				setInventorySlotContents(i, null);
			} else {
				itemStack = itemStack.splitStack(j);
				markDirty();
			}
		}
		return itemStack;
	}
	
	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		ItemStack item = getStackInSlot(i);
		setInventorySlotContents(i, null);
		return item;
	}
	
	@Override
	public void setInventorySlotContents(int i, ItemStack itemStack) {
		inventory[i] = itemStack;
		markDirty();
	}

	@Override
	public String getInventoryName() {
		return "efgui.extra_carpenter";
	}
	
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return player.getDistanceSq(this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return true;
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return tank.drain(resource, doDrain);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}
	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}
	
	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}
	
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] {tank.getInfo()};
	}
	
}