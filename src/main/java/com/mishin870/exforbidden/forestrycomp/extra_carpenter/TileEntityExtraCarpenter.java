package com.mishin870.exforbidden.forestrycomp.extra_carpenter;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class TileEntityExtraCarpenter extends TileEntity implements IInventory {
	private ItemStack inv;
	
	public TileEntityExtraCarpenter() {
		
	}
	
	@Override
	public int getSizeInventory() {
		return 1;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return slot == 0 ? inv : null;
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
		if (i == 0) {
			inv = itemStack;
			markDirty();
		}
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
		return 1;
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
	
}