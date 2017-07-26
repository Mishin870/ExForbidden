package com.mishin870.exforbidden.utils;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class ItemUtils {
	
	/**
	 * Добавить массив из предметов в инвентарь
	 * @param inventory целевой инвентарь
	 * @param items предмет
	 * @param startSlot слот начала
	 * @param slotCount количество слотов
	 * @return количество добавленных предметов
	 */
	public static int tryAddItemStacksToInvenotry(IInventory inventory, ItemStack[] items, int startSlot, int slotCount) {
		int itemsAdded = 0;
		for (ItemStack item : items) {
			itemsAdded += addItemToInventory(inventory, item, startSlot, slotCount);
		}
		return itemsAdded;
	}
	
	/**
	 * Добавить массив из предметов в инвентарь
	 * @param inventory целевой инвентарь
	 * @param items предмет
	 * @param startSlot слот начала
	 * @param slotCount количество слотов
	 * @return true или false в случае удачи или неудачи
	 */
	public static boolean tryAddAllItemStacksToInventory(IInventory inventory, ItemStack[] items, int startSlot, int slotCount) {
		int itemsToAdd = 0, itemsAdded = 0;
		for (ItemStack item : items) {
			itemsToAdd += item.stackSize;
			itemsAdded += addItemToInventory(inventory, item, startSlot, slotCount);
		}
		return itemsToAdd == itemsAdded;
	}
	
	/**
	 * Добавить предмет в любые слоты
	 * @param inventory целевой инвентарь
	 * @param item предмет
	 * @param startSlot слот начала
	 * @param slotCount количество слотов
	 * @return количество добавленного предмета
	 */
	public static int addItemToInventory(IInventory inventory, ItemStack item, int startSlot, int slotCount) {
		int itemsAdded = 0;
		itemsAdded = addToExistingSlot(inventory, item, startSlot, slotCount);
		if (itemsAdded < item.stackSize) {
			item.stackSize -= itemsAdded;
			itemsAdded += addToEmptySlot(inventory, item, startSlot, slotCount);
		}
		return itemsAdded;
	}
	
	/**
	 * Добавить предмет в заполненные слоты
	 * @param inventory целевой инвентарь
	 * @param item предмет
	 * @param startSlot слот начала
	 * @param slotCount количество слотов
	 * @return количество добавленного предмета
	 */
	private static int addToExistingSlot(IInventory inventory, ItemStack item, int startSlot, int slotCount) {
		int itemsAdded = 0;
		for (int slotIndex = startSlot; slotIndex < startSlot + slotCount; slotIndex++) {
			ItemStack slotStack = inventory.getStackInSlot(slotIndex);
			if ((slotStack != null) && (areItemsEqualNBT(item, slotStack))) {
				int spaceAvailable = slotStack.getMaxStackSize() - slotStack.stackSize;
				if (spaceAvailable > item.stackSize) {
					slotStack.stackSize += item.stackSize;
					inventory.setInventorySlotContents(slotIndex, slotStack);
					itemsAdded += item.stackSize;
					item.stackSize = 0;
				} else if (spaceAvailable > 0) {
					slotStack.stackSize = slotStack.getMaxStackSize();
					inventory.setInventorySlotContents(slotIndex, slotStack);
					itemsAdded += spaceAvailable;
					item.stackSize -= spaceAvailable;
				}
			}
			if (item.stackSize == 0) {
				break;
			}
		}
		return itemsAdded;
	}
	
	/**
	 * Попытаться добавить весь стек предмета в пустой слот
	 * @param inventory целевой инвентарь
	 * @param item предмет
	 * @param startSlot слот начала
	 * @param slotCount количество слотов
	 * @return 0 в случае неудачи и размер входного стека при удаче
	 */
	private static int addToEmptySlot(IInventory inventory, ItemStack item, int startSlot, int slotCount) {
		for (int slotIndex = startSlot; slotIndex < startSlot + slotCount; slotIndex++) {
			ItemStack slotStack = inventory.getStackInSlot(slotIndex);
			if (slotStack == null) {
				inventory.setInventorySlotContents(slotIndex, item);
				return item.stackSize;
			}
		}
		return 0;
	}
	
	/**
	 * Полная проверка совпадения двух стеков
	 */
	public static boolean areItemsEqualNBT(ItemStack item, ItemStack otherItem) {
		return (item.isItemEqual(otherItem)) && (ItemStack.areItemStackTagsEqual(item, otherItem));
	}
}