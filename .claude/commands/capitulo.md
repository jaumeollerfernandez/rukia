---
description: Cambia la historia del caso con los agentes (historia → main.ink → sincronia → medios → tests)
argument-hint: <qué cambiar o escribir>
---

Cambio pedido: $ARGUMENTS

Orquesta con los agentes de `.claude/agents/`. Pasa a cada uno solo lo que necesita y no repitas en esta sesión el trabajo que hacen ellos.

1. **historia**: dale el cambio pedido. Si afecta a chats independientes entre sí, lanza un agente por chat en paralelo.
2. **main.ink**: declara tú las variables y funciones que pidan los agentes de historia.
3. **sincronia**: pásale los resúmenes de historia.
4. **medios**: solo si hay imágenes, audios o guiones de llamada nuevos o cambiados. Puede ir en paralelo con `sincronia` si los guiones ya están escritos por historia; si no, después.
5. **tests**: al final. Si fallan, arregla lo pequeño tú mismo; si es grande, vuelve al agente que lo causó con el fallo concreto y repite `tests`.

Si el cambio son unas pocas líneas, hazlo tú sin agentes.

Al terminar, di al usuario en pocas líneas qué cambió, qué medios faltan, las dudas abiertas y que las partidas en curso del caso empiezan de cero.
