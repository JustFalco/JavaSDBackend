# Verantwoordings document
Door Falco Wolkorte

# Verantwoording van gemaakte keuzes

### 1) Test database
Om de applicatie volledig te testen, wordt er gebruik gemaakt van integratie-tests. Deze tests testen de applicatie zoals een gebruiker de applicatie zou gebruiken. Omdat deze tests ook een database nodig hebben, is er in deze applicatie voor gekozen om gebruik te maken van een test database. Dit is een MySQL database die bijna identiek is aan de standaard database van de applicatie, met het voordeel dat niet de productie data van de applicatie wordt aangetast.

### 2) dev en prod omgeving
Net als met de hierboven genoemde test database, wordt er in de hele applicatie onderscheid gemaakt tussen de ontwikkelomgeving en productieomgeving. Dit wordt gedaan door twee verschillende properties bestanden aan te maken. Deze keuze is gemaakt, zodat er tijdens het opstarten van het programma verschillende databases gebruikt kunnen worden, en dat deze databases gevuld kunnen worden met verschillende soorten data. Zo wordt er in de test omgeving alleen een Admin gebruiker aangemaakt, en in de productieomgeving een admin, student, docent en administratief medewerker.

### 3) Input validation
Om de applicatie te beschermen tegen ongewenste gebruikers input (zoals een SQL-injectie) is er voor gekozen om alle gebruikers invoer zoals tekst en nummers te valideren door middel van validatie classes. Deze classes testen de invoer op speciale characters (of een bereik als het om cijfers gaat) en geeft een error terug als de input ongeldig is.

### 4) Lombok
Om zo veel mogelijk standaard methoden achterwege te laten, zoals getters, setters, constructors en toString heb ik er voor gekozen om in deze applicatie gebruik te maken van de Lombok library. Lomok vervangt alle standaard code zoals getters en setters voor handige annotations (`@Getter, @Setter`). Hierdoor blijft de code overzichtelijk en hoeft niet gelijk alles aangepast te worden als er in een model iets toegevoegd of verwijderd wordt. 

### 5) Builder voor modellen
Eén van de onderdelen die lombok (zie punt 4: lombok) meelevert, is de @Builder annotatie. Ik heb er voor gekozen om deze annotatie te gebruiken zodat ik makkelijk mijn data objecten op kan bouwen zonder voor elk mogelijk scenario een nieuwe constructor aan te maken. Hierdoor blijft de applicatie netjes en overzichtelijk, en voldoet het ook beter aan de eisen van clean code en design patterns.

### 6) CommandLineRunner
In deze applicatie is er ook voor gekozen om twee verschillende CommandLineRunners te gebruiken. De CommandLineRunner is gebruikt om test data (gebruikers, etc.) aan de database toe te voegen voordat de applicatie gedraaid wordt. Hierdoor is er bijvoorbeeld altijd een admin gebruiker aanwezig waarmee ik de applicatie kan testen. Er is voor gekozen om twee verschillende CommandLineRunners te gebruiken: één voor de productieomgeving en één voor de development omgeving (zie punt 2: development en productie omgeving).

### 7) Record requestModels
Om data te ontvangen is er gekozen om gebruik te maken van een Data Transfer Object (DTO) in plaats van het hele object op te vragen. Dit is gedaan om een aantal redenen:
1. Met een DTO kan een bepaald object makkelijk opgebouwd worden
2. Met een DTO maakt Spring Boot automatisch een ID aan voor een gemaakt object
3. Omdat in deze applicatie gebruik wordt gemaakt van java 17, kan een DTO als record gedefinieerd worden wat als voordeel geeft dat er minder boilerplate code nodig is.

Deze DTO's zijn terug te vinden onder src/main/java/nl/bd/sdbackendopdracht/models/requestmodels. 

### 8) Mail systeem voor security
In deze applicatie is gekozen om, als een gebruiker een school aanmeld, tweestapsverificatie toe te passen door het ingevoerde e-mail adres een mail te sturen met daarin de login gegevens. Hier is voor gekozen omdat dit er voor zorgt dat de applicatie een stukje veiliger is, en zodat er niet zomaar nieuwe scholen aangemeld kunnen worden. Ook heb ik hier voor gekozen om persoonlijk meer te leren over Spring Mail en SMTP-servers.

### 9) Custom exeptions
Om de code netjes en overzichtelijk te houden heb ik er voor gekozen om zelfgemaakte exeptions te gebruiken. Deze exeptions hebben allemaal een andere HTTP status code waardoor het makkelijk te achterhalen is wat en waar het precies fout is gegaan in de code.

### 10) security op basis van url
Ik heb er voor gekozen om de verschillende endpoints van de applicatie af te schermen op basis van de url. Een voorbeeld hiervan is dat de endpoint /api/v*/student/ alleen toegankelijk is voor gebruikers met de rol STUDENT of de rol DEVELOPER. Achteraf gezien zorgt dit wel voor een aantal extra endpoints, maar hierdoor is de applicatie wel heel makkelijk te beveiligen.
 
## Mogelijke doorontwikkelingen
### Een front-end
In een mogelijke volgende versie van deze applicatie zou ik toch wel graag een front-end willen. 
Hier was ik ook mee begonnen in thymeleaf, maar toen kwam ik er achter dat al mijn endpoints waren opgebouwd om een JSON-body terug te geven, en niet een HTML-pagina. 
Omdat ik toch graag het huidige systeem met het terug geven van JSON wou behouden, en door tijdgebrek, heb ik er voor gekozen om de front-end achterwege te laten. \
In een volgende versie zou ik denk ik toch voor een onafhankelijke front-end gaan, in een framework als React of Vue. 
Hierdoor blijft het mogelijk om de API te benaderen op de gedefinieerde endpoints, maar ook om de app via een website te gebruiken.

### Meer/andere endpoints
Hoewel de applicatie in de huidige staat al veel endpoints heeft, zou ik toch in het vervolg meer endpoints toevoegen. Zo mist er bijvoorbeeld nog een endpoint voor het verkrijgen van alle opdrachten die door één bepaalde docent zijn gegeven. Helaas kon dit nog niet toegevoegd worden in de huidige versie door tijdgebrek, aangezien dit zou betekenen dat er nog veel meer tests geschreven moeten worden.


## Reflectie
Toen ik begon met deze eindopdracht was ik heel erg enthusiast, en had ik erg veel ideeën die ik graag wou uitvoeren. Hierdoor ging ik al erg snel aan de slag met programmeren, waardoor ik eigenlijk heel erg weinig overzicht had van wat er nou eigenlijk moest gebeuren. Het gevolg hiervan was dat de applicatie als gauw 'over engineerd' werd: veel te veel functionaliteit met weinig samenhang. \
Ondanks dit alles ben ik toch heel erg trots op de applicatie die ik heb gemaakt, en ben ik blij met de nieuwe dingen die ik heb geleerd. \
In het vervolg ga ik proberen om meer gestructureerd te werken, dus beginnen met een functioneel/ technisch ontwerp en daarna pas programmeren.