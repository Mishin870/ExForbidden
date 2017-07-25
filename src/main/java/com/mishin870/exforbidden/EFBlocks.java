package com.mishin870.exforbidden;

import com.mishin870.exforbidden.forestrycomp.alveary_lighter.AlvearyLighter;
import com.mishin870.exforbidden.forestrycomp.alveary_lighter.TileEntityAlvearyLighter;
import com.mishin870.exforbidden.forestrycomp.frame_analyzer.FrameAnalyzer;
import com.mishin870.exforbidden.forestrycomp.frame_analyzer.TileEntityFrameAnalyzer;
import com.mishin870.exforbidden.forestrycomp.transdim_apiary.TileEntityTransdimensionalApiary;
import com.mishin870.exforbidden.forestrycomp.transdim_apiary.TransdimensionalApiary;
import com.mishin870.exforbidden.thaumcraftcomp.EssentiaContainer;
import com.mishin870.exforbidden.thaumcraftcomp.TileEntityEssentiaContainer;

import cpw.mods.fml.common.registry.GameRegistry;

public class EFBlocks {
	private static final String ALVEARY_LIGHTER = "alveary_lighter";
	private static final String ESSENTIA_CONTAIENR = "essentia_container";
	private static final String TRANSDIMENSIONAL_APIARY = "transdimensional_apiary";
	private static final String FRAME_ANALYZER = "frame_analyzer";
	public static AlvearyLighter alvearyLighter;
	public static EssentiaContainer essentiaContainer;
	public static TransdimensionalApiary transdimensionalApiary;
	public static FrameAnalyzer frameAnalyzer;
	
	/**
	 * Инициализация всех блоков
	 */
	public static final void init() {
		alvearyLighter = new AlvearyLighter(ALVEARY_LIGHTER);
		GameRegistry.registerBlock(alvearyLighter, ALVEARY_LIGHTER);
		GameRegistry.registerTileEntity(TileEntityAlvearyLighter.class, "alvearyLighterTileEntity");
		
		essentiaContainer = new EssentiaContainer(ESSENTIA_CONTAIENR);
		GameRegistry.registerBlock(essentiaContainer, ESSENTIA_CONTAIENR);
		GameRegistry.registerTileEntity(TileEntityEssentiaContainer.class, "essentiaContainerTileEntity");
		
		transdimensionalApiary = new TransdimensionalApiary(TRANSDIMENSIONAL_APIARY);
		GameRegistry.registerBlock(transdimensionalApiary, TRANSDIMENSIONAL_APIARY);
		GameRegistry.registerTileEntity(TileEntityTransdimensionalApiary.class, "transdimensionalApiaryTileEntity");
		
		frameAnalyzer = new FrameAnalyzer(FRAME_ANALYZER);
		GameRegistry.registerBlock(frameAnalyzer, FRAME_ANALYZER);
		GameRegistry.registerTileEntity(TileEntityFrameAnalyzer.class, "frameAnalyzerTileEntity");
	}
	
}