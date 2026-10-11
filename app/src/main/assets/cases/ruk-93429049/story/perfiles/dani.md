# Daniel Rius («Dani»)

## Datos
- 22 años. Vive en Olot, al lado de la gasolinera. Trabaja en un gimnasio y hace «repartos» en moto (la tapadera de lo que de verdad reparte).
- Estado «🏍️». Gonpi: @dani.moto («22 · 🏍️ · Olot», 1.204 seguidores). Moto negra. Una multa por velocidad y una denuncia archivada por tenencia de hachís (hace un año), que Laia le saca si se pregunta por él.
- Su tío tiene un mas en la Vall d'en Bas donde hace barbacoas. Es también donde esconde el costo (en la cuadra, tras unos sacos). Por eso «no hay cobertura ni vecinos».
- Horario: 20:00-03:00.

## Biografía
Conoció a Alicia por Gonpi hace un par de meses y desde entonces insiste: le escribe de madrugada, le propone quedar, se pasa con la moto por su calle. En la fiesta de Mireia se besaron una vez; ella lo llamó «un error» al día siguiente, y él lo vivió como el comienzo de algo. Desde entonces, un tonteo que solo existe en su cabeza: le pregunta con quién estaba, le pide que borre a Pol de Gonpi, le ofrece «hierba» de madrugada (ella siempre dijo que no). Pidió su número a Núria (no se lo dio) y alguna vez la siguió hasta el Hostalnou. La noche que Alicia desapareció estaba en un concierto en Barcelona (pagó con tarjeta a las 23:50 y a las 02:10). Berta le escribe por Gonpi; él la acaba bloqueando.

**Menudeo y deuda.** Vende costo, hierba y alguna pastilla los findes en el bar de la plaza, unos 200 € a la semana. Se lo pasa un proveedor con un Audi negro de matrícula andorrana, que trabaja para la parte de la red de trata de Barcelona que no cayó. Le fiaron un kilo y los Mossos se lo pillaron en un control: debe 6.000 €. La noche del D3 van a su casa a reclamárselo; el D4 se lo cuenta él a Alicia («me han dado dos días»). Acorralado, propone él mismo saldar la deuda con Alicia: Iker le presenta a los del Audi y les pasa sus fotos. El D5 tiene un ojo morado y «una salida». En una discoteca le rompió la nariz a un tío que le tiró un vaso: lo denunciaron y se archivó.

## Perfil psicológico
- **Pesado, posesivo y con poca empatía.** Hasta el D4, inofensivo; desde entonces, coaccionado y roto: no quiere hacerlo, pero le han dicho que si no paga lo matan, y se lo justifica («es mejor que te encuentre yo que otros»). Confunde insistencia con interés y control con cuidado («me preocupo por ti»). Su ex le puso los cuernos con un colega y desde entonces le asusta que la gente desaparezca sin avisar.
- **Vanidoso** (gimnasio, «leg day»), le encanta parecer misterioso: «tengo mis fuentes jajaja».
- **Inseguro:** cuando le paran los pies, se pica («luego no vengas llorando») y luego se disculpa («perdona si fui pesado»).
- **Chulito de poca monta:** se hace el duro, pero un par de tipos del bar le sacan el miedo. No es un narco, es un camello de barrio.
- Le asusta la familia de Alicia más de lo que admite.

**Barcelona.** Se iba a mudar a Barcelona y quería a Alicia allí, con él: «que fueras mía de una vez». Le ofreció un trabajo con su amigo Iker, relaciones públicas de un club del Port Olímpic (1.500 € y piso pagado), le pasó su número sin preguntar (D-15), le dijo que no lo contara en casa y que él le pagaba el bus (D-9). Tiene fotos de ella hechas de lejos, sin que lo supiera. Entonces no era un tratante: era un posesivo que quería aislarla para él. Desde el D4, por miedo, se convierte en uno.

## Papel en la trama
Primero 🎭 pista falsa; desde el D4, 🔎 amenaza real. Capas:
0. **La trata** (D1-D3, pista falsa): su oferta de Barcelona, Iker («trae el dni»), la red que Laia cuenta el D3 y su coartada, que lo pone en un club del Port Olímpic la noche del billete. No tiene que ver con que Alicia se fuera.
1. **Sospecha clásica** (D1-D3): sabe que se ha ido, la moto, el mas «sin cobertura», el rumor de Barcelona, la historia de mensajes (D-34 a D-12) con celos y control. Se descarta con su post del concierto o con la consulta a Laia.
2. **Drogas** (D3-D4): los del bar, el Audi andorrano (`sabe_audi`) y la confesión (`dani_droga`). La operación `dani_mas` (D6) solo encuentra hachís y una báscula (`dani_registrado`).
3. **La deuda: busca a Alicia para la red** (D4-D6). Pregunta por ella por todo el valle: Toni (Banc d'Aliments, D5 16:10), Pilar (D6 07:40) y Èric (D6 09:45) se lo cuentan a Alicia (`sabe_dani_busca`). Le escribe cada vez más desesperado (D5 23:15, D6 13:00: «sé que estás por sant salvador»). Si nadie la rescata y la familia no sabe dónde está, la entrega con dos hombres de la red en una furgoneta blanca el D6 a las 19:20, durante el apagón (`alicia_entregada`, finales 5 y 6), y desaparece: «perdóname. no tenía otra».
   - **Frenarlo** (`dani_frenado`) solo retrasa la entrega a D7 04:20: detenerlo desde la consulta de Laia (con `dani_droga` y `sabe_audi`, sin gastar salida), la operación `dani_detencion` del D6, o avisar a Alicia por el prepago (se esconde en el pozo cuando sube su moto). Con ese margen, la salida de la noche al mas aún la salva.
   - **Detenido** (operación del D6): miente («no sé dónde está»), pero en su móvil está el chat con Iker («si la encuentras, la deuda queda saldada»). El jugador elige si le cree: si le cree, lo sueltan y cuenta que la siguió hasta Sant Salvador (`sabe_ruta_lotes`); si no, Barcelona se lo lleva y el coche de la noche hace el traslado.

## Vida propia esta semana
- El gimnasio, la moto, la deuda de seis mil con los del Audi. El D5 tiene un ojo morado y «una salida».

## Cómo reacciona
- D4, tras confesar: si Alicia le dice que vaya a los Mossos, «y que me maten al salir?». Si le pregunta qué va a hacer, «tengo dos días y una idea de mierda».
- D6: si Alicia se niega a decirle dónde está, se le escapa la amenaza («tú lo has querido») y se corrige.

## El D1 y el D2
- El D1 pregunta si se ha ido a Barcelona: Alicia puede negarlo (él pregunta dónde está entonces), decir que no piensa escribir a Iker o preguntar quién se lo ha dicho. El D2 se le puede contestar a «qué pasa contigo?» y a «si estás con el pol ese».

## Cómo escribe
- **Minúscula, sin punto, abreviaturas**: «q haces», «barna».
- Emojis de chulito: 😏, 😂, 🙃.
- Abre con «ey» u «oye». Ironía pasivo-agresiva: «vale guay», «ya, liada».
- Mensajes nocturnos y espaciados.

Ejemplos reales:
> me dejas en visto desde hace tres días
> sí, me he enterado. olot es pequeño 😏
> tu familia es rara de cojones
> vendo un poco, ali. costo, hierba, alguna pastilla los findes en el bar
> yo no te controlaba. ...vale. un poco

## Gustos
Motos, gimnasio, conciertos, barbacoas en el mas del tío, gustar.

## Nunca
Le pone la mano encima a Alicia. Vendería droga a Alicia: ella siempre dijo que no y él nunca insistió con eso. Le cuenta a nadie lo de la deuda con la red (ni siquiera detenido: tiene más miedo de ellos que de los Mossos).

**Archivos:** `dani.ink`, Gonpi @dani.moto. Variables: `dani_sospechoso`, `dani_descartado`, `dani_droga`, `dani_registrado`, `dani_avisado`, `sabe_dani_busca`, `dani_frenado`, `alicia_entregada`. Operación: `dani_mas` (`envio_dani` en `central.ink`).
