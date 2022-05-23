# Installatie handleiding 

## Benodigdheden

- NodeJS
- MySQL client/ workbench 
- Maven
- Java SDK/JDK
- Maildev (npm install)


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

--- 
### Stap 1: Installeren van Java
Voor een up to date handleiding, zie: 
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
Voor een up to date handleiding, zie:
- [Windows](https://toolsqa.com/maven/how-to-install-maven-on-windows/)
- [Linux](https://www.journaldev.com/33588/install-maven-linux-ubuntu)
- [MacOS](https://javabydeveloper.com/how-to-install-maven-on-mac-osx/)
---
(Deze handleiding gaat uit van een windows OS)
1. Open de website van Apache en download de Binary zip archive [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

---

### Stap 3: Installeren van MySQL client

---

### Stap 4: Installeren van NodeJS

---

### Stap 5: Installeren van maildev
Om maildev te kunnen gebruiken is het van belang dat NodeJS correct is geinstalleerd. Als dit het geval is kan maildev geinstalleerd worden door een terminal te openen en vervolgens het volgende commando te typen: 
> npm install maildev

Na het installeren van maildev kan de mailserver opgestart worden met het commando:
> maildev

Het is na het starten van de maildev server van belang dat de server op de juiste poorten draait: \
MailDev webapp running at http://0.0.0.0:1080 \
MailDev SMTP Server running at 0.0.0.0:1025

---

### Stap 6: Installeren van de applicatie
1. 