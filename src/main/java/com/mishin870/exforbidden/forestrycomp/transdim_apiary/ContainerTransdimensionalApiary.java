package com.mishin870.exforbidden.forestrycomp.transdim_apiary;

import com.mishin870.exforbidden.api.gui.EFContainerBase;
import com.mishin870.exforbidden.api.items.IBiomeStabilizer;
import com.mishin870.exforbidden.gui.SlotCustomItems;
import com.mishin870.exforbidden.utils.ItemInterface;

import forestry.api.apiculture.BeeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTransdimensionalApiary extends EFContainerBase {
	public TileEntityTransdimensionalApiary apiary;
	public int maxSlot = 0;
	private static final int SLOT_QUEEN = 0;
	private static final int SLOT_DRONE = 1;
	private static final int SLOT_BIOME_STABILIZER = 2;
	private static final int SLOT_INVENTORY_START = 3;
	private static final int SLOT_INVENTORY_COUNT = 7;

	public ContainerTransdimensionalApiary(InventoryPlayer inventoryPlayer, TileEntityTransdimensionalApiary apiary) {
		super(apiary);
		this.apiary = apiary;
		addSlotToContainer(new SlotCustomItems(this.apiary, 0, 29, 39,
				new ItemStack[] { ItemInterface.getItemStack("Forestry", "beeQueenGE", 1),
						ItemInterface.getItemStack("Forestry", "beePrincessGE", 1) }));
		
		addSlotToContainer(new SlotCustomItems(this.apiary, 1, 29, 65,
				new ItemStack[] { ItemInterface.getItemStack("Forestry", "beeDroneGE", 64) }));
		
		addSlotToContainer(new SlotBiomeStabilizer(apiary, 2, 66, 23 + 1 * 29));
		
		int currentSlot = SLOT_INVENTORY_START - 1;
		for (int x = 0; x < 3; x++) {
			currentSlot++;
			addSlotToContainer(new Slot(apiary, currentSlot, 116, 26 + x * 26));
		}
		int j = 0;
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 2; x++) {
				currentSlot++;
				addSlotToContainer(new Slot(apiary, currentSlot, 95 + x * 42, 39 + j * 26));
			}
			j++;
		}
		addPlayerInventory(inventoryPlayer, 8, 108);
		
		this.maxSlot = currentSlot;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		Slot itemSlot = getSlot(slotIndex);
		boolean clearSlot = false;
		if ((itemSlot != null) && (itemSlot.getHasStack())) {
			ItemStack srcStack = itemSlot.getStack();
			if ((slotIndex <= this.maxSlot) && (srcStack != null)) {
				clearSlot = mergeItemStack(srcStack, this.maxSlot + 1, this.maxSlot + 36, false);
			} else if ((slotIndex > this.maxSlot) && (srcStack != null)) {
				if (BeeManager.beeRoot.isMember(srcStack)) {
					if (!BeeManager.beeRoot.isDrone(srcStack)) {
						if (!getSlot(0).getHasStack()) {
							clearSlot = mergeItemStack(srcStack, 0, 1, false);
						}
					} else if (getSlot(1).isItemValid(srcStack)) {
						clearSlot = mergeItemStack(srcStack, 1, 2, false);
					}
				} else if ((srcStack.getItem() instanceof IBiomeStabilizer)) {
					clearSlot = mergeItemStack(srcStack, 2, 5, false);
				}
			}
		}
		if (clearSlot) {
			itemSlot.putStack(null);
		}
		itemSlot.onSlotChanged();
		player.inventory.markDirty();

		return null;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return this.apiary.isUseableByPlayer(entityPlayer);
	}
	
	@Override
	public void updateProgressBar(int i, int j) {
		this.apiary.getGUINetworkData(i, j);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (Object crafter : this.crafters) {
			this.apiary.sendGUINetworkData(this, (ICrafting) crafter);
		}
	}
}
