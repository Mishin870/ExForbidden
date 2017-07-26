package com.mishin870.exforbidden.utils;

import com.mishin870.exforbidden.comp.EnumModsComp;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemInterface {
	
	public static ItemStack getItemStack(String modId, String item) {
		return GameRegistry.findItemStack(modId, item, 1);
	}
	
	public static ItemStack getItemStack(String modId, String item, int stackSize) {
		return GameRegistry.findItemStack(modId, item, stackSize);
	}
	
	public static Item getItem(String modId, String item) {
		Item i = GameRegistry.findItem(modId, item);
		if (i == null) EFLogger.warn("ItemInterface::getItem() - item not found: " + modId + ":" + item);
		return i;
	}
	
	public static ItemStack getForestryItemStack(String item) {
		return GameRegistry.findItemStack(EnumModsComp.FORESTRY.modName, item, 1);
	}
	
	public static Item getForestryItem(String item) {
		Item i = GameRegistry.findItem(EnumModsComp.FORESTRY.modName, item);
		if (i == null) EFLogger.warn("ItemInterface::getForestryItem() - item not found: Forestry:" + item);
		return i;
	}
}
