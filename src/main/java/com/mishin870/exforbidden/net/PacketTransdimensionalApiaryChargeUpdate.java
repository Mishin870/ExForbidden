package com.mishin870.exforbidden.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mishin870.exforbidden.forestrycomp.transdim_apiary.TileEntityTransdimensionalApiary;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

public class PacketTransdimensionalApiaryChargeUpdate extends EFPacketBase {
	private int[] charges;
	private int x, y, z;
	
	public PacketTransdimensionalApiaryChargeUpdate(int charges[], int x, int y, int z) {
		super(NetworkPacketHandler.PacketType.TRANSDIMENSIONAL_APIARY_CHARGE_UPDATE);
		this.charges = charges;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public PacketTransdimensionalApiaryChargeUpdate(DataInputStream byteStream) {
		super(NetworkPacketHandler.PacketType.TRANSDIMENSIONAL_APIARY_CHARGE_UPDATE);
		readDataFromInputStream(byteStream);
	}
	
	protected void writeDataToOutputStream(DataOutputStream byteStream) {
		super.writeDataToOutputStream(byteStream);
		try {
			byteStream.writeInt(x);
			byteStream.writeInt(y);
			byteStream.writeInt(z);
			//т.к. значения зарядов не должны превышать 255 (и не будут)
			for (int i = 0; i < charges.length; i++) byteStream.writeByte(charges[i]);
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
			
			this.charges = new int[6];
			for (int i = 0; i < charges.length; i++) charges[i] = byteStream.readByte();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void process(EntityPlayerMP player) {
		TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(x, y, z);
		if ((tile != null) && ((tile instanceof TileEntityTransdimensionalApiary))) {
			TileEntityTransdimensionalApiary apiary = (TileEntityTransdimensionalApiary) tile;
			apiary.setCharges(charges);
		}
	}
}