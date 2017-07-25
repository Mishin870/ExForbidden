package com.mishin870.exforbidden.props;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class EFConfig extends Configuration {
	//private static final String CDEF = "default";
	private static String dir;
	private static Configuration config;
	//=====default==========
	//public static int SOME_VAR = 1;
	
	public EFConfig() {
		File file = new File("config/efcfg.cfg");
		System.out.println(file.getAbsolutePath());
		config = new Configuration(file);
		
		//default();
		
		config.load();
		config.save();
	}
	
	/*private static void biomes() {
		BIOMES_MANA = config.get(CBIOMES, "mana", 25).getInt();
		DEFAULT = config.get(CDEF, "default", 1).getInt(); //1 - значение по умолчанию
	}*/
	
	public static Configuration getConfig() {
		return config;
	}
	
}