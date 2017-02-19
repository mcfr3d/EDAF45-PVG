Denna README fil är till för att underlätta för en ny programmerare att sätta sig in i programmet. I vår tekniska dokumentation på Wikin finns en beskrivning av programmets helhet samt UML-diagram på de olika paketen och en bild som beskriver ett programflöde. Vi har även en manual där vi beskriver hur man bygger programmet och hur konfigurationsfilen hanteras vid start av de olika loppen etc. Nedan följer en kort beskrivning av alla klasser

—— registration

Gui - Huvudfönstret för registrering av Racers. Uppdelad i tre paneler: översta för att skriva in startnummer och registrera, vänstra för att se alla nuvarande registreringar, och högra för att se alla nuvarande registreringar som ej gått igenom på grund av oläsbar nummerplåt. Det går att ta bort och editera registreringar i höger panel.

ListItem - Ett ListItem-objekt består av Racer med sin registerade tid, samt Edit och Remove button om den befinner sig i höger panel

Main - main för registreringsprogrammet

Subscriber - Enbart till för att notifiera Gui:t då en racer har blivit editerad eller borttagen. Implementeras av Gui.



—— resultMerge

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



—— util

Chart - Stöds ej för tillfället. Är tänkt att göra regex enklare och smidigare att hantera vid hantering av strängar.

IOReader - Klass som sköter inläsning av filer

RegistrationIO - 

ResultWriter - 

TotalTimeCalculator - 

Getopt - 

EvaluatedExpression - 

RegistrationExpression -