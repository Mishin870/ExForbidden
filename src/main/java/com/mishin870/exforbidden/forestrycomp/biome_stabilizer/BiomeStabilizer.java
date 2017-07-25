package com.mishin870.exforbidden.forestrycomp.biome_stabilizer;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.api.items.IBiomeStabilizer;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class BiomeStabilizer extends Item implements IBiomeStabilizer {
	private BiomeStabilizerType[] types;
	private String[] priceDescs;
	private IIcon[] icons;
	
	public BiomeStabilizer() {
		types = BiomeStabilizerType.values();
		priceDescs = new String[types.length];
		
		int i = 0;
		for (BiomeStabilizerType type : types) {
			AspectList price = type.getPrice();
			String priceDesc = "";
			Iterator<Entry<Aspect, Integer>> iter = price.aspects.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<Aspect, Integer> entry = iter.next();
				priceDesc += " \u00A7" + entry.getKey().getChatcolor() + entry.getValue().intValue();
			}
			priceDescs[i] = priceDesc;
			i++;
		}
		
		this.setMaxStackSize(1);
		this.setCreativeTab(Main.eftab);
		this.setHasSubtypes(true);
		this.setUnlocalizedName("biome_stabilizer");
		//this.setTextureName(Main.MODID + ":" + unloc + "." + type.getName());
	}

	@Override
	public void registerIcons(IIconRegister reg) {
		this.icons = new IIcon[types.length];
		for (int i = 0; i < icons.length; i++) {
			this.icons[i] = reg.registerIcon(Main.MODID + ":biome_stabilizer." + types[i].getName());
		}
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icons[meta];
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
	    for (int i = 0; i < types.length; i ++) {
	        list.add(new ItemStack(item, 1, i));
	    }
	}
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean b) {
		list.add(StatCollector.translateToLocal("efgui.biome_stabilizer") + StatCollector.translateToLocal("efgui.biome_stabilizer." + types[itemStack.getItemDamage()].getName()));
		list.add(priceDescs[itemStack.getItemDamage()]);
	}
	
	@Override
	public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2) {
		return false;
	}
	
	@Override
	public BiomeGenBase getBiome(ItemStack itemStack) {
		return types[itemStack.getItemDamage()].getBiome();
	}
	
	@Override
	public AspectList getPrice(ItemStack itemStack) {
		return types[itemStack.getItemDamage()].getPrice();
	}
}