# API

Cette application sert d'API à la brique go-my-defi-admin.

## Installation

Pour développer sur ce projet en environnement local, les outils suivants sont nécessaires :

1. [Maven](https://maven.apache.org/download.cgi) en version 3.8
2. Un IDE permettant le développement Java, par exemple [IntelliJ](https://www.jetbrains.com/idea/download/#section=windows) ou [Eclipse](https://www.eclipse.org/downloads/packages/)

## Développement

Pour compiler en local il faut utiliser la commande :
```
mvn clean install -P dev
```

Pour lancer le projet en local il faut utiliser :
> launch SpringBoot App
> Profiles -P dev

## Modifier l'api

### Modifier ou ajouter des champs
Pour l'ajout ou la modification de champs, il faut modifier les différentes couches impactées par la modification

1. Base de donnée

Utiliser le projet [Database](https://gitlab.com/fdlv/database) de FDLV pour générer les scripts liquibase

3. Couche JPA / DAO

Modifier les classes @Entity du package *com.fdlv.gmd.api.domain* qui sont la représentation Java de nos tables.

Par exemple pour ajouter un nouveau champ x de type string en base on écrira 
```
@Column(name = "x") // l'annotation @Column peut prendre d'autres arguments pour mieux spéficier la colonne référencée comme nullable ou unique
private String x;
```

3. Couche DTO

Le DTO représente notre objet dans sa forme qui est envoyé par l'api.

Dans le cas de notre nouveau champ x on ajoutera dans la classe DTO :
```
private String x;
```

Des annotations hibernate peuvent être ajoutées pour spécifier des contraintes (longueur, null ..) qu'il est important de vérifier car celà peut également produire des erreurs sur l'api car le format reçu ne correspond pas avec l'attendu.


4. Le mapper

Le mapper est l'étape entre le DTO et DAO ou des transformations peuvent être faite entre la réprésentation de l'objet en base ou de l'api.

D'une façon générale, une bonne pratique est d'utiliser les mêmes noms de variable à travers les DAO, les DTO et même le front pour assurer une homogénéité et une bonne lisibilité.
Dans ce cas le mapping API-DTO / DAO est fait de façon transparente car la JVM arrive à relier entre eux les attributs.

Dans le cas où l'on a besoin d'utiliser des noms d'attribut différent entre les couches ou simplement un mapping plus complexe d'objet imbriqué,
on peut réaliser le mapping à la main à base de getter/setter ou 
utiliser des outils comme *Mapstruct* sur ce projet qui a pour but de simplifier le mapping entre deux Bean grâce aux annotations.

Il faut alors préciser l'attribut source, et l'attribut cible à notre mapper. Par exemple :
```
@Mapping(target = "x", source = "x")
objetDto toDto(ObjetDao objetDao);
```
