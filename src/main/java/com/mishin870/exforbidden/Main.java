package com.mishin870.exforbidden;

import com.mishin870.exforbidden.comp.EnumModsComp;
import com.mishin870.exforbidden.net.NetworkPacketHandler;
import com.mishin870.exforbidden.props.EFConfig;
import com.mishin870.exforbidden.props.EFInternalConfig;
import com.mishin870.exforbidden.proxy.CommonProxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION, dependencies = EnumModsComp.softDependencies)
public class Main {
	public static final String MODID = "exforbidden";
	public static final String MODNAME = "ExForbidden";
	public static final String CHANNELNAME = "ExForbidden";
	public static final String VERSION = "1.0";
	
	@SidedProxy(clientSide="com.mishin870.exforbidden.proxy.ClientProxy", serverSide="com.mishin870.exforbidden.proxy.ServerProxy")
	public static CommonProxy proxy;
	
	@SideOnly(Side.CLIENT)
	public static Minecraft mc;
	public static CreativeTabs eftab;
	public static EFConfig cfg;
	
	public static boolean isClient = false;
	
	@Instance
	public static Main instance = new Main();
	
	public static NetworkPacketHandler netHandler;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		isClient = FMLCommonHandler.instance().getEffectiveSide().isClient();
		if (isClient) mc = Minecraft.getMinecraft();
		
		EFInternalConfig.init();
		
		EFEventHandler eventHandler = new EFEventHandler();
	    MinecraftForge.EVENT_BUS.register(eventHandler);
	    FMLCommonHandler.instance().bus().register(eventHandler);
		
		eftab = new EFCreativeTab();
		cfg = new EFConfig();
		netHandler = new NetworkPacketHandler();
		
		proxy.preInit(e);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}
	
	/**
	 * СњС‚РїСЂР°РІРёС‚СЊ СЃРѕРѕР±С‰РµРЅРёРµ РѕРїСЂРµРґРµР»РµРЅРЅРѕРјСѓ РёРіСЂРѕРєСѓ
	 * @param p РёРіСЂРѕРє
	 * @param msg СЃРѕРѕР±С‰РµРЅРёРµ
	 */
	public static final void chatMsg(EntityPlayer p, String msg) {
		p.addChatComponentMessage(new ChatComponentText(msg));
	}
	
	/**
	 * СњС‚РїСЂР°РІРёС‚СЊ СЃРѕРѕР±С‰РµРЅРёРµ Р»РѕРєР°Р»СЊРЅРѕРјСѓ РёРіСЂРѕРєСѓ
	 * @param msg СЃРѕРѕР±С‰РµРЅРёРµ
	 */
	@SideOnly(Side.CLIENT)
	public static final void sendToMe(String msg) {
		if (Main.isClient) {
			Main.chatMsg(Minecraft.getMinecraft().thePlayer, msg);
		}
	}
	
}