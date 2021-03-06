package com.mishin870.exforbidden.props;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.mishin870.exforbidden.Main;
import com.mishin870.exforbidden.gui.PuzzleWidget;
import com.mishin870.exforbidden.utils.EFLogger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

/**
 * Внутренние настройки ExForbidden, интегрированные с ресурсами мода (текстуры)
 * @see <a href="/resources/assets/exforbidden/configs/">/resources/assets/exforbidden/configs/</a>
 */
public class EFTextures {
	private static final ResourceLocation texturesProp = new ResourceLocation(Main.MODID + ":configs/textures.properties");
	private static Properties textureNames = new Properties();
	public static Map<String, IIcon> textures = new HashMap<String, IIcon>();
	
	public static void init(IIconRegister register) {
		try {
			InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(texturesProp).getInputStream();
			textureNames.load(inputStream);
			Iterator<Entry<Object, Object>> iter = textureNames.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<Object, Object> entry = iter.next();
				textures.put((String) entry.getKey(), register.registerIcon(Main.MODID + ":imgs/" + entry.getValue()));
			}
			inputStream.close();
		} catch (Exception e) {
			EFLogger.warn("Unable to load configs/textures.properties. Its may cause problems...");
			e.printStackTrace();
		}
		PuzzleWidget.init();
	}
	
	public static IIcon getImage(String key) {
		return textures.get(key);
	}

}