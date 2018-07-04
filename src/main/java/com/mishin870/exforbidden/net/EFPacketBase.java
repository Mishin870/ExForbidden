package com.mishin870.exforbidden.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.mishin870.exforbidden.utils.EFLogger;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.common.registry.GameData;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Основа всех сетевых пакетов ExForbidden.
 * Предоставляет удобные функции кодирования и декодирования NBT и предметов.
 */
public class EFPacketBase {
	private NetworkPacketHandler.PacketType eventType;
	
	public EFPacketBase() {
		this.eventType = NetworkPacketHandler.PacketType.UNKNOWN;
	}
	
	public EFPacketBase(NetworkPacketHandler.PacketType type) {
		this.eventType = type;
	}
	
	/**
	 * Получить готовый сетевой пакет Forge на основе данных,
	 * записанных этим пакетом
	 * @return сетевой пакет
	 */
	public FMLProxyPacket getPacket() {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		DataOutputStream data = new DataOutputStream(bytes);
		try {
			data.writeInt(this.eventType.ordinal());
			writeDataToOutputStream(data);
		} catch (IOException e) {
			EFLogger.error("IOException in net.EventBase::getPacket()");
			e.printStackTrace();
		}
		return new FMLProxyPacket(Unpooled.wrappedBuffer(bytes.toByteArray()), NetworkPacketHandler.CHANNEL_NAME);
	}
	
	/**
	 * Слушатель обработки пакета (переопределяется)
	 * @param player игрок, с которым связан пакет
	 */
	public void process(EntityPlayerMP player) {}
	
	/**
	 * Слушатель отправки данных в канал (переопределяется)
	 * @param data канал
	 */
	protected void writeDataToOutputStream(DataOutputStream data) {}
	
	/**
	 * Слушатель приёма данных с канала (переопределяется)
	 * @param data канал
	 */
	protected void readDataFromInputStream(DataInputStream data) {}
	
	protected ItemStack readItemStackFromData(DataInputStream data) throws IOException {
		ItemStack itemstack = null;
		String itemName = data.readUTF();
		if (!itemName.isEmpty()) {
			Item item = (Item) GameData.getItemRegistry().getRaw(itemName);
			byte stackSize = data.readByte();
			short meta = data.readShort();
			itemstack = new ItemStack(item, stackSize, meta);
			if ((item.isDamageable()) || (item.getShareTag())) {
				itemstack.stackTagCompound = readNBTTagCompound(data);
			}
		}
		return itemstack;
	}
	
	protected void writeItemStackToData(ItemStack itemstack, DataOutputStream data) throws IOException {
		if (itemstack == null) {
			data.writeUTF("");
		} else {
			data.writeUTF(GameData.getItemRegistry().getNameForObject(itemstack.getItem()));
			data.writeByte(itemstack.stackSize);
			data.writeShort(itemstack.getItemDamage());
			if ((itemstack.getItem().isDamageable()) || (itemstack.getItem().getShareTag())) {
				writeNBTTagCompound(itemstack.stackTagCompound, data);
			}
		}
	}
	
	protected NBTTagCompound readNBTTagCompound(DataInputStream data) throws IOException {
		short length = data.readShort();
		if (length < 0) {
			return null;
		}
		byte[] compressed = new byte[length];
		data.readFully(compressed);
		return CompressedStreamTools.readCompressed(new ByteArrayInputStream(compressed));
	}
	
	protected void writeNBTTagCompound(NBTTagCompound nbttagcompound, DataOutputStream data) throws IOException {
		if (nbttagcompound == null) {
			data.writeShort(-1);
		} else {
			byte[] compressed = CompressedStreamTools.compress(nbttagcompound);
			data.writeShort((short) compressed.length);
			data.write(compressed);
		}
	}
}