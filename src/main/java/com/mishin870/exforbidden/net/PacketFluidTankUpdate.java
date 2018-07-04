package com.mishin870.exforbidden.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mishin870.exforbidden.api.IEFFluidInventory;
import com.mishin870.exforbidden.utils.EFLogger;

import cpw.mods.fml.client.FMLClientHandler;
import forestry.api.core.IErrorLogicSource;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Пакет обновления жидкостного слота в контейнере
 * Например, слот с лавой
 */
public class PacketFluidTankUpdate extends EFPacketBase {
	private IEFFluidInventory inventory;
	private int x, y, z, slot;
	private DataInputStream byteStream;
	
	public PacketFluidTankUpdate(IEFFluidInventory inventory, int x, int y, int z, int slot) {
		super(NetworkPacketHandler.PacketType.FLUID_TANK_UPDATE);
		this.inventory = inventory;
		this.x = x;
		this.y = y;
		this.z = z;
		this.slot = slot;
	}
	
	public PacketFluidTankUpdate(DataInputStream byteStream) {
		super(NetworkPacketHandler.PacketType.FLUID_TANK_UPDATE);
		this.byteStream = byteStream;
		//readDataFromInputStream(byteStream);
	}
	
	protected void writeDataToOutputStream(DataOutputStream byteStream) {
		super.writeDataToOutputStream(byteStream);
		try {
			byteStream.writeInt(x);
			byteStream.writeInt(y);
			byteStream.writeInt(z);
			byteStream.writeInt(slot);
			FluidStack stack = inventory.getFluidSlotContents(slot);
			if (stack == null) {
				byteStream.writeInt(0);
			} else {
				byteStream.writeInt(stack.amount);
				byteStream.writeUTF(FluidRegistry.getFluidName(stack));
			}
			//errorSource.getErrorLogic().writeData(byteStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void readDataFromInputStream(DataInputStream byteStream) {
		super.readDataFromInputStream(byteStream);
		try {
			this.x = byteStream.readInt();
			this.y = byteStream.readInt();
			this.z = byteStream.readInt();
			this.slot = byteStream.readInt();
			int amount = byteStream.readInt();
			if (amount != 0) {
				String fluidName = byteStream.readUTF();
				TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(x, y, z);
				if (tile instanceof IEFFluidInventory) {
					((IEFFluidInventory) tile).setFluidSlotContents(slot, new FluidStack(FluidRegistry.getFluid(fluidName), amount));
				}
			} else {
				TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(x, y, z);
				if (tile instanceof IEFFluidInventory) {
					((IEFFluidInventory) tile).setFluidSlotContents(slot, null);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void process(EntityPlayerMP player) {
		readDataFromInputStream(byteStream);
	}
}