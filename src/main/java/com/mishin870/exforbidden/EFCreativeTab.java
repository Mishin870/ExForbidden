package com.mishin870.exforbidden;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * Вкладка ExForbidden в меню креатива
 */
public class EFCreativeTab extends CreativeTabs {
	
	public EFCreativeTab() {
		super(CreativeTabs.getNextID(), Main.MODNAME);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return EFItems.frameTest;
	}
	
}