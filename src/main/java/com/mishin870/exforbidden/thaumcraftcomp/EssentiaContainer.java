package com.mishin870.exforbidden.thaumcraftcomp;

import com.mishin870.exforbidden.Main;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * База для всех блоков, хранящих эссенции Thaumcraft
 * по механизму ExForbidden
 */
public class EssentiaContainer extends Block implements ITileEntityProvider {
	
	public EssentiaContainer(String unlocName) {
		super(Material.rock);
		this.setBlockName(unlocName);
		this.setBlockTextureName(Main.MODID + ":" + unlocName);
		this.setCreativeTab(Main.eftab);
		this.setHardness(2.0f);
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int p_149915_2_) {
		return new TileEntityEssentiaContainer();
	}

	/*@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer e, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		TileEntityAlvearyLighter te = (TileEntityAlvearyLighter) w.getTileEntity(x, y, z);
		te.trace();
		return true;
	}*/

}