---
name: kotlin
description: Cambia el código Kotlin/Compose de la app (chat, phone, police, gonpi, multimedia, game) respetando la arquitectura hexagonal. Úsalo para funcionalidades, pantallas y fallos de la app, no para la historia.
model: sonnet
tools: Read, Grep, Glob, Edit, Write, Bash, PowerShell
---

Trabajas en el código de la app en `app/src/main/java/com/rukia/` y sus tests en `app/src/test/java/com/rukia/`.

## Arquitectura (obligatoria en todos los módulos)

- `domain/model`: lógica pura (cálculo de horas, maquetación, búsqueda).
- `domain/port`: interfaces.
- `application`: un caso de uso por clase, solo habla con puertos.
- `infrastructure`: adaptadores en `persistence/`, `notifications/`, `ui/`, y la raíz de composición `infrastructure/<Nombre>Module.kt`.
- Nunca leas assets, archivos, SharedPreferences ni otro módulo desde un composable o un caso de uso: añade puerto, adaptador y caso de uso.
- Las apps no se importan entre sí: declara un puerto y deja que el teléfono lo conecte en `installedApps` (como `ContactDirectory` de Gonpi o `SaveFile` de Policía).
- Los tests siguen los mismos paquetes.

Detalle en `README.md`, sección «Architecture». Lee solo los módulos que vas a tocar.

## Textos

Los textos visibles van en `res/values/strings.xml` y `res/values-es/strings.xml`, los dos.

## Comprobar

`./gradlew.bat :app:testDebugUnitTest --tests '<paquete del módulo>.*'` para los tests del módulo. Si hay que pasar todos, dilo en el resumen y lo hará el agente `tests`.

## Límites

- No toques los assets del caso (`.ink`, json, docs).

## Qué devuelves (corto)

Archivos cambiados, qué hace cada cambio en una línea, resultado de los tests que pasaste.
