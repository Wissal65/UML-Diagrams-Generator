package org.mql.java.analyzer.models;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClassInfos {

	private String name;
	private String modifiers;
	private List<String> extendsClasses;
	private List<String> implementsInterfaces;
	private List<FieldInfos> fields;
	private List<MethodInfos> methods;
	private List<RelationShips> relationships;

	public ClassInfos(Class<?> clazz) {
		this.name = clazz.getSimpleName();
		this.modifiers = Modifier.toString(clazz.getModifiers());
		this.extendsClasses = new ArrayList<>();
		this.implementsInterfaces = new ArrayList<>();
		this.fields = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.relationships = new ArrayList<>();

		detectRelationships(clazz);

		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null && !superClass.equals(Object.class)) {
			this.extendsClasses.add(superClass.getSimpleName());
		}

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			this.implementsInterfaces.add(interfaceClass.getSimpleName());
		}

		for (Field field : clazz.getDeclaredFields()) {
			this.fields.add(new FieldInfos(field));
		}

		for (Method method : clazz.getDeclaredMethods()) {
			this.methods.add(new MethodInfos(method));
		}
	}

	public ClassInfos() {
		this.extendsClasses = new ArrayList<>();
		this.implementsInterfaces = new ArrayList<>();
		this.fields = new ArrayList<>();
		this.methods = new ArrayList<>();
		this.relationships = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModifiers() {
		return modifiers;
	}

	public void setModifiers(String modifiers) {
		this.modifiers = modifiers;
	}

	public List<String> getExtendsClasses() {
		return extendsClasses;
	}

	public void setExtendsClasses(List<String> extendsClasses) {
		this.extendsClasses = extendsClasses;
	}

	public List<String> getImplementsInterfaces() {
		return implementsInterfaces;
	}

	public void setImplementsInterfaces(List<String> implementsInterfaces) {
		this.implementsInterfaces = implementsInterfaces;
	}

	public List<RelationShips> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<RelationShips> relationships) {
		this.relationships = relationships;
	}

	public List<FieldInfos> getFields() {
		return fields;
	}

	public void setFields(List<FieldInfos> fields) {
		this.fields = fields;
	}

	public List<MethodInfos> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodInfos> methods) {
		this.methods = methods;
	}

	private void detectRelationships(Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			Class<?> fieldType = field.getType();
			if (!fieldType.isPrimitive() && !fieldType.getName().startsWith("java.")) {
				String relationshipType = detectFieldRelationshipType(field, clazz);
				addRelationship(clazz.getSimpleName(), fieldType.getSimpleName(), relationshipType);
			}

			if (field.getGenericType() instanceof ParameterizedType) {
				ParameterizedType genericType = (ParameterizedType) field.getGenericType();
				for (Type typeArgument : genericType.getActualTypeArguments()) {
					if (typeArgument instanceof Class) {
						Class<?> collectionType = (Class<?>) typeArgument;
						if (!collectionType.isPrimitive() && !collectionType.getName().startsWith("java.")) {
							addRelationship(clazz.getSimpleName(), collectionType.getSimpleName(), "AGGREGATION");
						}
					}
				}
			}
		}

		for (Method method : clazz.getDeclaredMethods()) {
			for (Parameter parameter : method.getParameters()) {
				Class<?> parameterType = parameter.getType();
				if (!parameterType.isPrimitive() && !parameterType.getName().startsWith("java.")) {
					if (!relationshipExists(clazz.getSimpleName(), parameterType.getSimpleName())) {
						addRelationship(clazz.getSimpleName(), parameterType.getSimpleName(), "USAGE");
					}
				}
			}
		}
	}

	private String detectFieldRelationshipType(Field field, Class<?> clazz) {
		for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			for (Parameter parameter : constructor.getParameters()) {
				if (parameter.getType().equals(field.getType())) {
					return "COMPOSITION";
				}
			}
		}

		for (Method method : clazz.getDeclaredMethods()) {
			if (method.getName().startsWith("set") && method.getParameterCount() == 1
					&& method.getParameterTypes()[0].equals(field.getType())) {
				return "AGGREGATION";
			}
		}

		return "AGGREGATION";
	}

	private void addRelationship(String source, String target, String type) {
		if (!relationshipExists(source, target)) {
			RelationShips relationship = new RelationShips(type, type, source);
			relationship.setSource(source);
			relationship.setTarget(target);
			relationship.setType(type);
			relationships.add(relationship);
		}
	}

	private boolean relationshipExists(String source, String target) {
		for (RelationShips relationship : relationships) {
			if (relationship.getSource().equals(source) && relationship.getTarget().equals(target)) {
				return true;
			}
		}
		return false;
	}

}
