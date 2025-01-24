package org.mql.java.analyzer.parser.xml;

import org.mql.java.analyzer.models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLParser {

	public static ProjectInfos parseXML(String filePath) {
		try {
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			Element projectElement = doc.getDocumentElement();
			String projectName = projectElement.getAttribute("name");
			String projectPath = projectElement.getAttribute("path");

			ProjectInfos project = new ProjectInfos(projectName, projectPath);

			NodeList packageNodes = projectElement.getElementsByTagName("package");
			for (int i = 0; i < packageNodes.getLength(); i++) {
				Element packageElement = (Element) packageNodes.item(i);
				PackageInfos pkg = parsePackageElement(packageElement);
				project.getPackages().add(pkg);
			}

			return project;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static PackageInfos parsePackageElement(Element packageElement) {
		String packageName = packageElement.getAttribute("name");
		PackageInfos pkg = new PackageInfos();
		pkg.setName(packageName);

		NodeList classNodes = packageElement.getElementsByTagName("class");
		for (int i = 0; i < classNodes.getLength(); i++) {
			Element classElement = (Element) classNodes.item(i);
			ClassInfos clazz = parseClassElement(classElement);
			pkg.getClasses().add(clazz);
		}

		NodeList interfaceNodes = packageElement.getElementsByTagName("interface");
		for (int i = 0; i < interfaceNodes.getLength(); i++) {
			Element interfaceElement = (Element) interfaceNodes.item(i);
			InterfaceInfos interfaceInfo = parseInterfaceElement(interfaceElement);
			pkg.getInterfaces().add(interfaceInfo);
		}

		NodeList enumNodes = packageElement.getElementsByTagName("enum");
		for (int i = 0; i < enumNodes.getLength(); i++) {
			Element enumElement = (Element) enumNodes.item(i);
			EnumInfos enumInfo = parseEnumElement(enumElement);
			pkg.getEnums().add(enumInfo);
		}

		NodeList annotationNodes = packageElement.getElementsByTagName("annotation");
		for (int i = 0; i < annotationNodes.getLength(); i++) {
			Element annotationElement = (Element) annotationNodes.item(i);
			AnnotationInfos annotationInfo = parseAnnotationElement(annotationElement);
			pkg.getAnnotations().add(annotationInfo);
		}

		return pkg;
	}

	private static ClassInfos parseClassElement(Element classElement) {
		String className = classElement.getAttribute("name");
		String modifiers = classElement.getAttribute("modifiers");
		ClassInfos clazz = new ClassInfos();
		clazz.setName(className);
		clazz.setModifiers(modifiers);

		NodeList extendsNodes = classElement.getElementsByTagName("extends");
		for (int i = 0; i < extendsNodes.getLength(); i++) {
			Element extendsElement = (Element) extendsNodes.item(i);
			clazz.getExtendsClasses().add(extendsElement.getTextContent());
		}

		NodeList implementsNodes = classElement.getElementsByTagName("implements");
		for (int i = 0; i < implementsNodes.getLength(); i++) {
			Element implementsElement = (Element) implementsNodes.item(i);
			clazz.getImplementsInterfaces().add(implementsElement.getTextContent());
		}

		NodeList fieldNodes = classElement.getElementsByTagName("field");
		for (int i = 0; i < fieldNodes.getLength(); i++) {
			Element fieldElement = (Element) fieldNodes.item(i);
			FieldInfos field = parseFieldElement(fieldElement);
			clazz.getFields().add(field);
		}

		NodeList methodNodes = classElement.getElementsByTagName("method");
		for (int i = 0; i < methodNodes.getLength(); i++) {
			Element methodElement = (Element) methodNodes.item(i);
			MethodInfos method = parseMethodElement(methodElement);
			clazz.getMethods().add(method);
		}

		NodeList relationshipNodes = classElement.getElementsByTagName("relationship");
		for (int i = 0; i < relationshipNodes.getLength(); i++) {
			Element relationshipElement = (Element) relationshipNodes.item(i);
			RelationShips relationship = parseRelationshipElement(relationshipElement);
			clazz.getRelationships().add(relationship);
		}

		return clazz;
	}

	private static InterfaceInfos parseInterfaceElement(Element interfaceElement) {
		String interfaceName = interfaceElement.getAttribute("name");
		String modifiers = interfaceElement.getAttribute("modifiers");
		InterfaceInfos interfaceInfo = new InterfaceInfos();
		interfaceInfo.setName(interfaceName);
		interfaceInfo.setModifiers(modifiers);

		NodeList extendsNodes = interfaceElement.getElementsByTagName("extends");
		for (int i = 0; i < extendsNodes.getLength(); i++) {
			Element extendsElement = (Element) extendsNodes.item(i);
			interfaceInfo.getExtendedInterfaces().add(extendsElement.getTextContent());
		}

		return interfaceInfo;
	}

	private static EnumInfos parseEnumElement(Element enumElement) {
		String enumName = enumElement.getAttribute("name");
		String modifiers = enumElement.getAttribute("modifiers");
		EnumInfos enumInfo = new EnumInfos();
		enumInfo.setName(enumName);
		enumInfo.setModifiers(modifiers);

		NodeList constantNodes = enumElement.getElementsByTagName("constant");
		for (int i = 0; i < constantNodes.getLength(); i++) {
			Element constantElement = (Element) constantNodes.item(i);
			enumInfo.getConstants().add(constantElement.getTextContent());
		}

		return enumInfo;
	}

	private static AnnotationInfos parseAnnotationElement(Element annotationElement) {
		String annotationName = annotationElement.getAttribute("name");
		String modifiers = annotationElement.getAttribute("modifiers");
		AnnotationInfos annotationInfo = new AnnotationInfos();
		annotationInfo.setName(annotationName);
		annotationInfo.setModifiers(modifiers);

		NodeList metaAnnotationNodes = annotationElement.getElementsByTagName("metaAnnotation");
		for (int i = 0; i < metaAnnotationNodes.getLength(); i++) {
			Element metaAnnotationElement = (Element) metaAnnotationNodes.item(i);
			annotationInfo.getMetaAnnotations().add(metaAnnotationElement.getTextContent());
		}

		return annotationInfo;
	}

	private static FieldInfos parseFieldElement(Element fieldElement) {
		String fieldName = fieldElement.getAttribute("name");
		String fieldType = fieldElement.getAttribute("type");
		String modifiers = fieldElement.getAttribute("modifiers");
		return new FieldInfos(fieldName, fieldType, modifiers);
	}

	private static MethodInfos parseMethodElement(Element methodElement) {
		String methodName = methodElement.getAttribute("name");
		String returnType = methodElement.getAttribute("returnType");
		String modifiers = methodElement.getAttribute("modifiers");
		MethodInfos method = new MethodInfos(methodName, returnType, modifiers);

		NodeList parameterNodes = methodElement.getElementsByTagName("parameter");
		for (int i = 0; i < parameterNodes.getLength(); i++) {
			Element parameterElement = (Element) parameterNodes.item(i);
			ParameterInfos parameter = parseParameterElement(parameterElement);
			method.getParameters().add(parameter);
		}

		return method;
	}

	private static ParameterInfos parseParameterElement(Element parameterElement) {
		String parameterName = parameterElement.getAttribute("name");
		String parameterType = parameterElement.getAttribute("type");
		return new ParameterInfos(parameterName, parameterType);
	}

	private static RelationShips parseRelationshipElement(Element relationshipElement) {
		String source = relationshipElement.getAttribute("source");
		String type = relationshipElement.getAttribute("type");
		String target = relationshipElement.getAttribute("target");
		return new RelationShips(source, type, target);
	}
}