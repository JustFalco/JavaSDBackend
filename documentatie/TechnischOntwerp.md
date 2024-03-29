# Technisch ontwerp
Door Falco Wolkorte

## Inleiding
Voor de eindopdracht van software development heb ik een web API gemaakt genaamd project Progen. Deze API is qua functionaliteit geïnspireerd van mijn vorige java project: Magister2.0.  
Project Progen is een API voor school beheer software. Zo geeft deze API de mogelijkheid voor docenten, studenten en administratief medewerkers om:
- In te loggen
- Opdrachten te geven en te maken
- Cijfers te bekijken en in te voeren
- Cursussen aan te bieden
- Bestanden voor taken te up- en downloaden
- En nog veel meer   

Voordat de applicatie ontwikkeld kan worden is het eerst belangrijk om een technisch ontwerp te hebben. Daar dient dit bestand voor. 
In het eerste gedeelte wordt er gekeken naar de eisen van de applicaties, verwoord in 'user stories'.
Vervolgens wordt er op basis van de user stories een Use case en klassen diagram gemaakt. 
Als laatste wordt er gekeken naar de database diagram van de applicatie, 
en de sequentie diagrammen voor het registreren van een student en de sequentie diagram voor het toevoegen van een student aan een cursus.

## User stories
### Algemene gebruikers stories

**Story:** Ik wil als gebruiker veilig kunnen inloggen  
**Acceptatiecriteria:** Ik wil met mijn email en een geëncrypt wachtwoord kunnen inloggen in de applicatie


**Story:** Ik wil als gebruiker een school kunnen registreren  
**Acceptatiecriteria:** Ik wil dat alle gebruikers een school met schoolnaam en school mail kunnen registreren, en vervolgens op de schoolmail de inloggegevens ontvangen voor één gebruiker


**Story:** ik wil als gebruiker mijn persoonlijke gegevens kunnen inzien  
**Acceptatiecriteria:** Ik wil dat alle gebruikers gegevens kunnen opvragen zoals: naam, email, geboortedatum, leeftijd, de school en opdrachten

### Student stories

**Story:** ik wil als student kunnen zien welke opdrachten ik heb  
**Acceptatiecriteria:** ik wil dat alle studenten een lijst krijgen met opdrachten die hij/zij moet uitvoeren


**Story:** ik wil als student kunnen zien wat mijn cijfers zijn  
**Acceptatiecriteria:** Ik wil dat alle studenten alle behaalde cijfers en de gegevens van dit cijfer kunnen opvragen


**Story:** ik wil als student een overzicht van de laatste vijftien cijfers  
**Acceptatiecriteria:** ik wil dat alle studenten een lijst van hun vijftien laatst behaalde cijfers kunnen opvragen bij de applicatie


**Story:** Ik wil als student een overzicht kunnen zien van mijn absentie  
**Acceptatiecriteria:** ik wil dat alle studenten via één endpoint een lijst van hun persoonlijke absentie krijgen.


### Docent stories

**Story:** ik wil als docent een opdracht kunnen geven aan studenten   
**Acceptatiecriteria:** ik wil dat een docent een opdracht kan geven aan een student of groep studenten met daarin een opdracht beschrijving en een bestand (zoals pdf)


**Story:** ik wil als docent een student absent kunnen zetten   
**Acceptatiecriteria:** ik wil dat een docent een student te laat of absent kan zetten, en kan aangeven of deze student zijn/haar huiswerk vergeten is


**Story:** Ik wil als docent cijfers in kunnen voeren van studenten   
**Acceptatiecriteria:** ik wil dat een docent de cijfers van een toets of opdracht voor één of meerdere studenten tegelijk kan invoeren, met een cijfer, een weging en een omschrijving

## Use case diagram
Hier onder is de Use case diagram van de applicatie te zien.   
Hierin is de functionaliteit van de verschillende gebruikers verwerkt op basis van de hierboven genoemde user stories.   
![Use case](/images/SDeindopdrachtUCD.jpg)  
![Use case](/documentatie/images/SDeindopdrachtUCD.jpg)  

[//]: # (De reden dat de foto's er twee keer staan is omdat de bovenste van de twee alleen werkt in IntelliJ, en de onderste alleen in github)

## Klassendiagram
Het onderstaande klassen diagram is een schematische weergave van de objecten binnen de applicatie, en de relatie tussen deze objecten.
![Klassen Diagram](/images/classDiagramSD.jpg)  
![Klassen Diagram](/documentatie/images/classDiagramSD.jpg)

## Database diagram
Het onderstaande database diagram is de diagram die automatisch gegenereerd is door Spring Boot bij het opstarten van de applicatie, en geeft de werkelijke weergave van objecten en hun relatie weer.
![Database diagram](/images/DatabaseDiagram.png)  
![Database diagram](/documentatie/images/DatabaseDiagram.png)

## Sequentie diagrammen
### Sequentie diagram voor het registreren van een student gebruiker
![Seqentie diagram aanmaken student](/images/SequenceDiagramRegisterStudent.png)  
![Seqentie diagram aanmaken student](/documentatie/images/SequenceDiagramRegisterStudent.png)

### Sequentie diagram voor het toevoegen van een student aan een course
![Sequentie diagram toevoegen student aan course](/images/SequenceDiagramAddStudentToCourse.png)  
![Sequentie diagram toevoegen student aan course](/documentatie/images/SequenceDiagramAddStudentToCourse.png)

# Systeem specificaties
## Externe libraries/ dependencies
`org.apache.commons:commons-text
Installed versions: 1.9`

`com.google.guava:guava  
Installed versions: 31.1-jre`

`com.h2database:h2  
Installed versions: 2.1.212`

`mysql:mysql-connector-java  
Installed versions: 8.0.29`

`org.projectlombok:lombok  
Installed versions: 1.18.24`

`org.springdoc:springdoc-openapi-ui
Installed versions: 1.6.8`

`org.springframework.boot:spring-boot-devtools
Installed versions: 2.6.7`

`org.springframework.boot:spring-boot-starter-data-jpa
Installed versions: 2.6.7`

`org.springframework.boot:spring-boot-starter-jdbc
Installed versions: 2.6.7`

`org.springframework.boot:spring-boot-starter-mail
Installed versions: 2.6.7`

`org.springframework.boot:spring-boot-starter-security
Installed versions: 2.6.7`

`org.springframework.boot:spring-boot-starter-test
Installed versions: 2.6.7`

`org.springframework.boot:spring-boot-starter-thymeleaf
Installed versions: 2.6.7`

`org.springframework.boot:spring-boot-starter-web
Installed versions: 2.6.7`

`org.springframework.security:spring-security-test
Installed versions: 5.6.3`


## Software versies
### MySQL
```
| tls_version              | TLSv1.2,TLSv1.3              |
| version                  | 8.0.28                       |
| version_comment          | MySQL Community Server - GPL |
| version_compile_machine  | x86_64                       |
| version_compile_os       | Win64                        |
| version_compile_zlib     | 1.2.11                       | 
```

### NodeJS
`version 16.14.2`

### NPM
`8.7.0`

### Maven
```
Apache Maven 3.8.5 
Maven home: C:\apache-maven-3.8.5
Java version: 17.0.3, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-17.0.3
Default locale: en_GB, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

### Java
```
java version "17.0.3" 2022-04-19 LTS\
Java(TM) SE Runtime Environment (build 17.0.3+8-LTS-111)\
Java HotSpot(TM) 64-Bit Server VM (build 17.0.3+8-LTS-111, mixed mode, sharing)
```
