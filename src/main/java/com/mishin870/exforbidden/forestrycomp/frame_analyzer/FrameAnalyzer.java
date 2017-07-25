package com.mishin870.exforbidden.forestrycomp.frame_analyzer;

import java.util.List;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.gui.EFGuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class FrameAnalyzer extends Block implements ITileEntityProvider {
	private static final float MINH = 0.0f;
	private static final float MAXH = 1.0f;
	private static final float MAXV = 0.875f;
	
	public FrameAnalyzer(String unloc) {
		super(Material.wood);
		setBlockName(unloc);
		setBlockTextureName(Main.MODID + ":" + unloc);
		setCreativeTab(Main.eftab);
		setHardness(0.0f);
	}
	
	@Override
	public int getRenderType() {
		return -1;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float sideX, float sideY, float sideZ) {
		if (player.isSneaking()) {
			return false;
		} else {
			player.openGui(Main.instance, EFGuiHandler.FRAME_ANALYZER, world, x, y, z);
			return true;
		}
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
		this.setBlockBounds(MINH, 0, MINH, MAXH, MAXV, MAXH);
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		TileEntityFrameAnalyzer f = new TileEntityFrameAnalyzer();
		f.blockMetadata = meta;
		return f;
	}
	
}