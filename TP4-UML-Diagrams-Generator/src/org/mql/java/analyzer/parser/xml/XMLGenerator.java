package org.mql.java.analyzer.parser.xml;

import org.mql.java.analyzer.models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLGenerator {

	public static void generateXML(ProjectInfos project, String outputFilePath) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element projectElement = doc.createElement("project");
			projectElement.setAttribute("name", project.getName());
			projectElement.setAttribute("path", project.getPath());
			doc.appendChild(projectElement);

			for (PackageInfos pkg : project.getPackages()) {
				Element packageElement = NodeXML.createPackageElement(doc, pkg);
				projectElement.appendChild(packageElement);

				for (ClassInfos clazz : pkg.getClasses()) {
					Element classElement = NodeXML.createClassElement(doc, clazz);
					packageElement.appendChild(classElement);

					for (FieldInfos field : clazz.getFields()) {
						NodeXML.addFieldElement(doc, classElement, field);
					}

					for (MethodInfos method : clazz.getMethods()) {
						NodeXML.addMethodElement(doc, classElement, method);
					}

					for (RelationShips relationship : clazz.getRelationships()) {
						NodeXML.addRelationshipElement(doc, classElement, relationship);
					}
				}

				for (InterfaceInfos interfaceInfo : pkg.getInterfaces()) {
					Element interfaceElement = NodeXML.createInterfaceElement(doc, interfaceInfo);
					packageElement.appendChild(interfaceElement);
				}

				for (EnumInfos enumInfo : pkg.getEnums()) {
					Element enumElement = NodeXML.createEnumElement(doc, enumInfo);
					packageElement.appendChild(enumElement);
				}

				for (AnnotationInfos annotationInfo : pkg.getAnnotations()) {
					Element annotationElement = NodeXML.createAnnotationElement(doc, annotationInfo);
					packageElement.appendChild(annotationElement);
				}
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(outputFilePath));
			transformer.transform(source, result);

			System.out.println("Fichier XML généré avec succès : " + outputFilePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}