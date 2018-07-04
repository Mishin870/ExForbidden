package com.mishin870.exforbidden.comp;

/**
 * «десь наход€тс€ все возможные моды, на которые опираютс€ вещи из моего мода
 * @author ћихаил
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
	 * ѕолучить строку, содержащую текст дл€ аннотации зависимостей этого мода
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