package com.mishin870.exforbidden.forestrycomp.extra_carpenter;

import java.util.List;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.gui.EFGuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ExtraCarpenter extends Block implements ITileEntityProvider {
	private static final float MINH = 0.0f;
	private static final float MAXH = 1.0f;
	private static final float MAXV = 1.0f;
	
	public ExtraCarpenter(String unloc) {
		super(Material.wood);
		setBlockName(unloc);
		setBlockTextureName(Main.MODID + ":" + unloc);
		setCreativeTab(Main.eftab);
		setHardness(1.0f);
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
			player.openGui(Main.instance, EFGuiHandler.EXTRA_CARPENTER, world, x, y, z);
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
		TileEntityExtraCarpenter f = new TileEntityExtraCarpenter();
		f.blockMetadata = meta;
		return f;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		TileEntity te = world.getTileEntity(x, y, z);
		if ((te != null) && ((te instanceof IInventory))) {
			IInventory inventory = (IInventory) te;
			for (int i = 0; i < inventory.getSizeInventory(); i++) {
				ItemStack is = inventory.getStackInSlotOnClosing(i);
				if (is != null) {
					float spawnX = x + world.rand.nextFloat();
					float spawnY = y + world.rand.nextFloat();
					float spawnZ = z + world.rand.nextFloat();
					EntityItem droppedItem = new EntityItem(world, spawnX, spawnY, spawnZ, is);
					float mult = 0.05F;
					droppedItem.motionX = ((-0.5F + world.rand.nextFloat()) * mult);
					droppedItem.motionY = ((4.0F + world.rand.nextFloat()) * mult);
					droppedItem.motionZ = ((-0.5F + world.rand.nextFloat()) * mult);
					world.spawnEntityInWorld(droppedItem);
				}
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	
}