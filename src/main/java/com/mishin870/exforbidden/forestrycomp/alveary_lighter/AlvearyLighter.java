package com.mishin870.exforbidden.forestrycomp.alveary_lighter;

import com.mishin870.exforbidden.Main;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class AlvearyLighter extends Block implements ITileEntityProvider {
	private IIcon bottom, sideOff, sideOn;
	
	public AlvearyLighter(String unlocName) {
		super(Material.wood);
		this.setBlockName(unlocName);
		this.setBlockTextureName(Main.MODID + ":" + unlocName);
		this.setCreativeTab(Main.eftab);
		this.setHardness(1.0f);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.bottom = reg.registerIcon(this.textureName + ".bottom");
		this.sideOn = reg.registerIcon(this.textureName + ".on");
		//this.sideOff = reg.registerIcon(this.textureName + ".off");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0 || side == 1) {
			return this.bottom;
		} else {
			return sideOn;
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int p_149915_2_) {
		return new TileEntityAlvearyLighter();
	}

	/*@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer e, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		TileEntityAlvearyLighter te = (TileEntityAlvearyLighter) w.getTileEntity(x, y, z);
		te.trace();
		return true;
	}*/

}