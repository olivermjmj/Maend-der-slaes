###Mænd der slås
"Mænd der slås" er et digitalt kampspil, hvor brugeren skaber og udvikler sin egen kriger for at deltage i 
kampe mod andre krigere. Spillet tager inspiration fra klassiske arena-kampspil, som Swords and Sandals, 
hvor karakterudvikling og strategi er i fokus.

#Formål
Formålet med spillet er at give spilleren mulighed for at skabe en personlig kriger, 
tilpasse den med forskellige egenskaber og bruge krigeren i en kamparena. 
Spilleren kan konkurrere mod computergenererede modstandere.

#BrugerTyper
Spillet understøtter følgende brugertyper:
- Registrerede brugere: Brugere der opretter en personlig konto.
- Gæstebrugere: Brugere der spiller uden at oprette en konto. Disse brugeres data gemmes ikke permanent.

##Funktionalitet
#BrugerHåndtering
- Brugeren skal kunne oprette en konto ved at angive nødvendige oplysninger som brugernavn og adgangskode.
- Brugeren skal kunne logge ind med en eksisterende konto.
- Brugeren skal kunne logge ind som gæst, uden at tilknytte data til en konto.

#Karakterskabelse og håndtering
- Brugeren skal kunne fordele skill points på attributter som f.eks:
  - Health, Strength, Defence, Speed mm.
- Når krigeren er oprettet og egenskaber er fordelt, skal denne gemmes, så brugeren kan fortsætte senere (gælder ikke gæstebrugere).

#Kampfunktion
- Brugeren skal kunne starte en kamp mod en modstander.
- Modstanderen er en computergenereret kriger:
- Kampen skal have et enkelt turbaseret kampsystem, hvor brugeren og modstanderen skiftes til at angribe.
- Når et angreb bliver initialiseret, så bliver der afspillet en angrebs animation, samt lyd effekt.

#Datahåndtering
- Oprettede brugere og deres krigere skal kunne gemmes og genindlæses ved hjælp af en databaseret løsning.
- Brugeren skal kunne opdatere sin krigers egenskaber efter hver kamp, baseret på optjente point eller erfaring.

#Fremtidige Udvidelser, som skuffe projekt i sommerferie.
- Mulighed for multiplayer mod rigtige brugere i realtid.
- Flere typer angreb og evner, fx magi og specielle angreb.
- Bedre AI for at gøre spillet svære.
- Bedre balancing, så brugeren ikke er for stærk eller svag.
- Leaderboards og statistik.
- Brug af API, som OpenGL, for at anvende brugerens grafikkort.
