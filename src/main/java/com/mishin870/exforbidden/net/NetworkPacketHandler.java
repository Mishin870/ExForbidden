package com.mishin870.exforbidden.net;

import java.io.DataInputStream;
import java.io.IOException;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.forestrycomp.transdim_apiary.TileEntityTransdimensionalApiary;
import com.mishin870.exforbidden.utils.EFLogger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

public class NetworkPacketHandler {
	public static final String CHANNEL_NAME = Main.CHANNELNAME;
	private FMLEventChannel channel;
	
	public static NetworkPacketHandler getInstance() {
		return Main.netHandler;
	}
	
	public static enum PacketType {
		UNKNOWN, TRANSDIMENSIONAL_APIARY_CHARGE_UPDATE, CONTAINER_UPDATE_ERRORS;
	}
	
	public NetworkPacketHandler() {
		this.channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(CHANNEL_NAME);
		this.channel.register(this);
	}
	
	@SubscribeEvent
	public void onPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
		parseAndDispatchPacket(event.packet.payload(), ((NetHandlerPlayServer) event.handler).playerEntity);
	}
	
	@SubscribeEvent
	public void onPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
		parseAndDispatchPacket(event.packet.payload(), null);
	}

	/*public void sendTransdimChargesUpdate(TileEntityTransdimensionalApiary apiary, int[] charges) {
		PacketTransdimensionalApiaryChargeUpdate event = new PacketTransdimensionalApiaryChargeUpdate(charges, apiary.xCoord, apiary.yCoord, apiary.zCoord);
		FMLProxyPacket packet = event.getPacket();
		sendPacket(packet);
	}*/
	
	private void sendPacket(FMLProxyPacket packet) {
		if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
			this.channel.sendToAll(packet);
		} else {
			this.channel.sendToServer(packet);
		}
	}
	
	public void sendPacket(FMLProxyPacket packet, EntityPlayerMP player) {
		this.channel.sendTo(packet, player);
	}
	
	private void parseAndDispatchPacket(ByteBuf packetPayload, EntityPlayerMP player) {
		try {
			DataInputStream data = new DataInputStream(new ByteBufInputStream(packetPayload));
			processEventData(data, player);
			data.close();
		} catch (IOException e) {
			EFLogger.info("IOException in NetworkEventHandler::parseAndDispatchPacket()");
		}
	}
	
	private void processEventData(DataInputStream data, EntityPlayerMP player) throws IOException {
		int eventId = data.readInt();
		if (eventId == PacketType.TRANSDIMENSIONAL_APIARY_CHARGE_UPDATE.ordinal()) {
			PacketTransdimensionalApiaryChargeUpdate packet = new PacketTransdimensionalApiaryChargeUpdate(data);
			packet.process(player);
		} else if (eventId == PacketType.CONTAINER_UPDATE_ERRORS.ordinal()) {
			PacketContainerUpdateErrors packet = new PacketContainerUpdateErrors(data);
			packet.process(player);
		}
	}
}