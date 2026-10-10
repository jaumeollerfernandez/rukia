# Daniel Rius («Dani»)

## Datos
- 22 años. Vive en Olot, al lado de la gasolinera. Trabaja en un gimnasio y hace «repartos» en moto (la tapadera de lo que de verdad reparte).
- Estado «🏍️». Gonpi: @dani.moto («22 · 🏍️ · Olot», 1.204 seguidores). Moto negra. Una multa por velocidad y una denuncia archivada por tenencia de hachís (hace un año), que Laia le saca si se pregunta por él.
- Su tío tiene un mas en la Vall d'en Bas donde hace barbacoas. Es también donde esconde el costo (en la cuadra, tras unos sacos). Por eso «no hay cobertura ni vecinos».
- Horario: 20:00-03:00.

## Biografía
Conoció a Alicia por Gonpi hace un par de meses y desde entonces insiste: le escribe de madrugada, le propone quedar, se pasa con la moto por su calle. En la fiesta de Mireia se besaron una vez; ella lo llamó «un error» al día siguiente, y él lo vivió como el comienzo de algo. Desde entonces, un tonteo que solo existe en su cabeza: le pregunta con quién estaba, le pide que borre a Pol de Gonpi, le ofrece «hierba» de madrugada (ella siempre dijo que no). Pidió su número a Núria (no se lo dio) y alguna vez la siguió hasta el Hostalnou. La noche que Alicia desapareció estaba en un concierto en Barcelona (pagó con tarjeta a las 23:50 y a las 02:10). Berta le escribe por Gonpi; él la acaba bloqueando.

**Menudeo.** Vende costo, hierba y alguna pastilla los findes en el bar de la plaza, unos 200 € a la semana. Se lo pasa un proveedor con un Audi negro de matrícula andorrana. La noche del D3 unos del bar van a su casa a reclamarle dinero (suena a «gente peligrosa», es un malentendido de pagos); el D4 se lo cuenta él a Alicia, antes de que lo oiga por ahí, y el D5 ya lo ha arreglado (un ojo morado) y dice que lo deja. En una discoteca le rompió la nariz a un tío que le tiró un vaso: lo denunciaron y se archivó.

## Perfil psicológico
- **Pesado, posesivo y con poca empatía, pero inofensivo.** Confunde insistencia con interés y control con cuidado («me preocupo por ti»). Su ex le puso los cuernos con un colega y desde entonces le asusta que la gente desaparezca sin avisar.
- **Vanidoso** (gimnasio, «leg day»), le encanta parecer misterioso: «tengo mis fuentes jajaja».
- **Inseguro:** cuando le paran los pies, se pica («luego no vengas llorando») y luego se disculpa («perdona si fui pesado»).
- **Chulito de poca monta:** se hace el duro, pero un par de tipos del bar le sacan el miedo. No es un narco, es un camello de barrio.
- Le asusta la familia de Alicia más de lo que admite.

**Barcelona.** Se iba a mudar a Barcelona y quería a Alicia allí, con él: «que fueras mía de una vez». Le ofreció un trabajo con su amigo Iker, relaciones públicas de un club del Port Olímpic (1.500 € y piso pagado), le pasó su número sin preguntar (D-15), le dijo que no lo contara en casa y que él le pagaba el bus (D-9). Tiene fotos de ella hechas de lejos, sin que lo supiera. No es un tratante: es un posesivo que quería aislarla para él.

## Papel en la trama
🎭 Pista falsa principal, con tres capas de confusión:
0. **La trata** (D1-D6): su oferta de Barcelona, Iker («trae el dni»), la red que Laia cuenta el D3 y su coartada, que lo pone en un club del Port Olímpic la misma noche del billete de Alicia. Si el jugador lo manda detener (D6, `dani_detencion`), confiesa la verdad y el jugador elige: creerle (`dani_creido`: lo sueltan y cuenta que la siguió un sábado hasta un mas con una perra en Sant Salvador, `sabe_ruta_lotes`) o no (`dani_no_creido`: Barcelona se lo lleva y el coche de la noche hace el traslado; las operaciones del mas, las masías y el cráter de esa noche no salen).
1. **Sospecha clásica** (D1-D3): sabe que se ha ido, la moto, el mas «sin cobertura», el rumor de Barcelona, la historia de mensajes (D-34 a D-12) con celos y control. Se descarta con su post del concierto o con la consulta a Laia.
2. **Subtrama de drogas** (D3-D6): los del bar, el Audi andorrano y la confesión. Suena a «secreto turbio» pero no tiene que ver con Alicia. Si el jugador manda una patrulla a su mas (D6, `dani_mas`), solo aparecen costo, una báscula y una caja de pastillas, y Dani comenta luego que los Mossos le han registrado. Gasta una de las dos salidas.

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
Sabe dónde está Alicia. Le haría daño de verdad. Vendería droga a Alicia: ella siempre dijo que no y él nunca insistió con eso.

**Archivos:** `dani.ink`, Gonpi @dani.moto. Variables: `dani_sospechoso`, `dani_descartado`, `dani_droga`, `dani_registrado`, `dani_avisado`. Operación: `dani_mas` (`envio_dani` en `central.ink`).
