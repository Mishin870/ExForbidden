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
		//Р­С‚Рѕ РїРµСЂРµР¶РёС‚РєРё РїСЂРѕС€Р»РѕРіРѕ. ExForbidden СЏ РїРѕСЃС‚СЂРѕРёР» РЅР° РѕСЃРЅРѕРІРµ СЃРІРѕРµРіРѕ СЃС‚Р°СЂРѕРіРѕ РјРѕРґР°
		//РЅРµ СѓРґР°Р»СЏСЋ, С‡С‚РѕР±С‹ РЅРµ Р·Р°Р±С‹С‚СЊ РЅРµРєРѕС‚РѕСЂС‹Рµ РІРµС‰Рё
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
	}
	
}