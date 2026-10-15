// Pep, de la coral. Bromista. Vio a Montse y a Berta vestidas de blanco. Fue cartero del valle cuarenta años: conoce
// cada mas. Pista menor: preguntado por los mases, recuerda que arriba quedan viudas de las de antes que ya no bajan.
// No da nombres ni el sitio exacto.
// Vida propia: la rodilla (le operan en otoño), el huerto, la Nati (su mujer) y la coral sin ensayo esta semana.
// Escribe él solo dos veces: el D3, cuando se cancela el ensayo, y el D5, si Alicia le habló del aniversario de la yaya.

=== pep ===
-> charla

= charla
{pep_escribe() > 0 and hora >= pep_escribe():
    {dia == 3: -> sin_ensayo ->}
    {dia == 5: -> cancion ->}
}
- (opciones)
#caduca: {caduca(pep_escribe())}
* [pep 😂]
    ¡La contralto perdida! La Roser está insoportable sin ti. #delay: 600
    #caduca: D{dia + 1} 00:00
    ** [jajaja qué ha hecho ahora?]
        Se pone a mi lado para «tirar de las contraltos». Tira de mi oreja, eso es lo que hace. #delay: 120
        Canta tan fuerte que el Xavier ya ni la mira. Le tiene miedo. #delay: 8
    ** [dile que vuelvo pronto]
        ¿Pronto cuándo? Que me lo preguntará, y si le digo «pronto» me hace un interrogatorio. #delay: 120
        #caduca: D{dia + 1} 00:00
        *** [no sé. cuando esté mejor]
            Pues «cuando esté mejor». Eso se lo digo. Y que no pregunte más. #delay: 120
            Cuídate, nena. #delay: 6
        *** [mejor no le digas nada]
            Mejor. Con la Roser, cuanto menos, mejor 😅 #delay: 120
        *** [(sin responder)]
    ** [(sin responder)]
    - - -> opciones
* (madre) {dia >= 2 and dia < 7} [pep, has visto a mi madre?]
    A misa no viene desde hace un año. #delay: 600
    La vi en el mercado con tu hermana. Las dos de blanco, de arriba abajo. Parecían novias. #delay: 6
    Uy, perdona, nena. #delay: 20
    Oye, ¿y me lo preguntas a mí? Si vivís juntas. #delay: 15
    #caduca: D{dia + 1} 00:00
    ** (aniversario) [es por la yaya. se acerca lo de los tres años]
        Tres años ya... #delay: 120
        Tu yaya cantaba fatal y lo sabía. Y fundó la coral igual, con dos amigas. Eso es tener valor. #delay: 10
        Si tu madre quiere ir de blanco, que vaya. Cada uno lo lleva como puede. #delay: 8
    ** [es que casi no nos vemos]
        Ya. #delay: 120
        Hay madres que se van lejos sin moverse de casa. La mía también tuvo una época. #delay: 10
        Luego volvió. No sufras. #delay: 6
    ** [nada, era por saber]
        Por saber. Pues ya sabes lo que yo: blanco y mercado 😅 #delay: 120
    ** [(sin responder)]
    - - -> opciones
* (chiste) [pep cuéntame un chiste]
    ¿Qué le dice un cartero a otro? «Te veo en el buzón.» #delay: 600
    Cuarenta años repartiendo y es el mejor que tengo. Por eso me jubilé. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [pep qué malo 😂]
        Malo no. Clásico. #delay: 120
    ** [otro]
        Otro no tengo. Bueno, sí, pero es peor y la Nati me tiene prohibido contarlo. #delay: 120
    ** [(sin responder)]
    - - -> opciones
* (mases) {dia >= 2} [pep, de verdad conoces todos los mases del valle?]
    ¡Todos! Y sus perros. Me han mordido en once mases distintos. Tengo las cicatrices numeradas. #delay: 600
    El peor, el de Can Pericot, cuando vivía la Pepita. Un mastín que parecía un ternero. #delay: 6
    Ahora está vacío. Sin Pepita y sin perro. #delay: 5
    #caduca: D{dia + 1} 00:00
    ** [y te siguen dando miedo los perros?]
        ¿Miedo? Respeto. #delay: 120
        Bueno, miedo. A los grandes. A los pequeños les hablo y se callan. #delay: 8
    ** [y quién vive ahora allí arriba?]
        Cuatro gatos. Un holandés que hace queso, los de las vacas... #delay: 120
        Y alguna viuda de las de antes, de las que ya no bajan ni a misa. #delay: 8
        ¿Tú no subías de pequeña con tu yaya? Ella sí que se los sabía. #delay: 6
        #caduca: D{dia + 1} 00:00
        *** [sí. a casa de una amiga suya]
            Pues esa casa la sabrás tú mejor que yo. Yo solo sabía dónde mordían los perros 😅 #delay: 120
        *** [no me acuerdo de mucho]
            Normal. Eras un piojo. Tu yaya te subía en la mochila. #delay: 120
        *** [(sin responder)]
    ** [(sin responder)]
    - - -> opciones
* {dia >= 4 and dia < 7} [pep, la roser qué dice de mí?]
    ¡Que te has ido con un novio a Barcelona! La Roser tiene más imaginación que un culebrón. #delay: 600
    No le hagas caso. Ni le escribas, que se lo cuenta todo a tu madre. #delay: 6
    #caduca: D{dia + 1} 00:00
    ** [y tú qué crees?]
        ¿Yo? Que si te has ido, tendrás tus motivos. #delay: 120
        Y que esto es un pueblo: si no hay noticia, se la inventan. #delay: 8
    ** [dile que no es verdad]
        ¿A la Roser? Antes convenzo a un mastín. #delay: 120
        Déjala, que se aburre. #delay: 6
    ** [(sin responder)]
    - - -> opciones
* {dia >= 3} [pep qué tal la rodilla?]
    Mal, gracias por preguntar. #delay: 600
    Me operan después del verano. Hasta entonces, huerto sentado y escaleras de una en una. #delay: 8
    {pep.charla.mases:
        Por eso ya no subo a los mases. Antes me los hacía en un día. #delay: 6
    }
    -> opciones
// Invisible: la app la elige cuando algo de fuera (una foto, una ficha) puede abrir preguntas nuevas.
+ [(actualizar)]
    -> opciones
+ [(sin responder)]
    -> charla

// D3, cuando Xavier cancela el ensayo (coral.ink, 16:00).
= sin_ensayo
Sin ensayo y sin contralto. Esta semana me aburro hasta yo. #at: D3 17:40
{pep.charla.madre:
    Oye, lo de tu madre no lo he contado, eh. Que la Roser ya va sola. #delay: 8
}
¿Tú estás bien, nena? Que en el grupo no dices nada. #delay: 6
#caduca: D{dia + 1} 00:00
* [sí pep, tranquilo. solo es una mala semana]
    Las malas semanas se pasan cantando. O comiendo. Yo hago las dos. #delay: 600
* [no mucho la verdad]
    Pues eso no se arregla por el móvil. #delay: 600
    Si quieres un café, ya sabes dónde vivo. El de la puerta con la herradura. #delay: 8
    Y si no quieres, también. #delay: 5
* [(sin responder)]
- ->->

// D5, solo si Alicia le dijo que el blanco era por la yaya.
= cancion
He pensado en lo que me dijiste de tu yaya. #at: D5 11:20
Le he dicho al Xavier que en la misa le cantemos algo. El «Cant dels ocells», que era el suyo. #delay: 10
Si no puedes venir, no pasa nada. Ya cantaremos por ti. #delay: 6
#caduca: D{dia + 1} 00:00
* [gracias pep. de verdad]
    Nada, nena. Eso es lo que hace una coral. #delay: 600
* [(sin responder)]
- ->->

// La hora de hoy a la que Pep escribe por su cuenta (0: hoy no escribe).
=== function pep_escribe() ===
{
- dia == 3 and not pep.sin_ensayo:
    ~ return 17
- dia == 5 and pep.charla.aniversario and not pep.cancion:
    ~ return 11
- else:
    ~ return 0
}
