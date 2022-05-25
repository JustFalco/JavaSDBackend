# Technisch ontwerp
Door Falco Wolkorte

## Inleiding

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
![Use case](/SDeindopdrachtUCD.jpg)

## Klassendiagram
![Klassen Diagram](/classDiagramSD.jpg)

## Database diagram
![Database diagram](/DatabaseDiagram.png)

## Sequentie diagrammen
### Sequentie diagram voor het registreren van een student gebruiker
![Seqentie diagram aanmaken student](/SequenceDiagramRegisterStudent.png)
### Sequentie diagram voor het toevoegen van een student aan een course
![Sequentie diagram toevoegen student aan course](/SequenceDiagramAddStudentToCourse.png)

## Systeem specificaties
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
