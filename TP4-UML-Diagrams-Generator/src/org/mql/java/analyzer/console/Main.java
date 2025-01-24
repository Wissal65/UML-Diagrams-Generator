package org.mql.java.analyzer.console;

import org.mql.java.analyzer.models.*;
import org.mql.java.analyzer.parser.ProjectParser;
import org.mql.java.analyzer.parser.xml.XMIGenerator;
import org.mql.java.analyzer.parser.xml.XMLGenerator;
import org.mql.java.analyzer.parser.xml.XMLParser;

public class Main {

	private ProjectPrinter projectPrinter;

	public Main() {
		projectPrinter = new ProjectPrinter();
		test4();
	}

	void test1() {
		String projectPath = "C:\\Users\\user\\git\\repository\\TP4-UML-Diagrams-Generator";
		ProjectParser parser = new ProjectParser();
		ProjectInfos project = parser.parseProject(projectPath);

		System.out.println("=== Structure du Projet ===");
		System.out.println("Nom du projet : " + project.getName());
		System.out.println("Chemin du projet : " + project.getPath());
		System.out.println("Nombre de packages : " + project.getPackages().size());
		System.out.println("===========================");

		for (PackageInfos pkg : project.getPackages()) {
			projectPrinter.printPackageTree(pkg, "");
		}
	}

	void test2() {
		String projectPath = "C:\\Users\\user\\git\\repository\\TP4-UML-Diagrams-Generator";
		ProjectParser parser = new ProjectParser();
		ProjectInfos project = parser.parseProject(projectPath);

		XMLGenerator.generateXML(project, "ressources\\project.xml");

	}

	void test3() {
		String projectPath = "C:\\Users\\user\\git\\repository\\TP4-UML-Diagrams-Generator";
		ProjectParser parser = new ProjectParser();
		ProjectInfos project = parser.parseProject(projectPath);

		XMIGenerator.generateXMI(project, "ressources\\project.xmi");

	}

	void test4() {
		String xmlFilePath = "ressources\\project.xml";
		ProjectInfos project = XMLParser.parseXML(xmlFilePath);

		if (project != null) {

			for (PackageInfos pkg : project.getPackages()) {
				projectPrinter.printPackageTree(pkg, "");
			}
		} else {
			System.out.println("Erreur lors du chargement du fichier XML.");
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}