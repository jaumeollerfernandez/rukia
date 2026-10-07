// Grupo «Familia 🌅»: Montse (madre), Berta (hermana) y Alicia. Toda línea lleva #from.
// La madre tiene el móvil de Alicia en un cajón: si el jugador escribe aquí, lo sabrán.
// Alicia escribe en minúscula, sin punto final y con pocos emojis.
//
// Nadie dice «secta» aquí: el jugador lo deduce de los comportamientos típicos de un grupo coercitivo.
// - Líder al que se cita para todo por su nombre de pila: «Ignasi dice...». La madre no decide nada sin preguntarle.
// - Lenguaje propio con apariencia de bienestar: «la Casa», «el círculo», «compartir», «soltar», «resistencia», «la luz».
// - El Amanecer (la ceremonia) no se nombra nunca aquí: el jugador lo averigua por Laia, el canal de Rosa d'Abril, Ignasi, Gonpi, Iris y Alicia.
// - Privación: ayunos, meditación del alba a las 5:30, noches sin dormir que se llaman «limpieza».
// - Confesión en grupo («lo que has compartido hoy»), captación («traer a alguien de fuera»).
// - Aislamiento: la amiga «te carga», a la tía no se le abre, «la gente de fuera»; control de información.
// - Dinero como crecimiento personal («ordenar lo material», «soltar»), las «voluntades» firmadas.
// - Ante la desaparición, calma: nadie llora a Alicia, se la «espera».

=== familia ===
-> historial

= historial
Buenos días ☀️ Hoy hay ayuno en la Casa. Solo agua e infusión hasta la cena. #from: mama #at: D-6 07:45
Tú también, nena. Te irá bien para lo de la ansiedad. #from: mama #at: D-6 07:46
mamá tengo entreno, no puedo ir sin comer #from: me #at: D-6 08:30
Ignasi dice que el cuerpo aguanta mucho más de lo que creemos. La que se queja es la mente. #from: mama #at: D-6 08:31
Ali, no empieces 🙏 #from: berta #at: D-6 08:40
Nena, esta noche hay encuentro en la Casa. Vienes. #from: mama #at: D-4 19:40
mamá mañana tengo examen #from: me #at: D-4 19:52
Ignasi ha preguntado por ti. Dice que te nota lejos. #from: mama #at: D-4 19:53
Alicia, no le hagas esto a mamá. #from: berta #at: D-4 20:05
vale #from: me #at: D-4 20:31
Qué bonito el círculo de hoy ☀️ Gracias por venir, cariño. Ignasi está muy contento contigo. #from: mama #at: D-3 00:40
Ali, lo que has compartido hoy ha sido muy valiente 🌹 #from: berta #at: D-3 00:41
yo no he compartido nada. me lo habéis sacado entre todos #from: me #at: D-3 08:10
Eso que sientes se llama resistencia. Nos ha pasado a todas al principio. #from: berta #at: D-3 08:12
Mañana a las 5:30 meditación del alba. Te despierto yo. #from: mama #at: D-3 08:15
El jueves hay taller abierto de Rosa d'Abril. Cada una tiene que traer a alguien de fuera. #from: berta #at: D-2 09:12
Ali, tú podrías traer a la Nuri. Está muy cargada, le iría bien. #from: berta #at: D-2 09:13
ni de coña. a la nuri dejadla en paz #from: me #at: D-2 10:40
No le hables así a tu hermana. #from: mama #at: D-2 10:42
Ah, y llevad las libretas del banco. Ignasi nos ayuda a ordenar lo material 🙏 #from: berta #at: D-2 10:50
yo no tengo libreta #from: me #at: D-2 11:02
Tienes la cuenta que te abrimos. Mamá es cotitular. #from: berta #at: D-2 11:03
Ya lo hablaremos en casa. #from: mama #at: D-2 11:05
hoy ceno en casa de la nuri #from: me #at: D-1 18:20
Vale. Ignasi dice que esa chica te aleja de ti misma, pero bueno. No vuelvas tarde. #from: mama #at: D-1 18:24
me voy a dormir pronto, estoy reventada #from: me #at: D-1 22:31
Descansa 💛 Mañana a las 5:30, alba. #from: mama #at: D-1 22:33
-> d1

= d1
Berta, ¿sigues en la Casa? #from: mama #at: D1 21:02
Sí. Hemos hecho círculo por Ali. #from: berta #delay: 90
¿Y qué ha dicho Ignasi? #from: mama #delay: 20
Que no la busquemos con miedo. Que el miedo la aleja más. #from: berta #delay: 40
Que es una semilla que se ha escapado del cesto. Y que las semillas vuelven solas cuando tienen frío. #from: berta #delay: 8
Y que la familia tiene que estar completa. Las tres. #from: berta #delay: 5
Lo sé. #from: mama #delay: 60
Esta mañana fui a los Mossos. Ignasi dice que mejor que conste, por si alguien pregunta. #from: mama #delay: 10
De la Casa no he dicho nada. #from: mama #delay: 4
Bien. Lo de dentro se queda dentro. #from: berta #delay: 30
¿Has comido algo? #from: berta #delay: 20
Hoy ayuno. Por ella. #from: mama #delay: 40
Alicia, si algún día lees esto, vuelve a casa. Nadie está enfadado contigo. #from: mama #delay: 120 #caduca: D2 08:00
* [mamá estoy bien]
    -> escribe_en_familia ->
* [(sin responder)]
- -> d2

= d2
Berta, ya está hecho. Lo de la cuenta de la nena. #from: mama #at: D2 10:31
Gracias, mamá. Es lo mejor para ella, aunque ahora no lo vea. #from: berta #delay: 600
Ignasi dice que lo material era lo que la tenía atada ahí fuera. #from: berta #delay: 6
Me ha dado un poco de pena. Eran sus ahorros del trabajo. #from: mama #delay: 60
La pena también hay que soltarla 🙏 #from: berta #delay: 8
{sospecha_familia >= 2:
    ¿Y el móvil? #from: berta #delay: 30
    En la caja de metal de la cocina. Ya no lo dejo en el cajón. #from: mama #delay: 120
    Bien. Y no lo toques. Que crea que no lo miramos. #from: berta #delay: 10
}
{amigas.d1.vienen:
    Han venido sus amigas. Les he dicho que estaba de retiro. #from: mama #at: D2 18:55
    ¿Se lo han creído? #from: berta #delay: 300
    La rubia no. La Carla. #from: mama #delay: 60
    Normal. Hay gente que no está preparada. #from: berta #delay: 20
}
La tía Marta va diciendo cosas en el mercado. #from: mama #at: D2 20:15
Que diga. La gente de fuera siempre tiene miedo de lo que no entiende. #from: berta #delay: 300
Ignasi dice que no discutas con ella. Ni le cojas el teléfono. Te roba energía. #from: berta #delay: 10
Ya no se lo cojo. #from: mama #delay: 60
🌹 #from: mama #delay: 30 #caduca: D3 08:00
* [berta deja a mamá en paz]
    -> escribe_en_familia ->
* [(sin responder)]
- -> d3

// Si el jugador escribe en el grupo, la familia sabe que alguien usa el móvil. Se llama como túnel: -> escribe_en_familia ->
= escribe_en_familia
~ sospecha_familia += 2
{sospecha_familia <= 2:
    ¿Alicia? #from: mama #delay: 15
    Mamá. Su móvil está en el cajón de tu cuarto. #from: berta #delay: 10 #effect: glitch
    Ve a mirarlo. Ahora. #from: berta #delay: 3
    Está ahí. Con la pantalla apagada. #from: mama #delay: 90
    Entonces no ha sido ella. #from: berta #delay: 20
    Quien seas: sabemos que lees esto. #from: berta #delay: 8
- else:
    Otra vez. #from: berta #delay: 20 #effect: glitch
    Seas quien seas, nosotras también te leemos. #from: berta #delay: 6
    Ignasi ya lo sabe. #from: berta #delay: 4
}
->->

= d3
Buenos días ☀️ Gratitud por un día más. #from: berta #at: D3 07:00
Llevo tres noches sin dormir. #from: mama #at: D3 07:20
Es la limpieza, mamá. El cuerpo suelta así. Luego viene la calma. #from: berta #delay: 30
Ignasi quiere que hablemos con todo el que la conoce. Amigas, el chico ese, el trabajo. #from: berta #at: D3 12:30
Ya he llamado a todos. #from: mama #delay: 600
¿Y la gente de la yaya? Las del valle. #from: berta #delay: 60
¿La Rosalia? Está en una residencia de Olot desde hace años. Ni se acordará de la niña. #from: mama #delay: 300
Pues mejor. #from: berta #delay: 20
{laia.d2.a_barcelona or laia.d2.a_barcelona_sin:
    Jordi me ha llamado gritando. Que le han ido los Mossos a casa. #from: mama #at: D3 21:10
    Perfecto. Que la busquen en Barcelona. #from: berta #delay: 120
}
🌹 #from: berta #at: D3 22:00 #caduca: D4 08:00
* [la rosalia no está en ninguna residencia]
    -> escribe_en_familia ->
    ~ secta_sabe_rosalia = true
    ¿Cómo que no? #from: mama #delay: 60
    Mamá. Eso no lo ha escrito ningún policía. Eso lo sabe alguien que conoce a Ali. #from: berta #delay: 10
    Se lo cuento a Ignasi ahora mismo. #from: berta #delay: 5
* [(sin responder)]
- -> d4

= d4
Buenos días ☀️ Hoy, ayuno de palabras: solo lo necesario. #from: berta #at: D4 07:00
La Marta ha venido a casa. No le he abierto. #from: mama #at: D4 11:40
Bien hecho. #from: berta #delay: 600
Hoy he firmado las voluntades en el despacho de la Casa. #from: mama #at: D4 16:10
Yo las firmé en primavera. Ya verás qué ligera te sientes. #from: berta #delay: 300
Ignasi dice que la nena también las firmará cuando vuelva. #from: mama #delay: 60
{secta_sabe_rosalia:
    Ignasi ha llamado a la residencia de Olot. Allí no hay ninguna Rosalia. #from: berta #at: D4 19:20
    Entonces... #from: mama #delay: 60
    Entonces mañana subimos al valle. #from: berta #delay: 10
}
{not arnau_calla:
    ¿Has visto el vídeo que subió Arnau? El de la rotonda. #from: berta #at: D4 21:05
    ¿Qué vídeo? #from: mama #delay: 300
    Ali sale al fondo. La noche que se fue. Con una bolsa llena de pan. #from: berta #delay: 10
    ¿Pan? #from: mama #delay: 30
    Mañana voy a la panadería. #from: berta #delay: 10
}
🌹 #from: mama #at: D4 22:00
-> d5

= d5
Buenos días ☀️ Que hoy pese un poco menos. #from: berta #at: D5 07:00
{not arnau_calla:
    He ido a la panadería. #from: berta #at: D5 10:40
    La dueña no ha soltado nada. Pero el chico, el Èric, sí. #from: berta #delay: 30
    Dice que Ali subía pan a «la señora de arriba». #from: berta #delay: 5
    {not pol_calla:
        ~ secta_sabe_rosalia = true
        Andando hacia Bianya. Con pan para una señora de arriba. #from: berta #delay: 60
        Es la Rosalia, mamá. #from: berta #delay: 5
        Se lo digo a Ignasi. #from: berta #delay: 3
    }
}
¿Qué me pongo pasado mañana? #from: mama #at: D5 16:20
Blanco. Todas de blanco. #from: berta #delay: 300
Y la de Ali también. Ya la tengo planchada. #from: berta #delay: 10
Ignasi dice que no llevemos nada más. Ni bolso ni móvil. Que allí no nos hará falta nada. #from: berta #delay: 30
¿Ni las llaves de casa? #from: mama #delay: 120
Ni las llaves, mamá. #from: berta #delay: 10
{secta_sabe_rosalia:
    Mañana subimos a Sant Salvador con la furgoneta. #from: berta #at: D5 21:00
    Ignasi dice que la traeremos a casa a tiempo para la cena. #from: berta #delay: 10
}
Nena, mañana es la última noche. Si lees esto, ven a cenar a casa. #from: mama #at: D5 22:10 #caduca: D6 08:00
* [voy mamá]
    -> escribe_en_familia ->
    ¿Cuándo? #from: mama #delay: 30
    Mamá. No es ella. #from: berta #delay: 5
* [(sin responder)]
- -> d6

= d6
Mañana. #from: berta #at: D6 07:00
Mesa puesta. Tres platos. #from: mama #at: D6 13:00
{secta_sabe_rosalia:
    Subimos. #from: berta #at: D6 17:30
    {patrulla_en_mas:
        Hay un coche de los Mossos delante de la casa. #from: berta #at: D6 18:30
        Damos la vuelta. #from: berta #delay: 5
        Da igual. Ignasi dice que la luz la traerá sola. #from: berta #delay: 60
    - else:
        Puerta azul. Es aquí. #from: berta #at: D6 18:30
        La vieja dice que no sabe nada. #from: berta #delay: 600
        Pero en la cocina hay una mochila. Ali está en el bosque. #from: berta #delay: 10
        La esperamos aquí. Tiene que volver a por la mochila. #from: berta #delay: 30
    }
- else:
    Ignasi dice que no hace falta buscarla más. Que la luz la traerá sola. #from: berta #at: D6 21:00
}
Nena. Te esperamos. #from: mama #at: D6 23:00
-> d7

= d7
Hoy. #from: berta #at: D7 05:00
Ya vamos. Todas de blanco. #from: mama #at: D7 05:30
{capturada():
    Ali viene con nosotras. 🌹 #from: berta #at: D7 05:31
}
{
- capturada() and not familia_salvada():
    Ya estamos todas. #from: berta #at: D7 06:30 #effect: hacked
- familia_salvada():
    Nena. Perdóname. #from: mama #at: D7 11:20
    No sé qué he hecho. No sé cómo he llegado hasta aquí. #from: mama #delay: 60
}
-> DONE
