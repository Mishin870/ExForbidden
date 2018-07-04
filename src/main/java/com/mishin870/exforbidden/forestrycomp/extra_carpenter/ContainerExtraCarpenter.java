package com.mishin870.exforbidden.forestrycomp.extra_carpenter;

import com.mishin870.exforbidden.api.gui.EFContainerBase;

import forestry.api.apiculture.IHiveFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerExtraCarpenter extends EFContainerBase {
	private static final int SLOT_CRAFT_BEGIN = 0;
	private static final int SLOT_CRAFT_COUNT = 9;
	private static final int SLOT_INV_BEGIN = SLOT_CRAFT_BEGIN + SLOT_CRAFT_COUNT;
	private static final int SLOT_INV_COUNT = 9;
	private static final int SLOT_PRE = SLOT_INV_BEGIN + SLOT_INV_COUNT;
	private static final int SLOT_OUT = SLOT_PRE + 1;
	private static final int SLOT_INPUT_LIQUID = SLOT_OUT + 1;
	public TileEntityExtraCarpenter carpenter;
	
	public ContainerExtraCarpenter(InventoryPlayer inventoryPlayer, TileEntityExtraCarpenter carpenter) {
		super(carpenter);
		this.carpenter = carpenter;
		
		//addSlotToContainer(new SlotFrame(analyzer, 0, 153, 6));
		int x = 8;
		int y;
		int i;
		for (i = SLOT_INV_BEGIN; i < SLOT_INV_BEGIN + SLOT_INV_COUNT; i++) {
			addSlotToContainer(new Slot(carpenter, i, x, 90));
			x += 18;
		}
		
		x = 10;
		y = 20;
		i = SLOT_CRAFT_BEGIN;
		while (i < SLOT_CRAFT_BEGIN + SLOT_CRAFT_COUNT) {
			addSlotToContainer(new Slot(carpenter, i, x, y));
			x += 18;
			i++;
			addSlotToContainer(new Slot(carpenter, i, x, y));
			x += 18;
			i++;
			addSlotToContainer(new Slot(carpenter, i, x, y));
			i++;
			x = 10;
			y += 18;
		}
		
		addSlotToContainer(new Slot(carpenter, SLOT_PRE, 80, 51));
		addSlotToContainer(new Slot(carpenter, SLOT_OUT, 120, 56));
		addSlotToContainer(new Slot(carpenter, SLOT_INPUT_LIQUID, 120, 20));
		
		addPlayerInventory(inventoryPlayer, 8, 118);
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
		return this.carpenter.isUseableByPlayer(entityPlayer);
	}
	
}