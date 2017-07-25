package com.mishin870.exforbidden.forestrycomp.alveary_lighter;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.forestrycomp.TileEntityAlvearyBlock;

import forestry.api.apiculture.IBeeGenome;
import forestry.api.apiculture.IBeeListener;
import forestry.api.apiculture.IBeeModifier;
import forestry.api.genetics.IIndividual;
import forestry.api.multiblock.IAlvearyComponent.BeeListener;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.visnet.VisNetHandler;

public class TileEntityAlvearyLighter extends TileEntityAlvearyBlock implements IAspectContainer {
	private static final int TICK_RATE_DRAIN = 5;
	private static final int TICK_RATE_CONSUME = 10;
	private static final int VIS_CONSUME = 5;
	private static final int VIS_MAX = 50;
	public boolean isWork;
	private int terra, ignis;
	private AspectList list;
	//private IBeeListener beeListener;
	private IBeeModifier beeMod;
	
	public TileEntityAlvearyLighter() {
		list = new AspectList();
		list.add(Aspect.EARTH, 0);
		list.add(Aspect.FIRE, 0);
		terra = 0;
		ignis = 0;
		isWork = false;
		/*beeListener = new IBeeListener() {
			@Override
			public void wearOutEquipment(int paramInt) {
				Main.sendToMe("BeeListener::wearOutEquipment()");
			}
			@Override
			public void onQueenDeath() {
				Main.sendToMe("BeeListener::onQueenDeath()");
			}
			@Override
			public boolean onPollenRetrieved(IIndividual paramIIndividual) {
				Main.sendToMe("BeeListener::onPollenRetrieved()");
				return false;
			}
		};*/
		beeMod = new IBeeModifier() {
			@Override
			public boolean isSunlightSimulated() {
				// TODO Auto-generated method stub
				return false;
			}
			@Override
			public boolean isSelfLighted() {
				return isWork;
			}
			@Override
			public boolean isSealed() {
				return false;
			}
			@Override
			public boolean isHellish() {
				return false;
			}
			@Override
			public float getTerritoryModifier(IBeeGenome paramIBeeGenome, float paramFloat) {
				return 1.0f;
			}
			@Override
			public float getProductionModifier(IBeeGenome paramIBeeGenome, float paramFloat) {
				return 1.0f;
			}
			@Override
			public float getMutationModifier(IBeeGenome paramIBeeGenome1, IBeeGenome paramIBeeGenome2, float paramFloat) {
				return 1.0f;
			}
			@Override
			public float getLifespanModifier(IBeeGenome paramIBeeGenome1, IBeeGenome paramIBeeGenome2, float paramFloat) {
				return 1.0f;
			}
			@Override
			public float getGeneticDecay(IBeeGenome paramIBeeGenome, float paramFloat) {
				return 1.0f;
			}
			@Override
			public float getFloweringModifier(IBeeGenome paramIBeeGenome, float paramFloat) {
				return 1.0f;
			}
		};
	}
	
	@Override
	public void updateEntity() {
		long tick = this.worldObj.getTotalWorldTime();
		if (tick % TICK_RATE_DRAIN == 0) {
			ignis += VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Aspect.FIRE, 5);
			terra += VisNetHandler.drainVis(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Aspect.EARTH, 5);
			if (ignis > VIS_MAX) ignis = VIS_MAX;
			if (terra > VIS_MAX) terra = VIS_MAX;
			list.remove(Aspect.EARTH);
			list.remove(Aspect.FIRE);
			list.add(Aspect.EARTH, terra);
			list.add(Aspect.FIRE, ignis);
		}
		if (tick % TICK_RATE_CONSUME == 0) {
			isWork = ignis >= VIS_CONSUME && terra >= VIS_CONSUME;
			ignis -= VIS_CONSUME;
			terra -= VIS_CONSUME;
			if (ignis < 0) ignis = 0;
			if (terra < 0) terra = 0;
			list.remove(Aspect.EARTH);
			list.remove(Aspect.FIRE);
			list.add(Aspect.EARTH, terra);
			list.add(Aspect.FIRE, ignis);
		}
	}

	/*@Override
	public IBeeListener getBeeListener() {
		return beeListener;
	}*/
	
	@Override
	public IBeeModifier getBeeModifier() {
		return beeMod;
	}

	@Override
	public int addToContainer(Aspect arg0, int arg1) {
		return 0;
	}

	@Override
	public int containerContains(Aspect a) {
		if (a == Aspect.EARTH) {
			return terra;
		} else if (a == Aspect.FIRE) {
			return ignis;
		} else {
			return 0;
		}
	}

	@Override
	public boolean doesContainerAccept(Aspect arg0) {
		return false;
	}

	@Override
	public boolean doesContainerContain(AspectList list) {
		return false;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect aspect, int num) {
		if (aspect == Aspect.EARTH) {
			return terra >= num;
		} else if (aspect == Aspect.FIRE) {
			return ignis >= num;
		} else {
			return false;
		}
	}

	@Override
	public AspectList getAspects() {
		/*AspectList list = new AspectList();
		list.add(Aspect.EARTH, terra);
		list.add(Aspect.FIRE, ignis);
		return list;*/
		return list;
	}
	
	@Override
	public void setAspects(AspectList arg0) {
		
	}
	
	@Override
	public boolean takeFromContainer(AspectList arg0) {
		return false;
	}
	
	@Override
	public boolean takeFromContainer(Aspect arg0, int arg1) {
		return false;
	}
	
}