package com.mishin870.exforbidden.forestrycomp.transdim_apiary;

import java.util.ArrayList;
import java.util.Collections;

import com.mishin870.exforbidden.api.items.IBiomeStabilizer;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBiomeStabilizer extends Slot {
	
	public SlotBiomeStabilizer(IInventory inventory, int slotIndex, int xPos, int yPos) {
		super(inventory, slotIndex, xPos, yPos);
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		return itemStack.getItem() instanceof IBiomeStabilizer;
	}
	
}