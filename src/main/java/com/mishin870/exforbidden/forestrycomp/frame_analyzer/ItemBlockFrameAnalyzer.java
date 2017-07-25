package com.mishin870.exforbidden.forestrycomp.frame_analyzer;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockFrameAnalyzer extends ItemBlockWithMetadata {
	
	public ItemBlockFrameAnalyzer(Block b) {
		super(b, b);
	}
	
	/*@Override
	public String getUnlocalizedName(ItemStack is) {
		return this.getUnlocalizedName() + "_" + is.getItemDamage();
	}*/
	
}
