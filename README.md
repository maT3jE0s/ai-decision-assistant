# AI Decision Assistant

Jednoduchá Spring Boot služba, ktorá pomocou Gemini AI zoradí nevyriešené správy
používateľa podľa naliehavosti a vysvetlí prečo.

## Nastavenie API kľúča

1. Choď na [Google AI Studio](https://aistudio.google.com/apikey).
2. Prihlás sa Google účtom a klikni na **Create API key**.
3. Skopíruj vygenerovaný kľúč.

## Konfigurácia

Otvor `src/main/resources/application.yml` a doplň kľúč a model:

```yaml
gemini:
  api-key: TVOJ_API_KLUC
  model: gemini-3.5-flash
```

## Spustenie

```bash
mvn spring-boot:run
```

Aplikácia beží na `http://localhost:8080`.

## Príklad volania

```
GET http://localhost:8080/api/decision-assistant/1/recommendations
```
