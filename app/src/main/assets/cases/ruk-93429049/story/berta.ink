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
    Sé que tienes el teléfono en casa, pero es la única esperanza de que alguien lea esto. Desde que te has ido lloro todas las noches pensando en dónde estarás. #delay: 30
}
Buenas noches 🌹 #delay: 15 #caduca: D3 08:00
* [berta, estoy bien no te preocupes, volvere pronto]
    ~ sospecha_familia += 1
    Quien eres. #delay: 20
    Así que sí lo lee alguien. Donde tienes a mi hermana, dimelo y no habrán cargos contra tí. #delay: 5
* [(sin responder)]
- -> d3

= d3
{sospecha_familia >= 3:
    Tienes 24 horas para devolverme a mi hermana. #at: D3 07:05
    No la toques, ni se te ocurra hacerle nada. Iré a por ti, seas quien seas. #delay: 10
}
-> d4

= d4
{sospecha_familia >= 3:
    Sigues ahí. Lo noto. #at: D4 07:05
}
-> d5

= d5
{sospecha_familia >= 3:
    Alguien está usando tu móvil. Sé que no eres tú. Devuelvela sana y salva, te daremos todo lo que quieras, lo que necesites. Por favor almenos di algo, dime donde esta  #at: D5 22:50 
- else:
    Rezo para que cada día estes bien. Me duele el alma no saber donde estás o si estás bien. Quiero que vuelvas a casa, te necesito en casa. #at: D5 22:50
}
Tengo que llamarte, tengo que intentarlo #delay: 10 #call: audio/berta.m4a
🌹 #delay: 60
-> d6

= d6
{sospecha_familia >= 3:
    Por dios, devuélveme a mi hermana. Dime algo. #at: D6 07:05
    Que es lo que quieres? dinero? que te hemos hecho. Dinos dónde esta #delay:10
- else:
    Mañana te encontraré hermana. Estés donde estés, sé que te encontraré. #at: D6 07:05
}
-> d7

= d7
{familia_salvada():
    Sólo quería ascender, dejar el dolor y el sufrimiento de este mundo terrenal. #at: D7 07:10
    Quien te has creído para robarme ese destino, robarme estar en calma con mis seres queridos.#delay:5
    Eres escoria, todo ha sido culpa tuya. No me lo merezco... #delay:10
}
-> DONE
