package org.mql.java.analyzer.models;

import java.util.ArrayList;
import java.util.List;

public class PackageInfos {

	private String name;
	private List<ClassInfos> classes;
	private List<InterfaceInfos> interfaces;
	private List<EnumInfos> enums;
	private List<AnnotationInfos> annotations;

	public PackageInfos() {
		this.classes = new ArrayList<>();
		this.interfaces = new ArrayList<>();
		this.enums = new ArrayList<>();
		this.annotations = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ClassInfos> getClasses() {
		return classes;
	}

	public void setClasses(List<ClassInfos> classes) {
		this.classes = classes;
	}

	public List<InterfaceInfos> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<InterfaceInfos> interfaces) {
		this.interfaces = interfaces;
	}

	public List<EnumInfos> getEnums() {
		return enums;
	}

	public void setEnums(List<EnumInfos> enums) {
		this.enums = enums;
	}

	public List<AnnotationInfos> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(List<AnnotationInfos> annotations) {
		this.annotations = annotations;
	}

}
