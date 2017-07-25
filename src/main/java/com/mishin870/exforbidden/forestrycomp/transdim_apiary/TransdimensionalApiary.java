package com.mishin870.exforbidden.forestrycomp.transdim_apiary;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.gui.EFGuiHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class TransdimensionalApiary extends BlockContainer {
	private IIcon up, down, side, side2;
	
	public TransdimensionalApiary(String unlocName) {
		super(Material.rock);
		this.setBlockName(unlocName);
		this.setBlockTextureName(Main.MODID + ":" + unlocName);
		this.setCreativeTab(Main.eftab);
		this.setHardness(2.0F);
		this.setResistance(2.5F);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		this.up = reg.registerIcon(this.textureName + ".up");
		this.down = reg.registerIcon(this.textureName + ".down");
		this.side = reg.registerIcon(this.textureName + ".side");
		this.side2 = reg.registerIcon(this.textureName + ".side.2");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if (side == 0) {
			return this.down;
		} else if (side == 1) {
			return this.up;
		} else if (side == 2 || side == 3) {
			return this.side;
		} else {
			return this.side2;
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if (!player.isSneaking()) {
			if (!world.isRemote) {
				player.openGui(Main.instance, EFGuiHandler.TRANSDIMENSIONAL_APIARY, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityTransdimensionalApiary();
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