# rukia

Chat-based game for Android (Kotlin + Jetpack Compose).

## Writing content

Each case has its own folder, `app/src/main/assets/cases/<caseId>/`, holding everything below. Cases never share characters, chats, story or saves. To add a case:

1. Copy an existing case folder to `assets/cases/<new-id>/` and rewrite its content. `assets/cases/test-case/` uses every feature (choices, shared variables, calls with audio, delayed messages, a group that appears a minute in, contacts, the police question), so it doubles as a working example.
2. Add a `GameCase("<new-id>", ...)` entry to `cases` in `app/src/main/java/com/rukia/game/Cases.kt`. It then appears on the title screen.
3. Run `./gradlew testDebugUnitTest`: it checks that every case has a folder, and that every story compiles and every chat and question file in every case loads.

The files inside a case folder:

**`characters.json`**: everyone who can chat.

```json
{ "id": "rukia", "name": "Rukia", "online": true, "status": "Busy", "color": "#7E57C2" }
```

**`chats/<id>.json`**: one file per conversation. Any file added here shows up in the chat list.

```json
{ "id": "group", "title": "Night Shift", "participants": ["ichigo", "orihime"] }
```

- `title` is optional; 1-to-1 chats use the character's name.
- To add a new attribute, add a field with a default value to the matching data class in `domain/model/`. Older JSON files keep working.

**`story/*.ink`**: what is said in each chat, written in [ink](https://github.com/inkle/ink/blob/master/Documentation/WritingWithInk.md). The player can't type: when the story offers choices, they appear as buttons, and the chosen one is sent as the player's message.

- `story/main.ink` is the entry point. It declares the shared variables and `INCLUDE`s the other files.
- Each chat is the knot named like its chat id (`=== rukia ===`). Every character in `characters.json` is listed in the Contacts tab; tapping one opens a 1-to-1 chat whose id is the character id, so give a contact a knot with that name to make them talk (one file per contact, e.g. `story/uryu.ink`, `INCLUDE`d from `main.ink`); without one, their chat opens empty. Start it with choices if the player writes first. Every chat keeps its own place in the story, and all chats share the variables, so a choice in one chat can change another.
- In 1-to-1 chats, lines are said by the other person. In group chats, mark each line with its speaker: `Hello. #from: ichigo`.
- `#delay: <seconds>` on a line makes it arrive that long after the previous line, in real time (`Okay, I'm back. #delay: 30`). Later lines and the choices wait for it. If the player isn't looking at that chat when it arrives, even with the game closed, a WhatsApp-style notification appears; tapping it opens the chat. A chat appears in the list only once its first line has arrived, so a delayed first line (`#delay: 60`) makes a chat or group show up a minute into the case.
- `#call` on a line makes its speaker call the player right after it (`Okay. Calling you now. #call`). The incoming-call screen rings until the player answers or declines, and the rest of the chat waits for it. Every call shows in the chat and in the Calls tab. `#call: audio/x.m4a` gives the call a voice clip (from the case's `media/`, or shared media): answering plays it on an in-call screen and hangs up when it ends. Declining, or a call without audio, just hangs up.
- You can write and play-test the `.ink` files in [Inky](https://github.com/inkle/inky) before running the app. The app compiles them when a chat is first opened.
- `./gradlew testDebugUnitTest` compiles the story files, so ink errors and chats with no matching knot fail the tests.
- [INK_CHEATSHEET.md](INK_CHEATSHEET.md) has the ink syntax you need, with examples for this game.

**`police/case.json`**: the final question of the case, shown by "Solve the case" in the Police Department app. `answer` is the `id` of the correct option. Each option has a `label`, an `image` (path inside `media/`) and a `color` used for the placeholder tile while the image is missing. The player gets one try: a wrong answer is game over and erases the save.

```json
{ "question": "Who entered the lab after midnight?", "answer": "uryu",
  "options": [ { "id": "uryu", "label": "Uryu", "image": "photos/uryu.jpg", "color": "#1E88E5" } ] }
```

Characters can also have a `photo` (path inside `media/`), used as their profile picture in chats. Without the file, the avatar shows their initial.

**`media/`**: audio, photos and videos used by the story, in `audio/`, `photos/` and `videos/`. Refer to files by their path inside `media/` (for example `photos/station.jpg`), and use lowercase names without spaces. Media used by every case (a ringtone, app sounds) can go once in `assets/shared/media/` instead: a path is looked up in the case's `media/` first, then in the shared one. Recommended formats: `.ogg` or `.mp3` for audio, `.jpg` or `.webp` for photos, `.mp4` (H.264) for video. Large videos make the APK bigger, so keep them short and compressed.

## Saving

Story files are read-only. Each case saves its progress separately, under `files/cases/<caseId>/chat/` in the app's internal storage: `chats/<id>.json` holds each chat's messages and current choices, and `story-state.json` holds the ink state (position in every chat plus all variables). Uninstall the app or clear its data to reset progress.

## Build

`./gradlew assembleDebug`, or open the folder in Android Studio.

## Architecture

`com.rukia.game` is the outer layer: `MainActivity` shows the title screen (`TitleScreen`, with the case list in `cases`) and, once a case is picked, the phone. Sign-in is faked as already done for now.

The phone is simulated by `com.rukia.phone`, the "OS": `PhoneScreen`, the home screen (`LauncherScreen`) and `installedApps`, the list of apps on the phone. To add an app, add a `PhoneApp` entry there whose `content` is the app's root composable.

Each phone app is its own hexagon in its own package, with its saves in `files/<app>/`. The chat app lives in `com.rukia.chat`, with `infrastructure/ChatModule.kt` as its composition root and `infrastructure/ui/ChatApp.kt` as its entry screen.

Apps never import each other. When one needs another's feature, the phone passes it in from `installedApps`: for example the Police Department app (`com.rukia.police`) gets a reset action that calls the chat app's `ResetProgress` use case.

Inside an app (here `app/src/main/java/com/rukia/chat/`):

- `domain/`: pure Kotlin. `model/` holds `Chat`, `Message` and `Character`. `port/` holds the interfaces the app depends on (repositories and `StoryEngine`).
- `application/`: one class per use case (`ListChats`, `AdvanceChat`, `ChooseReply`, `CreateChat`, `DeleteChat`, `GetCharacters`, `GetProfile`, `UpdateProfile`). They only talk to ports.
- `infrastructure/`: Android-specific code. `persistence/` has the adapters that implement the ports (JSON files, and `InkStoryEngine` for ink). `ui/` has the Compose screens.

Dependencies only point inward: infrastructure → application → domain.

Use cases are tested on the JVM with in-memory fakes: `./gradlew testDebugUnitTest`.
