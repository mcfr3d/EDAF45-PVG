# README

Denna README fil är till för att underlätta för en ny programmerare att sätta sig in i programmet. I vår tekniska dokumentation på Wikin finns en beskrivning av programmets helhet samt UML-diagram på de olika paketen och en bild som beskriver ett programflöde. Vi har även en manual där vi beskriver hur man bygger programmet och hur konfigurationsfilen hanteras vid start av de olika loppen etc. 

#1. Avklarade stories 

* Story 3: Enkel Resultatlista
* Story 4: Personuppgifter
* Story 5: Totaltid
* Story 6: Felaktiga registreringar
* Story 7: GUI för registrering
* Story 8: Registrering av tider
* Story 9: Varvlopp
* Story 10: Flera skall kunna registrera måltider
* Story 11: Enkel masstart
* Story 12: Målgång där nummerplåten inte kan läsas i förväg
* Story 13: Hantera klasser
* Story 14: Manualer/instruktioner
* Story 15: Hantera felaktiga tidsdata
* Story 16: Hantera registreringar med otillåtna startnummer
* Story 17: Variabelt antal uppgifter om förarna
* Story 18: Sorterad resultatlista
* Story 19: Etapplopp
* Story 20: Specialsträckor
* Story 21: Felhantering för etapplopp
* Story 22: Minimitid för etapper
* Story 23: Sorterad resultatlista (även etapper)
* Story 24: Masstart
* Story 25: Stöd för konfiguration
* Story 26: Teknisk dokumentation
* Story 27: Serverlösning
* Story 28: HTML-resultat
* Story 29: Integrera HTML-baserad resultatlista
* Story 31: Teknisk dokumentation
* Story 32: Källkodsrelease 2
* Story 33B: Robust program
* Story 33C: Kodkvalité
* Story 35: Nattetapp
* Story 36: Kö vid målgång 

#2. Teknisk Dokumentation

Den tekniska dokumentationen kan hittas överst i arkitekturen. Där finns beskrivning av hur projektet är uppbyggt, hur det används samt befintliga verktyg för att utveckla detta.

#3. Kort översikt

##3.1 Arkitektur
Programet är organiserat i tre paket:

* registration
* resultMerge
* util

##3.2 Filstruktur

Våra Javaklasser ligger i "src" och alla enhetstest ligger i mappen "test".
config.json inheåller en exempel config fil 

##3.3 Bygga projektet
Projektet byggs i två program.
För att bygga projektet gör följande:

1. Skriv 'gradlew build' i repository /PVGvt17-team06-production / dasUberEnduroApplicationen /
2. Då ligger jar-filerna i "/PVGvt17-team06-production/dasUberEnduroApplicationen/registration/build/libs/registration,jar" och "/PVGvt17-team06-production/dasUberEnduroApplicationen/resultMerge/build/libs/resultMerge.jar"

##3.4 Köra projektet
Regisration kan köras genom att dubbelklicka på jar-filen.
ResultMerge körs genom komandotolken med kommandot java -jar ${home}/dasUberEnduroApplicationen/resultMerge/build/libs/resultMerge.jar -c config.json
När reultmerge ska konfigfilen,namnfilen,starttidsfilen och sluttidsfilen ligga i samma mapp som JAR filen.

##3.5 Köra acceptanstester
Alla acceptanstester och unittester kan köras genom kommandot ./runAcT.sh då man är i huvudmappen

#4. Paket och Klasser

##4.1 registration

Gui - Huvudfönstret för registrering av Racers. Uppdelad i tre paneler: översta för att skriva in startnummer och registrera, vänstra för att se alla nuvarande registreringar, och högra för att se alla nuvarande registreringar som ej gått igenom på grund av oläsbar nummerplåt. Det går att ta bort och editera registreringar i höger panel.

ListItem - Ett ListItem-objekt består av Racer med sin registerade tid, samt Edit och Remove button om den befinner sig i höger panel

Main - main för registreringsprogrammet

Subscriber - Enbart till för att notifiera Gui:t då en racer har blivit editerad eller borttagen. Implementeras av Gui.



##4.2 resultMerge

ConfigReader - Läser config-filen som innehåller alla väsentliga filer. Beskrivs mer i vår manual. Här skapas en database beroende på vilken Race-typ som väljs.

Database - Central klass för resultathantering. Här hanteras exempelvis racers, tider, racetyp, resultat, m.m.

RaceType - Interface med metoder gemensamma för alla olika RaceTypes.

EtappRace - Funktionalitet för etapprace. Implementerar RaceType. Stöds ej för tillfället (se Teknisk doumentation).

MultiLapRace - Funktionalitet för varvlopp, med eller utan masstart. Implemeterar RaceType.

OneLapRace - Funktionalitet för ett varvs lopp, med eller utan masstart. Implementerar RaceType. 

RaceGui - Gui för resultMerge-programmet.

MaratonRaceGui - Gui för maratonlopp.

MassStartRaceGui - Gui för masstart.

NormalRaceGui - Gui för ett varvs lopp.

Main - main för resultMerge utan stöd för konfiguration.

MainWithConfig - main för resultMerge med stöd för konfiguration.

Racer - Skapar en racer med namn, klass, startnummer och optional data. Kan jämföra sig med andra racers för att kunna sorteras samt skriva ut sig själv som en sträng.

Time - Stöds ej för tillfället. Är tänkt att representera tider generellt sätt för hela programmet och implementeras i samband med etapplopp.



##4.3 util

Chart - Stöds ej för tillfället. Är tänkt att göra regex enklare och smidigare att hantera vid hantering av strängar.

IOReader - Klass som sköter inläsning av filer

RegistrationIO - Läser från och skriver till en fil för registration Gui-klassen.

ResultWriter - Generera tre olika resultatfiler; en sorterad .txt, en osorterad .txt samt en sorterad .html fil.

TotalTimeCalculator - Kan användas för att utföra beräkningar hos tidssträngar.

Getopt - Parsar argument från kommandoraden.

EvaluatedExpression - Wrapper för två länkade listor, en som ger korrekt angivna startnummer samt felaktigt angivna startnummer.

RegistrationExpression - Parsar en sträng från registration's Gui och ger tillbaka en EvaluatedExpression.
