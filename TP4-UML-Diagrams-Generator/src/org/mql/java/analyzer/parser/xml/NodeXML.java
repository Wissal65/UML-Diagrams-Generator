package org.mql.java.analyzer.parser.xml;

import org.mql.java.analyzer.models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NodeXML {

	public static Element createPackageElement(Document doc, PackageInfos pkg) {
		Element packageElement = doc.createElement("package");
		packageElement.setAttribute("name", pkg.getName().replace("src.", ""));
		return packageElement;
	}

	public static Element createClassElement(Document doc, ClassInfos clazz) {
		Element classElement = doc.createElement("class");
		classElement.setAttribute("name", clazz.getName());
		classElement.setAttribute("modifiers", clazz.getModifiers());

		for (String extendedClass : clazz.getExtendsClasses()) {
			Element extendsElement = doc.createElement("extends");
			extendsElement.setTextContent(extendedClass);
			classElement.appendChild(extendsElement);
		}

		for (String implementedInterface : clazz.getImplementsInterfaces()) {
			Element implementsElement = doc.createElement("implements");
			implementsElement.setTextContent(implementedInterface);
			classElement.appendChild(implementsElement);
		}

		return classElement;
	}

	public static void addFieldElement(Document doc, Element parentElement, FieldInfos field) {
		Element fieldElement = doc.createElement("field");
		fieldElement.setAttribute("name", field.getName());
		fieldElement.setAttribute("type", field.getType());
		fieldElement.setAttribute("modifiers", field.getModifiers());
		parentElement.appendChild(fieldElement);
	}

	public static void addMethodElement(Document doc, Element parentElement, MethodInfos method) {
		Element methodElement = doc.createElement("method");
		methodElement.setAttribute("name", method.getName());
		methodElement.setAttribute("returnType", method.getReturnType());
		methodElement.setAttribute("modifiers", method.getModifiers());

		for (ParameterInfos parameter : method.getParameters()) {
			Element paramElement = doc.createElement("parameter");
			paramElement.setAttribute("name", parameter.getName());
			paramElement.setAttribute("type", parameter.getType());
			methodElement.appendChild(paramElement);
		}

		parentElement.appendChild(methodElement);
	}

	public static void addRelationshipElement(Document doc, Element parentElement, RelationShips relationship) {
		Element relationshipElement = doc.createElement("relationship");
		relationshipElement.setAttribute("source", relationship.getSource());
		relationshipElement.setAttribute("type", relationship.getType());
		relationshipElement.setAttribute("target", relationship.getTarget());
		parentElement.appendChild(relationshipElement);
	}

	public static Element createInterfaceElement(Document doc, InterfaceInfos interfaceInfo) {
		Element interfaceElement = doc.createElement("interface");
		interfaceElement.setAttribute("name", interfaceInfo.getName());
		interfaceElement.setAttribute("modifiers",
				interfaceInfo.getModifiers().replace("abstract interface", "interface"));

		for (String extendedInterface : interfaceInfo.getExtendedInterfaces()) {
			Element extendsElement = doc.createElement("extends");
			extendsElement.setTextContent(extendedInterface);
			interfaceElement.appendChild(extendsElement);
		}

		return interfaceElement;
	}

	public static Element createEnumElement(Document doc, EnumInfos enumInfo) {
		Element enumElement = doc.createElement("enum");
		enumElement.setAttribute("name", enumInfo.getName());
		enumElement.setAttribute("modifiers", enumInfo.getModifiers());

		for (String constant : enumInfo.getConstants()) {
			Element constantElement = doc.createElement("constant");
			constantElement.setTextContent(constant);
			enumElement.appendChild(constantElement);
		}

		return enumElement;
	}

	public static Element createAnnotationElement(Document doc, AnnotationInfos annotationInfo) {
		Element annotationElement = doc.createElement("annotation");
		annotationElement.setAttribute("name", annotationInfo.getName());
		annotationElement.setAttribute("modifiers", annotationInfo.getModifiers());

		for (String metaAnnotation : annotationInfo.getMetaAnnotations()) {
			Element metaElement = doc.createElement("metaAnnotation");
			metaElement.setTextContent(metaAnnotation);
			annotationElement.appendChild(metaElement);
		}

		return annotationElement;
	}
}