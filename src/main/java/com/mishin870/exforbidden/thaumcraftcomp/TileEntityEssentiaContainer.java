package com.mishin870.exforbidden.thaumcraftcomp;

import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;

public class TileEntityEssentiaContainer extends TileEntity implements IAspectSource {
	private AspectList list;
	
	public TileEntityEssentiaContainer() {
		list = new AspectList();
		list.add(Aspect.WATER, 1000);
		list.add(Aspect.FIRE, 1000);
		list.add(Aspect.EARTH, 1000);
		list.add(Aspect.AIR, 1000);
		list.add(Aspect.ORDER, 1000);
		list.add(Aspect.ENTROPY, 1000);
	}
	
	@Override
	public int addToContainer(Aspect a, int n) {
		list.add(a, n);
		return n;
	}

	@Override
	public int containerContains(Aspect a) {
		return list.getAmount(a);
	}

	@Override
	public boolean doesContainerAccept(Aspect a) {
		return a.isPrimal();
	}

	@Override
	public boolean doesContainerContain(AspectList list) {
		Iterator<Entry<Aspect, Integer>> iter = list.aspects.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Aspect, Integer> e = iter.next();
			if (this.list.getAmount(e.getKey()) < e.getValue().intValue()) return false;
		}
		return true;
	}

	@Override
	public boolean doesContainerContainAmount(Aspect a, int n) {
		return list.getAmount(a) >= n;
	}

	@Override
	public AspectList getAspects() {
		return list;
	}

	@Override
	public void setAspects(AspectList list) {
		this.list = list;
	}

	@Override
	public boolean takeFromContainer(AspectList list) {
		Iterator<Entry<Aspect, Integer>> iter = list.aspects.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<Aspect, Integer> e = iter.next();
			if (this.list.getAmount(e.getKey()) < e.getValue().intValue()) return false;
			this.list.reduce(e.getKey(), e.getValue().intValue());
		}
		return true;
	}

	@Override
	public boolean takeFromContainer(Aspect a, int n) {
		if (list.getAmount(a) >= n) {
			list.reduce(a, n);
			return true;
		} else {
			return false;
		}
	}
	
}