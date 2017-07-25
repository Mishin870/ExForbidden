package com.mishin870.exforbidden;

import com.mishin870.exforbidden.forestrycomp.EFFrame;
import com.mishin870.exforbidden.forestrycomp.EFFrameType;
import com.mishin870.exforbidden.forestrycomp.biome_stabilizer.BiomeStabilizer;
import com.mishin870.exforbidden.forestrycomp.biome_stabilizer.BiomeStabilizerType;

import cpw.mods.fml.common.registry.GameRegistry;

public final class EFItems {
	public static EFFrame frameTest;
	public static BiomeStabilizer biomeStabilizer;
	
	/**
	 * Инициализация всех предметов
	 */
	public static final void init() {
		frameTest = new EFFrame(EFFrameType.TEST);
		GameRegistry.registerItem(frameTest, frameTest.getUnlocalizedName());
		
		biomeStabilizer = new BiomeStabilizer();
		GameRegistry.registerItem(biomeStabilizer, biomeStabilizer.getUnlocalizedName());
	}
	
}
