package com.mishin870.exforbidden.forestrycomp;

import com.mishin870.exforbidden.Main;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.apiculture.IBee;
import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeHousing;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.apiculture.IHiveFrame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;

/**
 * Основа рамок для пасеки.
 * Краткая справка: рамки используются в пасеках для модификации
 * свойств пчёл в них. Также рамки могут иметь собственную логику
 * с какими-либо побочными эффектами при потере единицы прочности (1 тик работы)
 */
public class EFFrame extends Item implements IHiveFrame {
	private EFFrameType type;

	public EFFrame(EFFrameType frameType) {
		this.type = frameType;
		this.setMaxDamage(this.type.maxDamage);
		this.setMaxStackSize(1);
		this.setCreativeTab(Main.eftab);
		String unloc = "frame_" + this.type.getName();
		this.setUnlocalizedName(unloc);
		this.setTextureName(Main.MODID + ":" + unloc);
	}
	
	/*@SideOnly(Side.CLIENT)
	public void func_94581_a(IIconRegister par1IconRegister) {
		this.field_77791_bV = par1IconRegister.func_94245_a("magicbees:frame" + this.type.getName());
	}*/
	
	@Override
	public ItemStack frameUsed(IBeeHousing hive, ItemStack frame, IBee queen, int wear) {
		frame.setItemDamage(frame.getItemDamage() + wear);
		if (frame.getItemDamage() >= frame.getMaxDamage()) frame = null;
		return frame;
	}
	
	@Override
	public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2) {
		return false;
	}

	@Override
	public IBeeModifier getBeeModifier() {
		return this.type;
	}
}