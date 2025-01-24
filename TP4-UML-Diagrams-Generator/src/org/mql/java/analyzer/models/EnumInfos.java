package org.mql.java.analyzer.models;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class EnumInfos {

	private String name;
	private String modifiers;
	private List<String> constants;

	public EnumInfos() {
		this.constants = new ArrayList<>();
	}

	public EnumInfos(Class<?> clazz) {
		this.name = clazz.getSimpleName();
		this.modifiers = Modifier.toString(clazz.getModifiers());
		this.constants = new ArrayList<>();

		if (clazz.isEnum()) {
			for (Object constant : clazz.getEnumConstants()) {
				this.constants.add(constant.toString());
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}

	public List<String> getConstants() {
		return constants;
	}

	public void setConstants(List<String> constants) {
		this.constants = constants;
	}

}
