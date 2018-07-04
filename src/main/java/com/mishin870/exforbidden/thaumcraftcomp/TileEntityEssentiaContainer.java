package com.mishin870.exforbidden.thaumcraftcomp;

import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.tileentity.TileEntity;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectSource;

/**
 * Логика хранителя эссенции
 */
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
	
	/**
	 * Добавить определённое количество аспекта в хранитель
	 * @param aspect тип аспекта
	 * @param n количество
	 * @return добавленное количество
	 */
	@Override
	public int addToContainer(Aspect aspect, int n) {
		list.add(aspect, n);
		return n;
	}
	
	@Override
	public int containerContains(Aspect aspect) {
		return list.getAmount(aspect);
	}
	
	@Override
	public boolean doesContainerAccept(Aspect aspect) {
		return aspect.isPrimal();
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
	
	/**
	 * Содержит ли хранитель достаточное количество указанного аспекта
	 * @param aspect тип аспекта
	 * @param n количество
	 */
	@Override
	public boolean doesContainerContainAmount(Aspect aspect, int n) {
		return list.getAmount(aspect) >= n;
	}
	
	/**
	 * Возвращает все аспекты, содержащиеся в хранителе
	 * и их количества
	 */
	@Override
	public AspectList getAspects() {
		return list;
	}
	
	/**
	 * Принудительно устанавливает хранителю аспекты
	 */
	@Override
	public void setAspects(AspectList list) {
		this.list = list;
	}
	
	/**
	 * Попытаться забрать определённое количество аспектов
	 * из хранителя.
	 *
	 * @param list список аспектов и их количества
	 * @return true, если хранитель содержит и было изъято нужное количество
	 */
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
	
	/**
	 * Попытаться забрать определённое количество одного аспекта
	 * из хранителя
	 *
	 * @param aspect аспект
	 * @param n количество
	 * @return true, если хранитель содержит и было изъято нужное количество
	 */
	@Override
	public boolean takeFromContainer(Aspect aspect, int n) {
		if (list.getAmount(aspect) >= n) {
			list.reduce(aspect, n);
			return true;
		} else {
			return false;
		}
	}
	
}