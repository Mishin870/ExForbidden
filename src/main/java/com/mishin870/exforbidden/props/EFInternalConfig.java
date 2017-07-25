package com.mishin870.exforbidden.props;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.mishin870.exforbidden.EFLogger;
import com.mishin870.exforbidden.Main;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class EFInternalConfig {
	private static final ResourceLocation internalProp = new ResourceLocation(Main.MODID + ":configs/internal.properties");
	public static Map<String, String> props = new HashMap<String, String>();
	
	public static void init() {
		try {
			InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(internalProp).getInputStream();
			Properties properties = new Properties();
			properties.load(inputStream);
			Iterator<Entry<Object, Object>> iter = properties.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<Object, Object> entry = iter.next();
				props.put((String) entry.getKey(), (String) entry.getValue());
			}
			inputStream.close();
		} catch (Exception e) {
			EFLogger.warn("Unable to load configs/internal.properties. Its may cause big problems...");
			e.printStackTrace();
		}
	}
	
	public static String getVal(String key) {
		return props.get(key);
	}

}