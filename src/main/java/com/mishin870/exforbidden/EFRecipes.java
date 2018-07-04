package com.mishin870.exforbidden;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

/**
 * Рецепты крафта ExForbidden
 */
public class EFRecipes {
	
	public static void init() {
		AspectList al = new AspectList();
		al.add(Aspect.AIR, 80);
		al.add(Aspect.EARTH, 80);
		al.add(Aspect.FIRE, 80);
		al.add(Aspect.WATER, 80);
		al.add(Aspect.ENTROPY, 80);
		al.add(Aspect.ORDER, 80);
		ThaumcraftApi.addArcaneCraftingRecipe("", new ItemStack(EFItems.frameTest), al, new Object[] {"SSS", "SFS", "SSS", 'S', Items.gold_ingot, 'F', Items.stick});
	}
	
}