package com.mishin870.exforbidden.forestrycomp.biome_stabilizer;

import com.mishin870.exforbidden.api.items.IBiomeStabilizer;

import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public enum BiomeStabilizerType {
	PLAINS("plains", BiomeGenBase.plains, 0, 0, 0, 0, 0, 0), END("end", BiomeGenBase.sky, 0, 0, 0, 5, 0, 5),
	NETHER("nether", BiomeGenBase.hell, 0, 5, 5, 0, 0, 0);
	private BiomeGenBase biome;
	private AspectList price;
	private String name;
	
	BiomeStabilizerType(String name, BiomeGenBase biome, int aqua, int ignis, int terra, int air, int ordo, int perditio) {
		this.name = name;
		this.biome = biome;
		this.price = new AspectList();
		price.add(Aspect.WATER, aqua);
		price.add(Aspect.FIRE, ignis);
		price.add(Aspect.EARTH, terra);
		price.add(Aspect.AIR, air);
		price.add(Aspect.ORDER, ordo);
		price.add(Aspect.ENTROPY, perditio);
	}
	
	public BiomeGenBase getBiome() {
		return biome;
	}
	
	public AspectList getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
}
