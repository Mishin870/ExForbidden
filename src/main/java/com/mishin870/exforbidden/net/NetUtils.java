package com.mishin870.exforbidden.net;

import com.mishin870.exforbidden.Main;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class NetUtils {
	
	public static void sendNetworkPacket(EFPacketBase packet, int x, int z, World world) {
		if (!(world instanceof WorldServer)) return;
		WorldServer worldServer = (WorldServer) world;
		PlayerManager playerManager = worldServer.getPlayerManager();
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		for (Object playerObj : world.playerEntities) {
			if ((playerObj instanceof EntityPlayerMP)) {
				EntityPlayerMP player = (EntityPlayerMP) playerObj;
				if (playerManager.isPlayerWatchingChunk(player, chunkX, chunkZ)) {
					sendToPlayer(packet, player);
				}
			}
		}
	}
	
	public static void sendToPlayer(EFPacketBase packet, EntityPlayerMP player) {
		Main.netHandler.sendPacket(packet.getPacket(), player);
	}
	
}