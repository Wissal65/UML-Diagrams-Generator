package org.mql.java.analyzer.console;

import org.mql.java.analyzer.models.*;

public class ProjectPrinter {

	public void printPackageTree(PackageInfos pkg, String prefix) {
		System.out.println(prefix + "📦 [Package] " + pkg.getName());

		for (ClassInfos clazz : pkg.getClasses()) {
			printClassTree(clazz, prefix + "   ");
		}

		for (InterfaceInfos interfaceInfo : pkg.getInterfaces()) {
			printInterfaceTree(interfaceInfo, prefix + "   ");
		}

		for (EnumInfos enumInfo : pkg.getEnums()) {
			printEnumTree(enumInfo, prefix + "   ");
		}

		for (AnnotationInfos annotationInfo : pkg.getAnnotations()) {
			printAnnotationTree(annotationInfo, prefix + "   ");
		}
	}

	public void printClassTree(ClassInfos clazz, String prefix) {
		System.out.println(prefix + "├── 📄 [Class] " + clazz.getName() + " (" + clazz.getModifiers() + ")");

		if (!clazz.getExtendsClasses().isEmpty()) {
			System.out.println(prefix + "│   ├── Hérite de : " + clazz.getExtendsClasses());
		}

		if (!clazz.getImplementsInterfaces().isEmpty()) {
			System.out.println(prefix + "│   ├── Implémente : " + clazz.getImplementsInterfaces());
		}

		if (!clazz.getFields().isEmpty()) {
			System.out.println(prefix + "│   ├── Champs :");
			for (FieldInfos field : clazz.getFields()) {
				System.out.println(prefix + "│   │   ├── " + field.getName() + " : " + field.getType() + " ("
						+ field.getModifiers() + ")");
			}
		}

		if (!clazz.getMethods().isEmpty()) {
			System.out.println(prefix + "│   ├── Méthodes :");
			for (MethodInfos method : clazz.getMethods()) {
				StringBuilder methodSignature = new StringBuilder();
				methodSignature.append(method.getName()).append("(");
				for (int i = 0; i < method.getParameters().size(); i++) {
					ParameterInfos param = method.getParameters().get(i);
					methodSignature.append(param.getType()).append(" ").append(param.getName());
					if (i < method.getParameters().size() - 1) {
						methodSignature.append(", ");
					}
				}
				methodSignature.append(") : ").append(method.getReturnType()).append(" (").append(method.getModifiers())
						.append(")");
				System.out.println(prefix + "│   │   ├── " + methodSignature.toString());
			}
		}

		if (!clazz.getRelationships().isEmpty()) {
			System.out.println(prefix + "│   ├── Relations :");
			for (RelationShips relationship : clazz.getRelationships()) {
				String relationType = getRelationTypeSymbol(relationship.getType());
				System.out.println(prefix + "│   │   " + relationType + " " + relationship.getSource());
			}
		}
	}

	public void printInterfaceTree(InterfaceInfos interfaceInfo, String prefix) {
		System.out.println(
				prefix + "├── 📜 [Interface] " + interfaceInfo.getName() + " (" + interfaceInfo.getModifiers() + ")");

		if (!interfaceInfo.getExtendedInterfaces().isEmpty()) {
			System.out.println(prefix + "│   ├── Étend : " + interfaceInfo.getExtendedInterfaces());
		}

		if (!interfaceInfo.getMethods().isEmpty()) {
			System.out.println(prefix + "│   ├── Méthodes :");
			for (MethodInfos method : interfaceInfo.getMethods()) {
				StringBuilder methodSignature = new StringBuilder();
				methodSignature.append(method.getName()).append("(");
				for (int i = 0; i < method.getParameters().size(); i++) {
					ParameterInfos param = method.getParameters().get(i);
					methodSignature.append(param.getType()).append(" ").append(param.getName());
					if (i < method.getParameters().size() - 1) {
						methodSignature.append(", ");
					}
				}
				methodSignature.append(") : ").append(method.getReturnType()).append(" (").append(method.getModifiers())
						.append(")");
				System.out.println(prefix + "│   │   ├── " + methodSignature.toString());
			}
		}
	}

	public void printEnumTree(EnumInfos enumInfo, String prefix) {
		System.out.println(prefix + "├── 📋 [Enum] " + enumInfo.getName() + " (" + enumInfo.getModifiers() + ")");

		if (!enumInfo.getConstants().isEmpty()) {
			System.out.println(prefix + "│   ├── Constantes :");
			for (String constant : enumInfo.getConstants()) {
				System.out.println(prefix + "│   │   - " + constant);
			}
		}
	}

	public void printAnnotationTree(AnnotationInfos annotationInfo, String prefix) {
		System.out.println(
				prefix + "├── @ [Annotation] " + annotationInfo.getName() + " (" + annotationInfo.getModifiers() + ")");

		if (!annotationInfo.getMetaAnnotations().isEmpty()) {
			System.out.println(prefix + "│   ├── Méta-Annotations : " + annotationInfo.getMetaAnnotations());
		}
	}

	private String getRelationTypeSymbol(String type) {
		switch (type) {
		case "AGGREGATION":
			return "◇──>";
		case "COMPOSITION":
			return "◆──>";
		case "USAGE":
			return "──>";
		default:
			return "──>";
		}
	}
}
