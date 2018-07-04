package com.mishin870.exforbidden.forestrycomp;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeModifier;

/**
 * База реализованных рамок
 *
 * @see EFFrameType
 */
public enum EFFrameType implements IBeeModifier {
	TEST("test", 10, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, false, false, false, false);
	
	private final String frameName;
	public final int maxDamage;
	private final float territoryMod;
	private final float mutationMod;
	private final float lifespanMod;
	private final float productionMod;
	private final float floweringMod;
	private final float geneticDecayMod;
	private final boolean isSealed;
	private final boolean isLit;
	private final boolean isSunlit;
	private final boolean isHellish;
	
	private EFFrameType(String name, int damage, float territory, float mutation, float lifespan, float production, float geneticDecay) {
		this(name, damage, territory, mutation, lifespan, production, 1.0F, geneticDecay, false, false, false, false);
	}

	private EFFrameType(String name, int damage, float territory, float mutation, float lifespan, float production, float flowering, float geneticDecay, boolean sealed, boolean lit, boolean sunlit, boolean hellish) {
		this.frameName = name;
		this.maxDamage = damage;
		this.territoryMod = territory;
		this.mutationMod = mutation;
		this.lifespanMod = lifespan;
		this.productionMod = production;
		this.floweringMod = flowering;
		this.geneticDecayMod = geneticDecay;
		this.isSealed = sealed;
		this.isLit = lit;
		this.isSunlit = sunlit;
		this.isHellish = hellish;
	}
	
	public String getName() {
		return this.frameName;
	}
	
	@Override
	public float getTerritoryModifier(IBeeGenome genome, float currentModifier) {
		return this.territoryMod;
	}
	
	@Override
	public float getMutationModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return this.mutationMod;
	}
	
	@Override
	public float getLifespanModifier(IBeeGenome genome, IBeeGenome mate, float currentModifier) {
		return this.lifespanMod;
	}
	
	@Override
	public float getProductionModifier(IBeeGenome genome, float currentModifier) {
		return this.productionMod;
	}
	
	@Override
	public float getFloweringModifier(IBeeGenome genome, float currentModifier) {
		return this.floweringMod;
	}
	
	@Override
	public float getGeneticDecay(IBeeGenome genome, float currentModifier) {
		return this.geneticDecayMod;
	}
	
	@Override
	public boolean isSealed() {
		return this.isSealed;
	}
	
	@Override
	public boolean isSelfLighted() {
		return this.isLit;
	}
	
	@Override
	public boolean isSunlightSimulated() {
		return this.isSunlit;
	}
	
	@Override
	public boolean isHellish() {
		return this.isHellish;
	}
}