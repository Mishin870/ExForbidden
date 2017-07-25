package com.mishin870.exforbidden.api.gui;

import java.util.HashSet;

import com.google.common.collect.ImmutableSet;
import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.net.EFPacketBase;
import com.mishin870.exforbidden.net.PacketContainerUpdateErrors;

import forestry.api.core.IErrorLogicSource;
import forestry.api.core.IErrorState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class EFContainerBase extends Container {
	protected final int PLAYER_INVENTORY_ROWS = 3;
	protected final int PLAYER_INVENTORY_COLUMNS = 9;
	protected TileEntity tile;
	private ImmutableSet<IErrorState> prevStates = ImmutableSet.copyOf(new HashSet<IErrorState>());
	
	public EFContainerBase(TileEntity tile) {
		this.tile = tile;
	}
	
	public void addPlayerInventory(InventoryPlayer inventoryPlayer, int xStart, int yStart) {
		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(inventoryPlayer, x, xStart + 18 * x, yStart + 4 + 54));
		}
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(inventoryPlayer, x + y * 9 + 9, xStart + 18 * x, yStart + y * 18));
			}
		}
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if (this.tile instanceof IErrorLogicSource) {
			IErrorLogicSource errorSource = (IErrorLogicSource) this.tile;
			ImmutableSet<IErrorState> errorStates = errorSource.getErrorLogic().getErrorStates();
			if (!errorStates.equals(this.prevStates)) {
				PacketContainerUpdateErrors packet = new PacketContainerUpdateErrors(errorSource, tile.xCoord, tile.yCoord, tile.zCoord);
				sendPacketToCrafters(packet);
			}
			this.prevStates = errorStates;
		}
	}
	
	protected final void sendPacketToCrafters(EFPacketBase packet) {
		for (Object crafter : this.crafters) {
			if ((crafter instanceof EntityPlayer)) {
				Main.netHandler.sendPacket(packet.getPacket(), (EntityPlayerMP) crafter);
			}
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer entityPlayer) {
		return true;
	}
}
