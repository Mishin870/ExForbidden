package com.mishin870.exforbidden.utils;

import com.mishin870.exforbidden.EFLogger;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemInterface {
	
	public static ItemStack getForestryItemStack(String item) {
		return GameRegistry.findItemStack("Forestry", item, 1);
	}
	
	public static ItemStack getItemStack(String modId, String item) {
		return GameRegistry.findItemStack(modId, item, 1);
	}
	
	public static ItemStack getItemStack(String modId, String item, int stackSize) {
		return GameRegistry.findItemStack(modId, item, stackSize);
	}
	
	public static Item getForestryItem(String item) {
		Item i = GameRegistry.findItem("Forestry", item);
		if (i == null) EFLogger.warn("ItemInterface::getFrestryItem() - item not found: Forestry:" + item);
		return i;
	}
	
	public static Item getItem(String modId, String item) {
		Item i = GameRegistry.findItem(modId, item);
		if (i == null) EFLogger.warn("ItemInterface::getItem() - item not found: " + modId + ":" + item);
		return i;
	}
}
