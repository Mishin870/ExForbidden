package com.mishin870.exforbidden;

import com.mishin870.exforbidden.forestrycomp.EFForestryErrors;
import com.mishin870.exforbidden.props.EFTextures;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;

public class EFEventHandler {
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void handleTextureRemap(TextureStitchEvent.Pre event) {
		//0 = blocks, 1 = items
		if (event.map.getTextureType() == 1) {
			EFTextures.init(event.map);
			EFForestryErrors.registerIcons(event.map);
		}
	}
	
}