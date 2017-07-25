package com.mishin870.exforbidden.forestrycomp.frame_analyzer;

import forestry.api.apiculture.IHiveFrame;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFrame extends Slot {
	
	public SlotFrame(IInventory inventory, int slotIndex, int xPos, int yPos) {
		super(inventory, slotIndex, xPos, yPos);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return itemStack.getItem() instanceof IHiveFrame;
	}
	
}