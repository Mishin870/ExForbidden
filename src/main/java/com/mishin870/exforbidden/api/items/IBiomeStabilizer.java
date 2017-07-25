package com.mishin870.exforbidden.api.items;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import thaumcraft.api.aspects.AspectList;

/**
 * ���������, ������� ���������� ����������� � ��������, ����� �� ���
 * �������� � ������������������� ������ � �������� �������� �������������
 * 
 * @author Mishin870
 */
public interface IBiomeStabilizer {
	//����, ������� ������������� ������ ������������ (itemStack - ������ �� ���� �������������)
	public BiomeGenBase getBiome(ItemStack itemStack);
	//���� ������ ������������������� ������ � ����� �������������� (� ��������)
	public AspectList getPrice(ItemStack itemStack);
}