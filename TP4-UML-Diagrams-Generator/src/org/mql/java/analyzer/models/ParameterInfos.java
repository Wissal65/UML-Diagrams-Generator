package org.mql.java.analyzer.models;

import java.lang.reflect.Parameter;

public class ParameterInfos {
	private String name;
	private String type;

	public ParameterInfos(Parameter parameter) {
		this.name = parameter.getName();
		this.type = parameter.getType().getSimpleName();
	}

	public ParameterInfos(String name, String type) {
		this.name = name;
		this.type = type;
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

	@Override
	public String toString() {
		return "ParameterInfos [name=" + name + ", type=" + type + "]";
	}

}
