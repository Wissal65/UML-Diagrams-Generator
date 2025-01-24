package org.mql.java.analyzer.models;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class MethodInfos {
	private String name;
	private String returnType;
	private List<ParameterInfos> parameters;
	private String modifiers;

	public MethodInfos(Method method) {
		this.name = method.getName();
		this.returnType = method.getReturnType().getSimpleName();
		this.parameters = new ArrayList<>();
		this.modifiers = Modifier.toString(method.getModifiers());

		for (Parameter parameter : method.getParameters()) {
			this.parameters.add(new ParameterInfos(parameter));
		}
	}

	public MethodInfos(String name, String returnType, String modifiers) {
		this.name = name;
		this.returnType = returnType;
		this.modifiers = modifiers;
		this.parameters = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public List<ParameterInfos> getParameters() {
		return parameters;
	}

	public void setParameters(List<ParameterInfos> parameters) {
		this.parameters = parameters;
	}

	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}

}
