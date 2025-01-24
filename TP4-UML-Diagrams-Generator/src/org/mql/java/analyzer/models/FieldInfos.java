package org.mql.java.analyzer.models;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldInfos {

	private String name;
	private String type;
	private String modifiers;

	public FieldInfos(Field field) {
		this.name = field.getName();
		this.type = field.getType().getSimpleName();
		this.modifiers = Modifier.toString(field.getModifiers());
	}

	public FieldInfos(String name, String type, String modifiers) {
		this.name = name;
		this.type = type;
		this.modifiers = modifiers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}

	@Override
	public String toString() {
		return "FieldInfo [name=" + name + ", type=" + type + ", modifiers=" + modifiers + "]";
	}

}
