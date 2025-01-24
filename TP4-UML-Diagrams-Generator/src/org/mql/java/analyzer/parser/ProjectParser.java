package org.mql.java.analyzer.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mql.java.analyzer.models.ClassInfos;
import org.mql.java.analyzer.models.EnumInfos;
import org.mql.java.analyzer.models.InterfaceInfos;
import org.mql.java.analyzer.models.AnnotationInfos;
import org.mql.java.analyzer.models.PackageInfos;
import org.mql.java.analyzer.models.ProjectInfos;

public class ProjectParser {

	public ProjectInfos parseProject(String projectPath) {
		String projectName = extractProjectName(projectPath);
		ProjectInfos project = new ProjectInfos(projectName, projectPath);

		List<PackageInfos> packages = extractPackages(projectPath);
		project.setPackages(packages);

		return project;
	}

	private String extractProjectName(String projectPath) {
		File projectDir = new File(projectPath);
		return projectDir.getName();
	}

	private List<PackageInfos> extractPackages(String projectPath) {
		List<PackageInfos> packages = new ArrayList<>();
		File projectDir = new File(projectPath);

		if (projectDir.isDirectory()) {
			exploreDirectory(projectDir, packages, "");
		} else {
			System.err.println("Le chemin spécifié n'est pas un répertoire : " + projectPath);
		}

		return packages;
	}

	private void exploreDirectory(File directory, List<PackageInfos> packages, String parentPackage) {
		for (File file : directory.listFiles()) {
			if (file.isDirectory()) {
				if (!file.getName().startsWith(".") && !file.getName().equals("bin")) {
					String packageName = parentPackage.isEmpty() ? file.getName()
							: parentPackage + "." + file.getName();

					if (containsJavaFiles(file)) {
						PackageInfos pkg = new PackageInfos();
						pkg.setName(packageName);

						extractClasses(file, pkg);
						packages.add(pkg);
					}

					exploreDirectory(file, packages, packageName);
				}
			}
		}
	}

	private boolean containsJavaFiles(File directory) {
		for (File file : directory.listFiles()) {
			if (file.getName().endsWith(".java")) {
				return true;
			}
		}
		return false;
	}

	private void extractClasses(File packageDir, PackageInfos packageInfos) {
		for (File file : packageDir.listFiles()) {
			if (file.getName().endsWith(".java")) {
				String className = file.getName().replace(".java", "");
				String fullClassName = packageInfos.getName() + "." + className;

				try {

					if (fullClassName.startsWith("src.")) {
						fullClassName = fullClassName.substring(4);
					}

					Class<?> clazz = Class.forName(fullClassName);

					if (clazz.isAnnotation()) {
						packageInfos.getAnnotations().add(new AnnotationInfos(clazz));
					} else if (clazz.isInterface()) {
						packageInfos.getInterfaces().add(new InterfaceInfos(clazz));
					} else if (clazz.isEnum()) {
						packageInfos.getEnums().add(new EnumInfos(clazz));
					} else {
						packageInfos.getClasses().add(new ClassInfos(clazz));
					}
				} catch (ClassNotFoundException e) {
					System.err.println("Classe non trouvée : " + fullClassName);
				}
			}
		}
	}
}