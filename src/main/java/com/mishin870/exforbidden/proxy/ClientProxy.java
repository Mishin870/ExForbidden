package com.mishin870.exforbidden.proxy;

import com.mishin870.exforbidden.EFBlocks;
import com.mishin870.exforbidden.forestrycomp.extra_carpenter.ExtraCarpenterBlockItemRenderer;
import com.mishin870.exforbidden.forestrycomp.extra_carpenter.ExtraCarpenterRenderer;
import com.mishin870.exforbidden.forestrycomp.extra_carpenter.TileEntityExtraCarpenter;
import com.mishin870.exforbidden.forestrycomp.frame_analyzer.FrameAnalyzerBlockItemRenderer;
import com.mishin870.exforbidden.forestrycomp.frame_analyzer.FrameAnalyzerRenderer;
import com.mishin870.exforbidden.forestrycomp.frame_analyzer.TileEntityFrameAnalyzer;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);
	}
	
	@Override
	public void registerRenderers() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFrameAnalyzer.class, new FrameAnalyzerRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EFBlocks.frameAnalyzer), new FrameAnalyzerBlockItemRenderer(new FrameAnalyzerRenderer(), new TileEntityFrameAnalyzer()));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityExtraCarpenter.class, new ExtraCarpenterRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(EFBlocks.extraCarpenter), new ExtraCarpenterBlockItemRenderer(new ExtraCarpenterRenderer(), new TileEntityExtraCarpenter()));
	}
	
	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
		//MinecraftForge.EVENT_BUS.register(new EFRenderGUIHandler());
	}
	
}