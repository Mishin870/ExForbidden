package com.mishin870.exforbidden.forestrycomp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mishin870.exforbidden.Main;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestry.api.core.ForestryAPI;
import forestry.api.core.IErrorState;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class EFForestryErrors {
	public static EFForestryError NO_VIS, NO_STABILIZER;
	private static Map<Short, IErrorState> states;
	private static List<IErrorState> customStates;
	
	public static void init() {
		customStates = new ArrayList<IErrorState>();
		states = new HashMap<Short, IErrorState>();
		Set<IErrorState> fstates = ForestryAPI.errorStateRegistry.getErrorStates();
		for (IErrorState state : fstates) states.put(state.getID(), state);
		NO_VIS = addError("noVis");
		NO_STABILIZER = addError("noStabilizer");
	}
	
	public static IErrorState getErrorState(short id) {
		return states.get(Short.valueOf(id));
	}
	
	public static EFForestryError addError(String key) {
		EFForestryError err = new EFForestryError(key);
		states.put(err.getID(), err);
		customStates.add(err);
		return err;
	}
	
	public static void registerIcons(IIconRegister register) {
		for (IErrorState err : customStates) err.registerIcons(register);
	}
	
	static class EFForestryError implements IErrorState {
		private static short CURRENT_ID = 5482;
		private String key;
		private short id;
		@SideOnly(Side.CLIENT)
		private IIcon icon;
		
		public EFForestryError(String key) {
			this.id = CURRENT_ID;
			CURRENT_ID++;
			this.key = key;
		}
		
		@Override
		public String getDescription() {
			return "errors." + key + ".desc";
		}
		
		@Override
		public String getHelp() {
			return "errors." + key + ".help";
		}
		
		@Override
		public short getID() {
			return id;
		}
		
		@Override
		public IIcon getIcon() {
			return this.icon;
		}
		
		@Override
		public String getUniqueName() {
			return Main.MODNAME + ":" + this.key;
		}
		
		@Override
		public void registerIcons(IIconRegister register) {
			this.icon = register.registerIcon(Main.MODID + ":imgs/errors/" + this.key);
		}
		
	}
	
}