# Mænd der slås

*Mænd der slås* er et digitalt kampspil, hvor brugeren skaber og udvikler sin egen kriger for at deltage i kampe mod andre krigere. Spillet tager inspiration fra klassiske arena-kampspil som **Swords and Sandals**, hvor karakterudvikling og strategi er i fokus.

---

## Formål

Formålet med spillet er at give spilleren mulighed for at:
- Skabe en personlig kriger.
- Tilpasse krigeren med forskellige egenskaber.
- Bruge krigeren i en turbaseret kamparena mod computergenererede modstandere.

---

## Brugertyper

Spillet understøtter følgende brugertyper:

- **Registrerede brugere**  
  Brugere der opretter en personlig konto, hvor deres data gemmes permanent.

- **Gæstebrugere**  
  Brugere der spiller uden at oprette en konto. Data for gæstebrugere gemmes ikke permanent.

---

## Funktionalitet

### Brugerhåndtering

- Oprette en konto ved at angive brugernavn og adgangskode.
- Logge ind med en eksisterende konto.
- Logge ind som gæst, uden at data gemmes.

### Karakterskabelse og -håndtering

- Oprette en kriger og fordele **skill points** på følgende attributter:
  - Health (Livspoint)
  - Strength (Styrke)
  - Defence (Forsvar)
  - Speed (Hastighed)
- Gemme krigeren permanent (gælder kun registrerede brugere).

### Kampfunktion

- Starte en kamp mod en computergenereret modstander.
- Gennemføre en turbaseret kamp, hvor spilleren og modstanderen skiftes til at angribe.
- Afspille en **angrebsanimation** og **lyd effekt**, når et angreb udføres.

### Datahåndtering

- Gemme og genindlæse brugerkonti og deres krigere via en database.
- Opdatere krigerens egenskaber efter kamp, baseret på optjente point eller erfaring.

---

## Fremtidige udvidelser (skuffeprojekt til sommerferien)

- Implementere **multiplayer i realtid** mod andre spillere.
- Tilføje flere typer angreb og evner, fx magi og specielle angreb.
- Forbedre AI for at gøre spillet mere udfordrende.
- Balancere spillet, så brugeren hverken bliver for stærk eller for svag.
- Implementere **leaderboards** og statistik over brugernes præstationer.
- Udnytte **OpenGL** eller andre grafiske API’er til at bruge spillerens grafikkort for bedre ydeevne og grafik.

---

> Dette dokument beskriver de **funktionelle krav** til projektet *Mænd der slås*. Teknologivalg og implementeringsdetaljer er ikke dækket her.
