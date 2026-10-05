// Entry point of the whole story.
// Each chat is a knot named exactly like its chat id: assets/chats/<id>.json for chats that exist from the start,
// or the character id for 1-to-1 chats the player starts from Contacts.
// Variables declared here are shared by every chat, so a choice in one chat can change another.

VAR trust_rukia = 0
VAR rukia_upset = false
VAR knows_about_key = false

INCLUDE group.ink
INCLUDE girls.ink

// One file per contact.
INCLUDE rukia.ink
INCLUDE ichigo.ink
INCLUDE orihime.ink
INCLUDE uryu.ink
INCLUDE renji.ink
INCLUDE chad.ink
INCLUDE kisuke.ink
INCLUDE yoruichi.ink
INCLUDE toshiro.ink
INCLUDE rangiku.ink
INCLUDE jaume.ink

-> DONE
