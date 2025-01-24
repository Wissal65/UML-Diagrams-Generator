package org.mql.java.analyzer.models;

import java.util.ArrayList;
import java.util.List;

public class ProjectInfos {

	private String name;
	private String path;
	private List<PackageInfos> packages;

	public ProjectInfos() {
		this.packages = new ArrayList<>();
	}

	public ProjectInfos(String name, String path) {
		super();
		this.name = name;
		this.path = path;
		this.packages = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<PackageInfos> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageInfos> packages) {
		this.packages = packages;
	}

}
