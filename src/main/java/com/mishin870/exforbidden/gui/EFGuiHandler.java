package com.mishin870.exforbidden.gui;

import com.mishin870.exforbidden.forestrycomp.extra_carpenter.ContainerExtraCarpenter;
import com.mishin870.exforbidden.forestrycomp.extra_carpenter.GuiExtraCarpenter;
import com.mishin870.exforbidden.forestrycomp.extra_carpenter.TileEntityExtraCarpenter;
import com.mishin870.exforbidden.forestrycomp.frame_analyzer.ContainerFrameAnalyzer;
import com.mishin870.exforbidden.forestrycomp.frame_analyzer.GuiFrameAnalyzer;
import com.mishin870.exforbidden.forestrycomp.frame_analyzer.TileEntityFrameAnalyzer;
import com.mishin870.exforbidden.forestrycomp.transdim_apiary.ContainerTransdimensionalApiary;
import com.mishin870.exforbidden.forestrycomp.transdim_apiary.GuiTransdimensionalApiary;
import com.mishin870.exforbidden.forestrycomp.transdim_apiary.TileEntityTransdimensionalApiary;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EFGuiHandler implements IGuiHandler {
	public static final int TRANSDIMENSIONAL_APIARY = 0;
	public static final int FRAME_ANALYZER = 1;
	public static final int EXTRA_CARPENTER = 2;
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer p, World w, int x, int y, int z) {
		if (id == TRANSDIMENSIONAL_APIARY) {
			return new ContainerTransdimensionalApiary(p.inventory, (TileEntityTransdimensionalApiary) w.getTileEntity(x, y, z));
		} else if (id == FRAME_ANALYZER) {
			return new ContainerFrameAnalyzer(p.inventory, (TileEntityFrameAnalyzer) w.getTileEntity(x, y, z));
		} else if (id == EXTRA_CARPENTER) {
			return new ContainerExtraCarpenter(p.inventory, (TileEntityExtraCarpenter) w.getTileEntity(x, y, z));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer p, World w, int x, int y, int z) {
		if (id == TRANSDIMENSIONAL_APIARY) {
			return new GuiTransdimensionalApiary(p.inventory, (TileEntityTransdimensionalApiary) w.getTileEntity(x, y, z));
		} else if (id == FRAME_ANALYZER) {
			return new GuiFrameAnalyzer(p.inventory, (TileEntityFrameAnalyzer) w.getTileEntity(x, y, z));
		} else if (id == EXTRA_CARPENTER) {
			return new GuiExtraCarpenter(p.inventory, (TileEntityExtraCarpenter) w.getTileEntity(x, y, z));
		}
		return null;
	}

}