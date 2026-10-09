// Canal de la Central (comisaría de Olot). Aquí llegan los informes de los agentes que el jugador manda desde la app de Policía.
// Cada operación de police/actions.json reproduce uno de estos knots en este chat (el que indica su "knot").
// Los informes tardan lo que tardaría el coche en llegar: #delay en segundos.

=== central ===
📻 Canal de la Central de Olot. Aquí llegarán los informes de los agentes que mandes. #at: D6 08:00
-> DONE

=== envio_estacion ===
Unidad en camino a la estación de autobuses de Olot. #delay: 5
En la estación. Revisamos andenes, consigna y la cafetería. #delay: 900
Nadie la ha visto desde aquella noche. El de la taquilla se acuerda de ella: compró y no subió. #delay: 1200
Sin novedad. Volvemos. #delay: 30
-> DONE

=== envio_pol ===
Unidad en camino a casa de Pol Casals, en Olot. #delay: 5
Pol estaba en casa. Ha colaborado. Nervioso, pero limpio. #delay: 1500
La llevó a la estación aquella noche y no volvió a verla. Ella no está aquí. #delay: 20
-> DONE

=== envio_girona ===
Pedimos a Girona que pasen por el piso de Núria Gil. #delay: 5
Compañeros de Girona: el piso está vacío, Núria está en clase. Las compañeras de piso no han visto a Alicia. #delay: 2400
-> DONE

=== envio_barcelona ===
Pedimos a Barcelona que pasen por casa del padre, Jordi Serra. #delay: 5
Compañeros de Barcelona: no hay nadie. La vecina dice que el padre se ha ido esta mañana hacia Olot. #delay: 3000
De la chica, nada. Por allí no ha aparecido. #delay: 10
-> DONE

=== envio_crater ===
Unidad en camino al cráter de Santa Margarida. #delay: 5
En el cráter. Solo está la ermita y dos excursionistas. #delay: 1800
Hay marcas recientes de algo pesado arrastrado hasta la ermita. Y cera de vela en el suelo. #delay: 120
Alguien está preparando algo aquí. #delay: 10
-> DONE

=== envio_masias ===
Unidad en camino a Sant Salvador de Bianya. Batida por las masías de la subida a Bracons. #delay: 5
Hemos llamado a cuatro puertas de once. Caminos de tierra, perros, nadie sabe nada. #delay: 3600
En una, una señora mayor no ha querido ni abrir. Tenía una perra que no paraba de ladrar. #delay: 60
Sin saber cuál es, así no llegamos. Volvemos. #delay: 20
-> DONE

=== envio_mas ===
~ patrulla_en_mas = true
Unidad en camino al Mas de la Rosalia, en la subida a Bracons. #delay: 5
Ya estamos allí. #delay: 2400
Puerta azul. Dintel de piedra de 1782. Un pozo delante. #delay: 5
Nos ha abierto una anciana, Rosalia Masó. Dice que vive sola con su perra. #delay: 8
Pero en la mesa hay dos tazas. Y en el banco de fuera, una chaqueta verde de chica. #delay: 6
De la chica, ni rastro. Se habrá ido al bosque al ver el coche. #delay: 5
Dejamos a un agente vigilando el camino. Si alguien sube, lo sabremos. #delay: 5
-> DONE

=== envio_vigilancia ===
~ vigilancia_crater = true
Recibido. Con lo de los papeles y el «tránsito», fiscalía nos da gente. #delay: 60
A las cinco estaremos en el cráter de Santa Margarida. Sin uniforme. La sargento Puig viene con nosotros. #delay: 10
-> DONE

// Pista falsa: el refugio del esplai. Alguien ha dormido allí, pero no Alicia (Biel y su novia).
=== envio_capsec ===
Unidad en camino a Can Pericot, en Capsec. #delay: 5
En el mas. Puerta azul, muy descolorida. Ni pozo ni perro: aquí no vive nadie. #delay: 2100
La puerta trasera está abierta. Dentro, un saco de dormir, latas de cerveza, colillas y velas gastadas. #delay: 60
Alguien ha dormido aquí hace poco. Dos personas, por las colillas. #delay: 10
En la pared, recién grabado con una navaja: «B + N». #delay: 8
En un armario, una mochila con mantas, latas de conserva y una linterna. Tiene polvo encima: nadie la ha tocado en semanas. #delay: 30
De la chica, nada. Volvemos. #delay: 20
-> DONE

// Pista falsa: lo que va diciendo Montse. Rosalia no ha estado nunca en una residencia.
=== envio_residencia ===
Unidad en camino a las residencias de Olot. #delay: 5
Residència Sant Jaume: ninguna Rosalia Masó. Nunca ha estado aquí. #delay: 1500
Las otras dos, igual. #delay: 600
{secta_sabe_rosalia:
    Una enfermera dice que hace unos días llamó un hombre preguntando lo mismo. Hablaba muy bajito, muy tranquilo. #delay: 8
}
-> DONE
