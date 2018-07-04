package com.mishin870.exforbidden.api;

/**
 * Интерфейс для блоков, которые хотят добавить внутрь себя виджет VisWidget
 * (они не обязаны реально хранить информацию о вис-энергии, могут имитировать это)
 *
 * @author Михаил
 */
public interface ICVisContainer {
	/**
	 * Вода
	 */
	public int getAqua();
	
	/**
	 * Огонь
	 */
	public int getIgnis();
	
	/**
	 * Земля
	 */
	public int getTerra();
	
	/**
	 * Воздух
	 */
	public int getAir();
	
	/**
	 * Порядок
	 */
	public int getOrdo();
	
	/**
	 * Хаос
	 */
	public int getPerditio();
	
	/**
	 * Максимальное значение аспектов в контейнере
	 */
	public int getMax();
}