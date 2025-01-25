# Projet UML Diagrams Generator

### 1.1 Extraction des packages, classes, interfaces, énumérations et annotations
- **Détails** : 

  - Les classes `ProjectInfos`, `PackageInfos`, `ClassInfos`, `InterfaceInfos`, `EnumInfos`, et `AnnotationInfos` ont été implémentées pour représenter les différentes entités d'un projet Java.
  
  - Le parser `ProjectParser` permet d'analyser le projet Java et extrait les informations sur les packages, classes, interfaces et etcvia l'introspection.
  
  - Méthode `ProjectInfos parseProject(String projectPath)`: Analyse le projet et retourne un objet ProjectInfos contenant les informations.

 - Méthode `List<PackageInfos> extractPackages(String projectPath)`: Parcourt les répertoires du projet pour identifier les packages.

 - Méthode `void extractClasses(File packageDir, PackageInfos packageInfos)`: Extrait les informations des classes, interfaces, et etc à partir des fichiers Java.

### 1.2 Extraction des relations entre les entités
- **Détails** :

  - Les relations entre les classes (agrégation, composition, utilisatior) sont détectées via l'introspection et stockées dans la classe `RelationShips`.
  
  - Méthode `String detectFieldRelationshipType(Field field, Class<?> clazz)`de `ClassInfos`: Analyse les champs, méthodes et constructeurs d'une classe pour détecter ses relations avec d'autres classes.

### 1.3 Persistance des données dans un fichier XML
- **Détails** :

  - La Classe `XMLGenerator` permet de générer un fichier XML qui représente la structure du projet.
  
  - Classe `NodeXML`: Cette classe fournit des méthodes pour créer des éléments XML correspondant aux différentes entités du projet (packages, classes, interfaces, énumérations, annotations, etc.).

### 1.4 Génération d'une représentation XMI
- **Détails** :

  - La Classe `XMIGenerator` permet de générer un fichier XMI conforme au standard de l'OMG.

### 1.5 Parseur XML pour charger la structure de données
- **Détails** :

  - Classe `XMLParser` permet de lire un fichier XML et de reconstruire la structure de données en mémoire.
  
  - Méthode `public static ProjectInfos parseXML(String filePath)`: Lit un fichier XML et retourne un objet ProjectInfos contenant les informations du projet.

### 1.6 Affichage console pour vérification
- **Détails** :

  - La classe `Main`affiche dans la console l'architecture des packages, les classes, les interfaces, les énumérations, les annotations, et leurs relations.

## Structure du projet
- `src/org/mql/java/analyzer/models` : Contient les classes représentant les entités du projet (Package, Class, Interface, etc.).
- `src/org/mql/java/analyzer/parser` : Contient le parser pour extraire les informations des classes Java.
- `src/org/mql/java/analyzer/parser/xml` : Contient les classes pour générer et parser les fichiers XML et XMI.
- `src/org/mql/java/analyzer/console` : Contient les classes pour l'affichage console.
- `src/org/mql/java/examples` : Contient des exemples de classes, interfaces, énumérations, et annotations utilisées pour tester le projet.
