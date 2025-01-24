package org.mql.java.analyzer.models;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class InterfaceInfos {

	private String name;
	private String modifiers;
	private List<String> extendedInterfaces;
	private List<MethodInfos> methods;
	private List<FieldInfos> constants;

	public InterfaceInfos() {
		this.extendedInterfaces = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.constants = new ArrayList<>();
	}

	public InterfaceInfos(Class<?> clazz) {
		this.name = clazz.getSimpleName();
		this.modifiers = Modifier.toString(clazz.getModifiers()).replace("abstract interface", "interface");
		this.extendedInterfaces = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.constants = new ArrayList<>();

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			this.extendedInterfaces.add(interfaceClass.getSimpleName());
		}

		for (Method method : clazz.getDeclaredMethods()) {
			this.methods.add(new MethodInfos(method));
		}

		for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
			this.constants.add(new FieldInfos(field));
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

	public List<String> getExtendedInterfaces() {
		return extendedInterfaces;
	}

	public void setExtendedInterfaces(List<String> extendedInterfaces) {
		this.extendedInterfaces = extendedInterfaces;
	}

	public List<MethodInfos> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodInfos> methods) {
		this.methods = methods;
	}

	public List<FieldInfos> getConstants() {
		return constants;
	}

	public void setConstants(List<FieldInfos> constants) {
		this.constants = constants;
	}

	@Override
	public String toString() {
		return "InterfaceInfos [name=" + name + ", modifiers=" + modifiers + ", extendedInterfaces="
				+ extendedInterfaces + ", methods=" + methods + ", constants=" + constants + "]";
	}

}
