# Installatie handleiding 
Door Falco Wolkorte

## Benodigdheden

- NodeJS
- MySQL client/ workbench 
- Maven
- Java SDK/JDK
- Maildev (npm install)
- NPM


## Versie nummers
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

# Installatie

De installatie kan op twee verschillende manieren worden uitgevoerd: handmatig of via docker.
Voor de handmatige installatie, volg stappen 1 tot en met 6. Voor de installatie via docker, volg 'Applicatie installeren via docker'.

--- 
### Stap 1: Installeren van Java
Voor een up-to-date handleiding, zie: 
- [Windows](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-microsoft-windows-platforms.html)
- [Linux](https://www.itzgeek.com/how-tos/linux/how-to-install-oracle-java-jdk-17-on-linux.html)
- [MacOS](https://java.tutorials24x7.com/blog/how-to-install-java-17-on-mac)

(Deze handleiding gaat uit van een windows OS)
1. Open de website van Oracle met de downloads van de Java 17 SDK [https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. Download de juiste installer onder "Java SE Development Kit 17.0.3"
3. Voer de installer uit
4. Voeg java toe aan de PATH variabelen
5. voeg JAVA_HOME toe aan de systeem variabelen
6. Test of de installatie gelukt is door in de terminal het volgende commando uit te voeren:
> java -version
7. Als de java versie overeen komt met de versie onder het kopje Versie nummers -> Java is de installatie geluk.

---
### Stap 2: Installeren van Maven
Voor een up-to-date handleiding, zie:
- [Windows](https://toolsqa.com/maven/how-to-install-maven-on-windows/)
- [Linux](https://www.journaldev.com/33588/install-maven-linux-ubuntu)
- [MacOS](https://javabydeveloper.com/how-to-install-maven-on-mac-osx/)

(Deze handleiding gaat uit van een windows OS)
1. Open de website van Apache en download de Binary zip archive [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
2. Pak het Binary zip bestand uit in de C:\ folder
3. Voeg MAVEN_HOME toe aan de systeem variabelen met de waarde C:\apache-maven-3.8.5
4. Voeg maven toe aan de PATH variabelen
5. Test of de installatie geslaagt is door het volgende commando in een terminal uit te voeren:
> mvn -version
6. Als het resultaat overeen komt met het kopje Versie nummers -> Maven is de installatie gelukt
---

### Stap 3: Installeren van MySQL client
Voor een up-to-date handleiding, zie:
- [Windows](https://www.liquidweb.com/kb/install-mysql-windows/)
- [Linux](https://dev.mysql.com/doc/mysql-shell/8.0/en/mysql-shell-install-linux-quick.html)
- [MacOS](https://dev.mysql.com/doc/refman/5.7/en/macos-installation-pkg.html)

(Deze handleiding gaat uit van een windows OS)
1. Open de website van MySQL (https://dev.mysql.com/downloads/installer/)
2. download het bestand mysql-installer-web-community-8.0.29.0.msi
3. Voer het installatiebestand uit
4. Accepteer de licensie overeenkomst
5. Bij het kiezen van een set-up type, kies voor 'Custom'
6. Selecteer vervolgens de volgende pakketen om te installeren:
   1. MySQL Server 8.0.29 X64
   2. MySQL Workbench 8.0.29
   3. Connector/ODBC 8.0.29
   4. Connector/J 8.0.29
7. Voer de installatie verder uit
8. Na de installatie moet de server geconfigureerd worden op de volgende mannier:
   1. Zorg dat port 3306 gekozen is
   2. zorg dat x Protocol poort 33060 gekozen is
   3. Zorg dat de config type 'Development computer' is
   4. klik next
   5. Kies voor 'use strong password encyrpting for auth'
   6. klik next
   7. Kies een sterk ROOT wachtwoord (Raak deze niet kwijt, hij is heel belangrijk!)
   8. Klik vervolgens op 'Add User'
   9. Maak een gebruiker aan
   10. Klik op next
9. Vervolg met de installatie zonder iets aan te passen
10. Voltooi de installatie
11. Kijk of de installatie is gelukt door MySQL workbench te openen 
12. Verbind via de workbench met de standaard localhost:3306 server
13. Voer de volgende query uit:
> CREATE SCHEMA sddev ;

---

### Stap 4: Installeren van NodeJS
Voor een up-to-date handleiding, zie:
- [Windows](https://phoenixnap.com/kb/install-node-js-npm-on-windows)
- [Linux](https://www.geeksforgeeks.org/installation-of-node-js-on-linux/)
- [MacOS](https://nodesource.com/blog/installing-nodejs-tutorial-mac-os-x/)

(Deze handleiding gaat uit van een windows OS)
1. Open de website van NodeJS (https://nodejs.org/en/)
2. Klik op de download knop voor de Long term support (LTS)
3. Voer het installatiebestand uit
4. Het systeem vraagt of je het bestand wilt uitvoeren: kies ja (of run)
5. In de installatie wizzard, klik next
6. Accepteer de licentie overeenkomst
7. Het volgende scherm vraagt naar een installatie locatie, laat deze gewoon standaard en klik next
8. Vervolgens wordt gevraagd om componenten te behouden/ verwijderen, verander hier niks aan en klik next
9. Klik installeer en wacht tot de installatie klaar is
10. Kijk of de installatie is gelukt door een terminal te openen en de volgende commando's uit te voeren: 
> node -v
> npm -v
11. Als de versies overeen komen met de versie nummers in het kopje Versie nummers -> NPM en Versie nummers -> NodeJS is de installatie geslaagd

---

### Stap 5: Installeren van maildev
1. Om maildev te kunnen gebruiken is het van belang dat NodeJS correct is geinstalleerd. Als dit het geval is kan maildev geinstalleerd worden door een terminal te openen en vervolgens het volgende commando te typen: 
> npm install maildev --global

2. Na het installeren van maildev kan de mailserver opgestart worden met het commando:
> maildev

3. Het is na het starten van de maildev server van belang dat de server op de juiste poorten draait: \
MailDev webapp running at http://0.0.0.0:1080 \
MailDev SMTP Server running at 0.0.0.0:1025

( Mocht de maildev server niet op de juiste poorten draaien, dan zal dit later in het applicatie.properties bestand aangepast moeten worden )

---

### Stap 6: Installeren van de applicatie
1. Download het zip bestand van de applicatie en pak deze uit
2. Open het project bestand in een tekst bewerker naar keuze (Notepad++, VScode, IntelliJ IDEA, Atom)
3. Open het bestand application-prod.properties onder src/main/resources/application-prod.properties
4. Vervang ${DATABASE_USERNAME} voor root 
5. Vervang ${DATABASE_PASSWORD} voor het root wachtwoord van de database
6. Open vervolgens het bestand application.properties en kijk of het er als volgt uit ziet
```spring.profiles.active=prod```
7. Open de terminal en voer het volgende commando uit:
> mvn clean install -U
7. Zorg dat de hiervoor geinstalleerde maildev server draaid door een nieuwe terminal te openen en het volgende commando uit te voeren:
> maildev
8. Voer als laatste het volgende commando uit:
>  java -jar target/software-development-eindopdracht.jar
9. Als de installatie goed is verlopen draait de spring boot applicatie nu op localhost:8080, en is de OpenAPI documentatie te vinden op http://localhost:8080/swagger-ui/index.html en is de SMPT mail server te vinden http://localhost:1080/

## Applicatie installeren via docker
(Deze optie alleen gebruiken als alle voorgaande stappen niet werkten om de applicatie draaiende te krijgen, en werkt waarschijnlijk alleen op de x86-64 architectuur)

Om de applicatie te draaien in een container, is het belangrijk dat docker is geinstalleerd. Volg hiervoor één van de volgende handleidingen:
- [Windows](https://docs.docker.com/desktop/windows/install/)
- [Linux](https://docs.docker.com/engine/install/ubuntu/)
- [MacOS](https://docs.docker.com/desktop/mac/install/)

Zorg er voor dat tijdens het installeren ook WSL geinstalleerd word, anders kunnen er rare bugs ontstaan, zie (stap 5)[https://docs.microsoft.com/nl-nl/windows/wsl/install-manual#step-4---download-the-linux-kernel-update-package]

Na het installeren van docker:
1. Pak het zip bestand van de applicatie uit
2. Open de applicatie in een tekst bewerker naar keuze (Notepad++, VScode, IntelliJ IDEA, Atom)
3. Creeër in de root folder van het project een bestand met de naam .env
4. Voeg de volgende waarden in het bestand:
```
MYSQLDB_USER=root
MYSQLDB_ROOT_PASSWORD=123456
MYSQLDB_DATABASE=sddev
MYSQLDB_LOCAL_PORT=3307
MYSQLDB_DOCKER_PORT=3306
SPRING_LOCAL_PORT=8081
SPRING_DOCKER_PORT=8080
```
5. Sla het bestand op
6. Open de terminal van de tekst bewerker en voer het volgende commando uit
> docker-compose up --build
4. Na een tijdje zou de applicatie op localhost:8081 moeten draaien en is de OpenAPI documentatie te vinden op http://localhost:8081/swagger-ui/index.html en is de SMPT mail server te vinden http://localhost:1080/

# Testgebruikers / User rollen
De applicatie word standaard gestart met testdata. Deze data bestaat uit 4 verschillende gebruikers en een school:

### Student (Role: STUDENT)
De test gebruiker student bestaat uit de volgende inloggegevens: 
- Email: falco@wolkorte.nl
- Wachtwoord: StrongP@ssword123
Met deze gebruiker kan ingelogd worden en deze gebruiker heeft toegang tot alle endpoints die beginnen met /api/v*/student/** 

### Docent (Role: TEACHER)
De test gebruiker docent bestaat uit de volgende inloggegevens:
- Email: john@doe.nl
- Wachtwoord: StrongP@ssword123 \
  Met deze gebruiker kan ingelogd worden en deze gebruiker heeft toegang tot alle endpoints die beginnen met /api/v*/teacher/**

### Administratief medewerker (Role: ADMINISTRATOR)
De test gebruiker student bestaat uit de volgende inloggegevens:
- Email: jane@doe.nl
- Wachtwoord: StrongP@ssword123 \
  Met deze gebruiker kan ingelogd worden en deze gebruiker heeft toegang tot alle endpoints die beginnen met /api/v*/administrator/**

### Developer / Admin (Role: DEVELOPER)
De test gebruiker student bestaat uit de volgende inloggegevens:
- Email: Admin
- Wachtwoord: StrongP@ssword123 \
  Met deze gebruiker kan ingelogd worden en deze gebruiker heeft toegang tot alle endpoints

### School
De standaard school in de database heeft de volgende waarden:
- School naam: Novi
- School email: novi@education.nl \
Met een school kan niet ingelogd worden in de applicatie.

# REST-endpoints
De applicatie kan op de volgende endpoints benaderd worden. Deze endpoints zijn ook terug te vinden in de OpenAPI documentatie op http://localhost:8080/swagger-ui.html of http://localhost:8081/swagger-ui.html (voor docker gebruikers). Deze documentatie werkt uiteraard alleen als de applicatie actief is. 

## user-controller
- [GET] /api/v1/user/get_details
- [GET] /api/v1/user/get_details/user={userId}
- [GET] /api/v1/user/get_details/email={email}
- [PUT] /api/v1/user/change/email={email} \
Request body:
```
{
  "firstName": "string",
  "middleName": "string",
  "lastName": "string",
  "email": "string@string.nl",
  "dateOfBirth": "2022-05-24",
  "password": "Secur3P@ss",
  "studentYear": 0,
  "isActive": true
}
```
- [DELETE] /api/v1/admin/delete/user={userId}

## Task-controller
- [GET] /api/v1/task/get_tasks_from_student/student={userId}
- [GET] /api/v1/task/get_taskfiles/task={taskId}
- [GET] /api/v1/task/get_taskfiles/file={fileId}
- [GET] /api/v1/task/get_taskdetails/task={taskId}
- [GET] /api/v1/admin/coffee
- [POST] /api/v1/teacher/task/create_task \
Request body:
```
{
  "taskName": "string",
  "taskDescription": "string",
  "taskDeadline": "2022-05-24T17:28:20.640Z"
}
```
- [POST] /api/v1/teacher/task/create_task/course={courseId} \
  Request body:
```
{
  "taskName": "string",
  "taskDescription": "string",
  "taskDeadline": "2022-05-24T17:28:20.640Z"
}
```
- [POST] /api/v1/teacher/task/add_file/task={taskId} \
  Request body:
```
[] array of files
```
- [PUT] /api/v1/teacher/task/change/task={taskId} \
  Request body:
```
{
  "taskName": "string",
  "taskDescription": "string",
  "taskDeadline": "2022-05-24T17:29:59.359Z"
}
```
- [PUT] /api/v1/teacher/task/add_student/student={userId}&task={taskId}
- [DELETE] /api/v1/teacher/task/delete/task={taskId}
- [DELETE] /api/v1/teacher/task/delete/student={userId}&task={taskId}

## Grade-controller
- [GET] /api/v1/student/grades/get_grade/last/student={userId}
- [GET] /api/v1/grades/get_grade/overview/student={userId}
- [GET] /api/v1/grades/get_grade/grade={gradeId}
- [POST] /api/v1/teacher/grades/submit_grade/student={studentId} \
  Request body:
```
{
  "description": "string",
  "grade": 1,
  "weight": 1,
  "testDate": "2022-05-24",
  "markBelongsToTaskId": 0
}
```
- [PUT] /api/v1/teacher/grade/change_grade/student={studentId}&grade={gradeId} \
  Request body:
```
{
  "description": "string",
  "grade": 1,
  "weight": 1,
  "testDate": "2022-05-24",
  "markBelongsToTaskId": 0
}
```
- [DELETE] /api/v1/teacher/grades/delete/grade={gradeId}

## Course-controller
- [GET] /api/v1/user/course/get_details/course={courseId}
- [POST] /api/v1/administrator/course/create \
  Request body:
```
{
  "courseName": "string",
  "courseDescription": "string",
  "teacherGivesCourseId": 1
}
```
- [POST] /api/v1/administrator/course/add_students/course={courseId} \
  Request body:
```
[
   2
]
```
- [POST] /api/v1/administrator/course/add_student/student={studentId}&course={courseId}
- [PUT] /api/v1/administrator/course/change/course={courseId} \
  Request body:
```
{
  "courseName": "string",
  "courseDescription": "string",
  "teacherGivesCourseId": 1
}
```
- [DELETE] /api/v1/administrator/course/remove/course={courseId}
- [DELETE] /api/v1/administrator/course/delete_student/student={studentId}&course={courseId}

## Absence-controller
- [GET] /api/v1/absence/get_absence/student={userId}
- [GET] /api/v1/absence/absenceId={absenceId}
- [POST] /api/v1/administrator/absence/submit \
  Request body:
```
{
  "absentStudent": 2,
  "absenceType": "ABSENT",
  "absenceDescription": "string"
}
```
- [PUT] /api/v1/administrator/absence/change/absence={absenceId} \
  Request body:
```
{
  "absentStudent": 2,
  "absenceType": "ABSENT",
  "absenceDescription": "string"
}
```
- [DELETE] /api/v1/administrator/absence/delete/absenceId={absenceId}

## Registration-controller
- [POST] /api/v1/registration/register_school \
  Request body:
```
{
  "schoolName": "string",
  "schoolMail": "string@string.nl"
}
```
- [POST] /api/v1/administrator/registration/register_teacher \
  Request body:
```
{
  "firstName": "string",
  "middleName": "string",
  "lastName": "string",
  "email": "string@string.nl",
  "dateOfBirth": "2022-05-24",
  "password": "Secur3P@ss",
  "studentYear": 0,
  "isActive": true
}
```
- [POST] /api/v1/administrator/registration/register_student \
  Request body:
```
{
  "firstName": "string",
  "middleName": "string",
  "lastName": "string",
  "email": "string@string.nl",
  "dateOfBirth": "2022-05-24",
  "password": "Secur3P@ss",
  "studentYear": 0,
  "isActive": true
}
```
- [POST] /api/v1/administrator/registration/register_administrator \
  Request body:
```
{
  "firstName": "string",
  "middleName": "string",
  "lastName": "string",
  "email": "string@string.nl",
  "dateOfBirth": "2022-05-24",
  "password": "Secur3P@ss",
  "studentYear": 0,
  "isActive": true
}
```