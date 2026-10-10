// Berta, la hermana (24), por privado. Sabe que el móvil está en casa: escribe para ver si alguien lo lee.
// Contestarle confirma que el móvil está «vivo».

=== berta ===
-> historial

= historial
ali me coges el cargador del coche? #at: D-5 08:10
cógelo tú #from: me #at: D-5 08:40
Siempre igual 🙄 #at: D-5 08:41
-> d2

= d2
{sospecha_familia >= 2:
    Sé que no eres Ali. #at: D2 23:10
    El móvil de Ali está en casa. Y tú estás dentro. #delay: 8
    No sé quién eres. Pero sé que nos lees. #delay: 6
    Todo lo que se esconde acaba saliendo a la luz. #delay: 10
- else:
    Sé que vas a volver. #at: D2 23:10
    Siempre vuelves. #delay: 5
    De pequeña te escondías en el desván y bajabas en cuanto olías la cena. #delay: 20
    Esta vez es especial, Ali. Ya sabes qué día es. #delay: 8
    Tu móvil está en casa, por cierto. Si alguien lo está leyendo... ya veremos. #delay: 30
}
Buenas noches 🌹 #delay: 15 #caduca: D3 08:00
* [déjame en paz berta]
    ~ sospecha_familia += 1
    Vaya. #delay: 20
    Así que sí lo lee alguien. #delay: 5
* [(sin responder)]
- -> d3

= d3
{sospecha_familia >= 3:
    Hoy he visto salir el sol desde tu ventana. #at: D3 07:05
    Tu cama sigue hecha. Quien seas, no la toques. #delay: 10
}
-> d4

= d4
{sospecha_familia >= 3:
    Sigues ahí. Lo noto. #at: D4 07:05
}
-> d5

= d5
{sospecha_familia >= 3:
    Alguien está usando tu móvil. Sé que no eres tú. #at: D5 22:50 #effect: glitch
- else:
    Ali. #at: D5 22:50
}
Te llamo. #delay: 10 #call: audio/berta.m4a
🌹 #delay: 60
-> d6

= d6
{sospecha_familia >= 3:
    Te veo al amanecer, quien seas. #at: D6 07:05
- else:
    Mañana, Ali. #at: D6 07:05
}
-> d7

= d7
{familia_salvada():
    Nos han robado la luz. #at: D7 07:10
    Y ha sido culpa tuya, seas quien seas. #delay: 10
}
-> DONE
