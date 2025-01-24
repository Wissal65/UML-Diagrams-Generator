package org.mql.java.analyzer.models;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class AnnotationInfos {
	private String name;
	private String modifiers;
	private List<MethodInfos> elements;
	private List<String> metaAnnotations;

	public AnnotationInfos() {
		this.metaAnnotations = new ArrayList<>();
	}

	public AnnotationInfos(Class<?> clazz) {
		this.name = clazz.getSimpleName();
		this.modifiers = Modifier.toString(clazz.getModifiers());
		this.elements = new ArrayList<>();
		this.metaAnnotations = new ArrayList<>();

		for (Method method : clazz.getDeclaredMethods()) {
			this.elements.add(new MethodInfos(method));
		}

		for (Annotation annotation : clazz.getDeclaredAnnotations()) {
			this.metaAnnotations.add(annotation.annotationType().getSimpleName());
		}

	}

	public AnnotationInfos(String name, String modifiers) {
		this.name = name;
		this.modifiers = modifiers;
		this.metaAnnotations = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}

	public List<MethodInfos> getElements() {
		return elements;
	}

	public void setElements(List<MethodInfos> elements) {
		this.elements = elements;
	}

	public List<String> getMetaAnnotations() {
		return metaAnnotations;
	}

	public void setMetaAnnotations(List<String> metaAnnotations) {
		this.metaAnnotations = metaAnnotations;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AnnotationInfos [name=" + name + ", modifiers=" + modifiers + ", elements=" + elements
				+ ", metaAnnotations=" + metaAnnotations + "]";
	}

}