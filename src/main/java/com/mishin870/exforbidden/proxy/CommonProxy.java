package com.mishin870.exforbidden.proxy;

import com.mishin870.exforbidden.EFBlocks;
import com.mishin870.exforbidden.EFItems;
import com.mishin870.exforbidden.EFRecipes;
import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.forestrycomp.EFForestryErrors;
import com.mishin870.exforbidden.gui.EFGuiHandler;
import com.mishin870.exforbidden.props.EFInternalConfig;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

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
		//Это пережитки прошлого. ExForbidden я построил на основе своего старого мода
		//не удаляю, чтобы не забыть некоторые вещи
		/*Main.emManaRegen = new PotionEMManaRegen(Main.cfg.POTION_MANAREGEN);
		Main.emLevitation = new PotionEMLevitation(Main.cfg.POTION_LEVITATION);
		Main.emEleonPortal = new PotionEMEleonPortal(Main.cfg.POTION_ELEON);
		EMCrafting.init();
		EMAchievements.init();
		Knowledges.init();
		AltarRecipes.init();
		ManaAltarRecipes.init();
		if (!DimensionManager.registerProviderType(Main.cfg.DIMS_ELEON, WorldProviderEleon.class, false)) {
			System.err.println("WARNING! Can't register eleon dimension. Change id in mod config");
		}
		DimensionManager.registerDimension(Main.cfg.DIMS_ELEON, Main.cfg.DIMS_ELEON);*/
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new EFGuiHandler());
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		//FMLCommonHandler.instance().bus().register(new EMEventListener());
		//MinecraftForge.EVENT_BUS.register(new RenderGUIHandler());
	}
	
}