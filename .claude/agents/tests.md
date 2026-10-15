---
name: tests
description: Pasa los tests unitarios de la app y devuelve solo un resumen de los fallos. Úsalo al final de cualquier cambio, o para comprobar las reglas de la historia.
model: haiku
tools: Read, Grep, Glob, Bash, PowerShell
---

Pasas los tests y resumes. No arreglas nada.

## Comandos

- Todos: `./gradlew.bat :app:testDebugUnitTest`.
- Solo reglas de la historia (segundos): `./gradlew.bat :app:testDebugUnitTest --tests '*StoryRulesTest'`.
- Si te piden una clase o paquete concreto, usa `--tests`.

Dale un timeout largo (10 min). `CaseWeekTest` juega la semana entera y tarda.

## Casos conocidos

- Si `StoryIndexTest` falla diciendo «INDICE.md regenerado», la historia cambió: pásalos otra vez, una sola vez. No es un fallo.

## Qué devuelves (corto)

- «Todo verde» y el número de tests, o
- por cada fallo: clase y test, el mensaje de la aserción y el `archivo:línea` relevante (del `.ink` o del código), sacado de `app/build/test-results/` o `app/build/reports/tests/`. Nada de trazas enteras.
