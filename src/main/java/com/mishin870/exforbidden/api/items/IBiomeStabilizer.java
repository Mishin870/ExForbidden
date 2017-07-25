package com.mishin870.exforbidden.api.items;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.api.aspects.AspectList;

/**
 * Интерфейс, который необходимо реализовать в предмете, чтобы он мог
 * работать в межпространственной пасеке в качестве биомного стабилизатора
 * 
 * @author Mishin870
 */
public interface IBiomeStabilizer {
	//Биом, который устанавливает данный стабилизатор (itemStack - ссылка на стек стабилизатора)
	public BiomeGenBase getBiome(ItemStack itemStack);
	//Цена работы межпространственной пасеки с таким стабилизатором (в сантивис)
	public AspectList getPrice(ItemStack itemStack);
}