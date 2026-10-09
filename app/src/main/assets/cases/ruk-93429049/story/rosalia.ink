// Rosalia, 78 años, desde el fijo del Mas de la Rosalia. No escribe: solo llama.
// Alicia ha salido con la perra y Rosalia, preocupada y algo confusa, marca el número de la nena que tiene apuntado en la nevera.
// Si el jugador no atiende la primera llamada (la rechaza o la deja sonar), vuelve a llamar a las 13:10: no se acuerda de que ya ha llamado. Si la atiende, no repite.

=== rosalia ===
📞 #at: D4 12:30 #call: audio/rosalia.m4a
#at: D4 13:10
{not llamada_rosalia_contestada:
    📞 #call: audio/rosalia.m4a
}
-> d5

= d5
// Rosalia no vuelve a llamar.
-> d6

= d6
// D6: nada.
-> d7

= d7
// D7: nada.
-> DONE
