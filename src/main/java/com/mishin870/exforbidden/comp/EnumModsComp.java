package com.mishin870.exforbidden.comp;

/**
 * Здесь находятся все возможные моды, на которые опираются вещи из моего мода
 * @author Михаил
 *
 */
public enum EnumModsComp {
	THAUMCRAFT("Thaumcraft"), FORESTRY("Forestry");
	public static final String softDependencies = "after:Thaumcraft;after:Forestry";
	public String modName;
	
	EnumModsComp(String modName) {
		this.modName = modName;
	}
	
	/**
	 * Получить строку, содержащую текст для аннотации зависимостей этого мода
	 * @return
	 */
	public static final String getAllSoftDependencies() {
		StringBuilder sb = new StringBuilder();
		for (EnumModsComp mod : EnumModsComp.values()) {
			sb.append("after:").append(mod.modName).append(";");
		}
		return sb.toString();
	}
	
}