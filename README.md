# kimo

Chat-based game for Android (Kotlin + Jetpack Compose). The player is Kimo, a renowned hacker from the CNI's elite, the police's last hope in cases nobody else can solve: each case hands them someone's phone. ("rukia" was the working name; the code still uses it in package names.)

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
- **Case time.** A case runs on its own calendar: D1 is the day the player first opens it, or the next day if it's opened from 14:00 on (`CaseClock.LATE_START_HOUR`), so a late start doesn't deliver the whole day at once (saved, and started over by "Reset chats" or a wrong answer); D2 is the next day, and D0, D-1... the days before. On a late start the evening is D0 and the story's `dia` is 0, so a chat can introduce the case that night (`{dia < 1: -> d0}`, as Laia does in RUK-93429049). `#at: D3 22:15` makes a line arrive at that moment instead of after a delay. The story pauses on that line until then, so whatever comes after it (conditions, variables set by other chats) is decided at that moment, not in advance. A line with a time that has already passed during the case arrives right away; one before D1 keeps its date and time, which is how a chat starts with the phone's history (`ok #from: me #at: D-2 18:20` is an old message the player "sent"). `./gradlew testDebugUnitTest` checks every `#at` and `#caduca` in every story.
- **Choices that expire.** Put `#caduca: D2 08:00` on the line just before some choices and add a choice `* [(sin responder)]`. That choice is never shown; if the player hasn't picked one by then, the story takes it on its own and nothing is sent, so an unanswered chat never blocks the rest of the week.
- **Online hours.** `"hours": "08:00-15:00,20:00-23:00"` on a character (a range may cross midnight, `"23:00-01:00"`) decides when they show as online, and when they answer: a reply to the player outside those hours waits until the next range starts. Lines with `#at` ignore it. To make someone sometimes not answer at all, use ink's randomness (`{~...}`, `RANDOM`).
- **Story variables the phone sets.** If `main.ink` declares them, `VAR dia` and `VAR hora` are kept at the current case day (1 = D1) and hour before every step, and `VAR caso_resuelto` becomes `true` when the player solves the case in the Police app.
- `#image: photos/x.jpg` on a line shows that picture (from the case's `media/`) in the bubble, above the line's text. Until the file exists, only the text shows.
- `#effect: <name>` on a line plays a screen effect when the line arrives, over whatever the player is looking at (home screen or any app), even if they never open the chat; if the game was closed, it plays when the case is opened. Each effect plays once. Available: `hacked` (the phone gets taken over: scrolling code and a warning, ~5 s), `glitch` (a short burst of interference, ~1 s), `blackout` (the screen dies for a few seconds). Combine with other tags: `Hello. #delay: 60 #effect: hacked`. To add an effect, write a composable in `app/src/main/java/com/rukia/phone/Effects.kt` and list it in `effects` under its name; a test fails if a story uses a name that isn't there.
- You can write and play-test the `.ink` files in [Inky](https://github.com/inkle/inky) before running the app. The app compiles them when a chat is first opened.
- `./gradlew testDebugUnitTest` compiles the story files, so ink errors and chats with no matching knot fail the tests.
- [INK_CHEATSHEET.md](INK_CHEATSHEET.md) has the ink syntax you need, with examples for this game.

**`police/case.json`**: the final question of the case, shown by "Solve the case" in the Police Department app. `answer` is the `id` of the correct option. Each option has a `label`, an `image` (path inside `media/`) and a `color` used for the placeholder tile while the image is missing. The player gets one try: a wrong answer is game over and erases the save. An optional `"deadline": "D7 06:30"` (case time) closes the case: after it, it can no longer be solved.

**`police/actions.json`** (optional): field operations in the Police app. `squads` are groups of officers, each available once between two case times; `operations` are where they can be sent. Sending a squad plays the operation's ink `knot` in the chat `channel` (a chat in `chats/`), which is where the officers' report arrives. A report is ordinary ink: lines with `#delay` for the time it takes them to get there, and variables it sets (`~ patrulla_en_mas = true`) for the rest of the story. `type` is `"patrol"` or `"inspect"` (only the icon changes), and `squads` limits an operation to some squads. The tests check that every knot exists.

```json
{ "channel": "central",
  "squads": [ { "id": "manana", "label": "Two officers and a car", "from": "D6 08:00", "until": "D6 12:00" } ],
  "operations": [ { "id": "farm", "label": "The farm with the blue door", "knot": "envio_mas", "type": "patrol" } ] }
``` The app's Evidence section lists every call the player answered that had audio (`#call: audio/x.m4a`), newest first, to listen to again.

```json
{ "question": "Who entered the lab after midnight?", "answer": "uryu",
  "options": [ { "id": "uryu", "label": "Uryu", "image": "photos/uryu.jpg", "color": "#1E88E5" } ] }
```

Characters can also have a `photo` (path inside `media/`), used as their profile picture in chats. Without the file, the avatar shows their initial. `"hidden": true` keeps a character out of the Contacts tab (an unknown number, a hacker): they can still write to the player from a chat in `chats/`.

**`media/`**: audio, photos and videos used by the story, in `audio/`, `photos/` and `videos/`. Refer to files by their path inside `media/` (for example `photos/station.jpg`), and use lowercase names without spaces. Media used by every case (a ringtone, app sounds) can go once in `assets/shared/media/` instead: a path is looked up in the case's `media/` first, then in the shared one. Recommended formats: `.ogg` or `.mp3` for audio, `.jpg` or `.webp` for photos, `.mp4` (H.264) for video. Large videos make the APK bigger, so keep them short and compressed.

**`gonpi/gonpi.json`**: the Gonpi app, the phone's Instagram. `accounts` lists everyone on it, contacts or strangers: an `id`, a `username`, and optionally `name`, `photo` (path inside `media/`; without it the avatar shows the initial in `color`), `bio`, `followers` and `following`. `posts` show in the feed in file order, so put the newest first. Each has an `author` (an account id), an `image` (path inside `media/`), and optionally a `caption`, a `time` shown as written ("2 hours ago"), `likes` and `comments` (`{ "author": "<account id>", "text": "..." }`). Tapping a username opens that account's profile with all its posts, so strangers who only comment can hide clues too. A post or comment with `"at": "D3 21:00"` (case time) only appears from then on; without it, it's there from the start. Without the file, Gonpi is empty.

**Placeholder media.** `tools/placeholders.ps1 -Case app\src\main\assets\cases\<caseId>` (Windows PowerShell, needs ffmpeg) creates a provisional file for everything a case's `MEDIA_PENDIENTE.md` lists: a colored card with the file's path and description for each image, and the call's script read by the system's Spanish voice for each audio. It never overwrites a file, so it only fills in what's missing; replace a placeholder by saving the real file under the same name.

**`multimedia/`**: the player's photo gallery, shown by the Multimedia app. Every image dropped here (`.jpg`, `.png`, `.webp`, ...) appears in the grid, sorted by file name; tapping one opens it full screen. No list to edit: add or remove files and rebuild.

## Saving

Story files are read-only. Each case saves its progress separately, under `files/cases/<caseId>/chat/` in the app's internal storage: `chats/<id>.json` holds each chat's messages and current choices, and `story-state.json` holds the ink state (position in every chat plus all variables). A save can only be resumed with the same story: when a case's `.ink` files change, its saved progress is started over the next time it's opened (`ChatModule` compares a fingerprint of the story with the one saved), because ink can't reliably pick up a save from different files. To restart a case, open the Police Department app and tap "Reset chats": it wipes that case's chats and the week starts again (D1, without the opening notifications) the next time the case is opened. Uninstalling the app or clearing its data resets every case.

**Debug cases.** A case whose id starts with `debug-` (e.g. `debug-ruk-93429049`, "DEBUG:RUK-93429049") plays the content of the case it copies with its own save, never sends notifications, and has a yellow time bar on top: "Next" jumps to the story's next event (a timed line or choices expiring), the others move the clock ahead (+10m, +1h, +6h, next 08:00). The skip stops at every event on the way, so lines arrive and choices expire in order. The clock only moves forward; "Reset chats" puts it back to real time. To add one, list it in `game/Cases.kt`.

## Build

`./gradlew assembleDebug`, or open the folder in Android Studio.

## Architecture

`com.rukia.game` is the outer layer: `MainActivity` shows the title screen (`TitleScreen`, with the case list in `cases`) and, once a case is picked, the phone. Sign-in is faked as already done for now.

The phone is simulated by `com.rukia.phone`, the "OS": `PhoneScreen`, the home screen (`LauncherScreen`) and `installedApps`, the list of apps on the phone. To add an app, add a `PhoneApp` entry there whose `content` is the app's root composable.

Each phone app is its own hexagon in its own package, with its saves in `files/<app>/`. The chat app lives in `com.rukia.chat`, with `infrastructure/ChatModule.kt` as its composition root and `infrastructure/ui/ChatApp.kt` as its entry screen. There is one `ChatModule` per case (`ChatModule.of`), shared by the screens, the notification jobs and the story clock, so they never hold different copies of the story state.

The story keeps time on its own: while a case is open, `PhoneScreen` advances every chat every 10 seconds (timed lines, expired choices, new chats), and each notification job advances them too, so the week goes on with the game closed. `CaseClock` (in `com.rukia.phone`) holds the case's D1 and turns case times like "D3 22:15" into real ones for the chat, Gonpi and Police apps.

Apps never import each other. When one needs another's feature, the phone passes it in from `installedApps`: for example the Police Department app (`com.rukia.police`) gets a reset action that calls the chat app's `ResetProgress` use case.

Inside an app (here `app/src/main/java/com/rukia/chat/`):

- `domain/`: pure Kotlin. `model/` holds `Chat`, `Message` and `Character`. `port/` holds the interfaces the app depends on (repositories and `StoryEngine`).
- `application/`: one class per use case (`ListChats`, `AdvanceChat`, `ChooseReply`, `CreateChat`, `DeleteChat`, `GetCharacters`, `GetProfile`, `UpdateProfile`). They only talk to ports.
- `infrastructure/`: Android-specific code. `persistence/` has the adapters that implement the ports (JSON files, and `InkStoryEngine` for ink). `ui/` has the Compose screens.

Dependencies only point inward: infrastructure → application → domain.

Use cases are tested on the JVM with in-memory fakes: `./gradlew testDebugUnitTest`.
