package com.mishin870.exforbidden.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mishin870.exforbidden.forestrycomp.transdim_apiary.TileEntityTransdimensionalApiary;

import cpw.mods.fml.client.FMLClientHandler;
import forestry.api.core.IErrorLogicSource;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;

/**
 * Пакет обновления визуального списка ошибок в контейнере
 * (например, "пчёлам недостаточно света" или "недостаточно энергии" и т.д.)
 */
public class PacketContainerUpdateErrors extends EFPacketBase {
	private IErrorLogicSource errorSource;
	private int x, y, z;
	private DataInputStream byteStream;
	
	public PacketContainerUpdateErrors(IErrorLogicSource errorSource, int x, int y, int z) {
		super(NetworkPacketHandler.PacketType.CONTAINER_UPDATE_ERRORS);
		this.errorSource = errorSource;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public PacketContainerUpdateErrors(DataInputStream byteStream) {
		super(NetworkPacketHandler.PacketType.CONTAINER_UPDATE_ERRORS);
		this.byteStream = byteStream;
		//readDataFromInputStream(byteStream);
	}
	
	protected void writeDataToOutputStream(DataOutputStream byteStream) {
		super.writeDataToOutputStream(byteStream);
		try {
			byteStream.writeInt(x);
			byteStream.writeInt(y);
			byteStream.writeInt(z);
			errorSource.getErrorLogic().writeData(byteStream);
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
			TileEntity tile = FMLClientHandler.instance().getClient().theWorld.getTileEntity(x, y, z);
			if (tile instanceof IErrorLogicSource) {
				((IErrorLogicSource) tile).getErrorLogic().readData(byteStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void process(EntityPlayerMP player) {
		readDataFromInputStream(byteStream);
	}
}