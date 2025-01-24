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

public class XMIGenerator {

	public static void generateXMI(ProjectInfos project, String outputFilePath) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element xmiRoot = doc.createElement("xmi:XMI");
			xmiRoot.setAttribute("xmlns:xmi", "http://www.omg.org/XMI");
			xmiRoot.setAttribute("xmlns:uml", "http://www.omg.org/spec/UML/20090901");
			doc.appendChild(xmiRoot);

			Element modelElement = doc.createElement("uml:Model");
			modelElement.setAttribute("name", project.getName());
			modelElement.setAttribute("xmi:type", "uml:Model");
			xmiRoot.appendChild(modelElement);

			for (PackageInfos pkg : project.getPackages()) {
				Element packageElement = createPackageElement(doc, pkg);
				modelElement.appendChild(packageElement);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(outputFilePath));
			transformer.transform(source, result);

			System.out.println("Fichier XMI généré avec succès : " + outputFilePath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Element createPackageElement(Document doc, PackageInfos pkg) {
		Element packageElement = doc.createElement("packagedElement");
		packageElement.setAttribute("name", pkg.getName());
		packageElement.setAttribute("xmi:type", "uml:Package");

		for (ClassInfos clazz : pkg.getClasses()) {
			Element classElement = createClassElement(doc, clazz);
			packageElement.appendChild(classElement);
		}

		for (EnumInfos enumInfo : pkg.getEnums()) {
			Element enumElement = createEnumElement(doc, enumInfo);
			packageElement.appendChild(enumElement);
		}

		for (AnnotationInfos annotationInfo : pkg.getAnnotations()) {
			Element annotationElement = createAnnotationElement(doc, annotationInfo);
			packageElement.appendChild(annotationElement);
		}

		return packageElement;
	}

	private static Element createClassElement(Document doc, ClassInfos clazz) {
		Element classElement = doc.createElement("packagedElement");
		classElement.setAttribute("name", clazz.getName());
		classElement.setAttribute("xmi:type", "uml:Class");

		for (FieldInfos field : clazz.getFields()) {
			Element fieldElement = createFieldElement(doc, field);
			classElement.appendChild(fieldElement);
		}

		for (MethodInfos method : clazz.getMethods()) {
			Element methodElement = createMethodElement(doc, method);
			classElement.appendChild(methodElement);
		}

		for (RelationShips relationship : clazz.getRelationships()) {
			Element relationElement = createRelationElement(doc, relationship);
			classElement.appendChild(relationElement);
		}

		return classElement;
	}

	private static Element createFieldElement(Document doc, FieldInfos field) {
		Element fieldElement = doc.createElement("ownedAttribute");
		fieldElement.setAttribute("name", field.getName());
		fieldElement.setAttribute("type", field.getType());
		return fieldElement;
	}

	private static Element createMethodElement(Document doc, MethodInfos method) {
		Element methodElement = doc.createElement("ownedOperation");
		methodElement.setAttribute("name", method.getName());
		methodElement.setAttribute("returnType", method.getReturnType());

		for (ParameterInfos parameter : method.getParameters()) {
			Element paramElement = createParameterElement(doc, parameter);
			methodElement.appendChild(paramElement);
		}

		return methodElement;
	}

	private static Element createParameterElement(Document doc, ParameterInfos parameter) {
		Element paramElement = doc.createElement("ownedParameter");
		paramElement.setAttribute("name", parameter.getName());
		paramElement.setAttribute("type", parameter.getType());
		return paramElement;
	}

	private static Element createEnumElement(Document doc, EnumInfos enumInfo) {
		Element enumElement = doc.createElement("packagedElement");
		enumElement.setAttribute("name", enumInfo.getName());
		enumElement.setAttribute("xmi:type", "uml:Enumeration");

		for (String constant : enumInfo.getConstants()) {
			Element constantElement = doc.createElement("ownedLiteral");
			constantElement.setAttribute("name", constant);
			enumElement.appendChild(constantElement);
		}

		return enumElement;
	}

	private static Element createAnnotationElement(Document doc, AnnotationInfos annotationInfo) {
		Element annotationElement = doc.createElement("packagedElement");
		annotationElement.setAttribute("name", annotationInfo.getName());
		annotationElement.setAttribute("xmi:type", "uml:Class");

		for (String metaAnnotation : annotationInfo.getMetaAnnotations()) {
			Element metaElement = doc.createElement("ownedAttribute");
			metaElement.setAttribute("name", metaAnnotation);
			annotationElement.appendChild(metaElement);
		}

		return annotationElement;
	}

	private static Element createRelationElement(Document doc, RelationShips relationship) {
		Element relationElement = doc.createElement("association");
		relationElement.setAttribute("source", relationship.getSource());
		relationElement.setAttribute("target", relationship.getTarget());
		relationElement.setAttribute("type", relationship.getType());
		return relationElement;
	}
}