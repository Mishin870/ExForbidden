package com.mishin870.exforbidden.forestrycomp.frame_analyzer;

import com.mishin870.exforbidden.api.gui.EFContainerBase;

import forestry.api.apiculture.IHiveFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFrameAnalyzer extends EFContainerBase {
	public TileEntityFrameAnalyzer analyzer;
	
	public ContainerFrameAnalyzer(InventoryPlayer inventoryPlayer, TileEntityFrameAnalyzer analyzer) {
		super(analyzer);
		this.analyzer = analyzer;
		
		addSlotToContainer(new SlotFrame(analyzer, 0, 153, 6));
		addPlayerInventory(inventoryPlayer, 8, 140);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int from) {
		Slot fromSlot = getSlot(from);
		boolean clearSlot = false;
		if ((fromSlot != null) && (fromSlot.getHasStack())) {
			ItemStack srcStack = fromSlot.getStack();
			if (from == 0) {
				clearSlot = mergeItemStack(srcStack, 1, 36, false);
			} else if (srcStack.getItem() instanceof IHiveFrame) {
				clearSlot = mergeItemStack(srcStack, 0, 1, false);
			} else {
				return srcStack;
			}
		}
		if (clearSlot) fromSlot.putStack(null);
		fromSlot.onSlotChanged();
		player.inventory.markDirty();
		
		return null;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return this.analyzer.isUseableByPlayer(entityPlayer);
	}
	
}