package com.mishin870.exforbidden.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCustomItems extends Slot {
	private List<ItemStack> items;

	public SlotCustomItems(IInventory inventory, int slotIndex, int xPos, int yPos, ItemStack... items) {
		super(inventory, slotIndex, xPos, yPos);
		this.items = new ArrayList(items.length);
		Collections.addAll(this.items, items);
	}
	
	public List<ItemStack> getValidItems() {
		return this.items;
	}
	
	public void addValidItem(ItemStack newItem) {
		boolean contains = false;
		for (int i = 0; i < this.items.size(); i++) {
			ItemStack s = (ItemStack) this.items.get(i);
			if (s.getItem() == newItem.getItem()) {
				if (s.getItemDamage() == 32767) {
					contains = true;
					break;
				}
				if (s.getItemDamage() == newItem.getItemDamage()) {
					contains = true;
					break;
				}
			}
		}
		if (!contains) this.items.add(newItem);
	}
	
	public void removeValidItem(ItemStack removeItem) {
		for (int i = 0; i < this.items.size(); i++) {
			ItemStack s = (ItemStack) this.items.get(i);
			if (s.getItem() == removeItem.getItem()) {
				if (s.getItemDamage() == 32767) {
					this.items.remove(i);
					break;
				}
				if (s.getItemDamage() == removeItem.getItemDamage()) {
					this.items.remove(i);
					break;
				}
			}
		}
	}
	
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		for (ItemStack item : this.items) {
			if ((item != null) && (item.getItem() == itemStack.getItem())) {
				if ((item.getItemDamage() == itemStack.getItemDamage()) || (item.getItemDamage() == 32767)) {
					return true;
				}
			}
		}
		return false;
	}
}