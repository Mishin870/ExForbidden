package com.mishin870.exforbidden.proxy;

import com.mishin870.exforbidden.EFBlocks;
import com.mishin870.exforbidden.EFItems;
import com.mishin870.exforbidden.EFRecipes;
import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.forestrycomp.EFForestryErrors;
import com.mishin870.exforbidden.gui.EFGuiHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraftforge.common.MinecraftForge;

/**
 * Общая часть логики. База для клиентской и серверной.
 */
public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		EFItems.init();
		EFBlocks.init();
		
		registerRenderers();
		
		EFForestryErrors.init();
	}
	
	public void registerRenderers() {
		
	}
	
	public void init(FMLInitializationEvent e) {
		EFRecipes.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new EFGuiHandler());
	}
	
	public void postInit(FMLPostInitializationEvent e) {
//		FMLCommonHandler.instance().bus().register(new EMEventListener());
	}
	
}