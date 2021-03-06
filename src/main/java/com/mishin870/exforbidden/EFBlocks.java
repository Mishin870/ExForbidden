package com.mishin870.exforbidden;

import com.mishin870.exforbidden.forestrycomp.alveary_lighter.AlvearyLighter;
import com.mishin870.exforbidden.forestrycomp.alveary_lighter.TileEntityAlvearyLighter;
import com.mishin870.exforbidden.forestrycomp.extra_carpenter.ExtraCarpenter;
import com.mishin870.exforbidden.forestrycomp.extra_carpenter.TileEntityExtraCarpenter;
import com.mishin870.exforbidden.forestrycomp.frame_analyzer.FrameAnalyzer;
import com.mishin870.exforbidden.forestrycomp.frame_analyzer.TileEntityFrameAnalyzer;
import com.mishin870.exforbidden.forestrycomp.transdim_apiary.TileEntityTransdimensionalApiary;
import com.mishin870.exforbidden.forestrycomp.transdim_apiary.TransdimensionalApiary;
import com.mishin870.exforbidden.thaumcraftcomp.EssentiaContainer;
import com.mishin870.exforbidden.thaumcraftcomp.TileEntityEssentiaContainer;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Блоки ExForbidden
 */
public class EFBlocks {
	private static final String ALVEARY_LIGHTER = "alveary_lighter";
	private static final String ESSENTIA_CONTAIENR = "essentia_container";
	private static final String TRANSDIMENSIONAL_APIARY = "transdimensional_apiary";
	private static final String FRAME_ANALYZER = "frame_analyzer";
	private static final String EXTRA_CARPENTER = "extra_carpenter";
	public static AlvearyLighter alvearyLighter;
	public static EssentiaContainer essentiaContainer;
	public static TransdimensionalApiary transdimensionalApiary;
	public static FrameAnalyzer frameAnalyzer;
	public static ExtraCarpenter extraCarpenter;
	
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
		
		extraCarpenter = new ExtraCarpenter(EXTRA_CARPENTER);
		GameRegistry.registerBlock(extraCarpenter, EXTRA_CARPENTER);
		GameRegistry.registerTileEntity(TileEntityExtraCarpenter.class, "extraCarpenterTileEntity");
	}
	
}