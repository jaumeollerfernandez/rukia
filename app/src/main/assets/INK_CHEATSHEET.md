# Ink cheatsheet

Everything you need to write chats for this game. Full ink manual: [Writing with ink](https://github.com/inkle/ink/blob/master/Documentation/WritingWithInk.md). Try scripts in [Inky](https://github.com/inkle/inky) before running the app.

Story files live in `app/src/main/assets/cases/<caseId>/story/`. After editing, run `./gradlew testDebugUnitTest` to catch errors.

## How the game reads ink

| In ink | In the game |
|---|---|
| A knot `=== rukia ===` | The chat whose id is `rukia` |
| A line of text | A message from the other person (or from `#from:` in groups) |
| A choice `* [Text]` | A button; tapping it sends "Text" as your message |
| `-> DONE` | The chat stops and waits (nothing more to say for now) |
| `VAR` in `main.ink` | Shared by all chats of the case |

Each chat keeps its own place in the story. Its lines play when the case starts, when it's opened, and after each choice.

## Files

```ink
// main.ink — the entry point
VAR trust = 0
VAR knows_key = false

INCLUDE rukia.ink
INCLUDE group.ink

-> DONE
```

One file per chat is easiest. Each file is `INCLUDE`d from `main.ink`.

## Creating a chat

1. **Knot**: write `=== <id> ===` in a `.ink` file. Ids use only letters, digits and `_` (no `-` or spaces).
2. **Which chat it is**:
   - A 1-to-1 chat with a contact: use the character's `id` from `characters.json`. It starts when the player taps the contact.
   - A chat that exists from the start, or a group: also add `chats/<id>.json`:
     ```json
     { "id": "night_shift", "title": "Night Shift", "participants": ["ichigo", "orihime"] }
     ```

## Lines

```ink
=== rukia ===
Hey.
Are you awake?
-> DONE
```

Every non-empty line is one message. Empty lines are ignored.

## Choices

```ink
=== rukia ===
I need to tell you something.
* [What is it?]
    It's about the station.
* [Not now.]
    Fine. Later then.
- Anyway, be careful.
-> DONE
```

- **Always wrap the choice text in `[ ]`.** Text outside the brackets is printed again as a message from the *other* person.
- `*` choices disappear once picked. `+` choices (sticky) stay available, which is good for loops.
- Indented lines under a choice are that branch.
- `-` (a **gather**) is where the branches meet again.

### Nested choices

```ink
* [Who was there?]
    Promise you won't tell?
    ** [I promise.]
        It was Uryu.
    ** [I can't promise that.]
        Then I can't tell you.
- -> DONE
```

### Looping back (questions the player can keep asking)

```ink
=== kisuke ===
What do you want to know?
- (ask)
* [About the lab.]
    It was locked at 10.
    -> ask
* [About the van.]
    Never seen it.
    -> ask
+ [That's all.]
    Take care.
    -> DONE
```

`(ask)` labels the gather so `-> ask` can jump back to it. Once both `*` questions are used, only "That's all." remains.

## Jumps

```ink
-> other_knot        // go to another knot (or a stitch, or a label)
-> DONE              // stop here; the chat waits
```

Use `-> DONE` to stop a chat. Avoid `-> END`: it ends the story itself, not just the chat.

### Stitches (sections inside a knot)

```ink
=== rukia ===
= start
Hey.
* [Hi.] -> calm
* [What now?] -> angry

= calm
Good to hear from you.
-> DONE

= angry
Don't take that tone.
-> DONE
```

The chat starts at the knot's first stitch. Jump inside the knot with `-> calm`, from outside with `-> rukia.calm`.

## Variables

```ink
// main.ink
VAR trust = 0
VAR knows_key = false
VAR suspect = ""
```

```ink
* [I believe you.]
    ~ trust = trust + 1
    ~ knows_key = true
    Thank you.
```

- `~` lines change variables. They must sit on their own line.
- Variables are shared by all chats, so what the player says to one person can change another chat.

## Conditions

```ink
{knows_key: You already know about the key.}
{trust > 2: I trust you. | I'm not sure about you.}

{
- trust >= 3:
    Okay, here's everything.
- trust >= 1:
    I'll tell you a little.
- else:
    Forget it.
}
```

### Conditional choices

```ink
* {knows_key} [Is this about the key?]
* {trust > 1} [You can trust me.]
* [Bye.]
```

The choice only appears when the condition is true.

### Has the player already seen something?

A knot, stitch or label name works as a number: how many times it was visited.

```ink
{rukia.angry: You were rude to me before.}
* {not kisuke.ask} [Ask Kisuke first.]
```

Conditions are checked when the line plays. A chat that already stopped at `-> DONE` doesn't look again. To react to something that happens later in another chat, keep the chat waiting at a choice (a sticky `+` choice works well) and check the condition after it.

## Varying text

```ink
{Hi.|Hi again.|You again?}       // a different line each time (stops at the last)
{&Morning.|Hey.|Yo.}             // cycles
{~Hmm.|Well...|Okay.}            // random
```

Useful inside loops.

## Game tags

Tags go at the end of a line, after `#`. Several can share a line.

| Tag | Effect | Example |
|---|---|---|
| `#from: <id>` | Who says the line. **Required on every line in group chats.** | `I saw it. #from: orihime` |
| `#delay: <seconds>` | The line arrives that many real seconds after the previous one. The player gets a notification if they're not in the chat, even with the game closed. | `I'm back. #delay: 30` |
| `#call` | The speaker calls the player right after the line. The chat waits until the call is answered or declined. | `Calling you. #call` |
| `#call: <audio>` | Same, and answering plays a clip from `media/` (it hangs up when the clip ends). | `Pick up. #call: audio/rukia_call.m4a` |

```ink
=== night_shift ===
Anyone up? #from: ichigo
Me. #from: orihime #delay: 10
```

- **Delays add up:** a line with `#delay: 30` after one with `#delay: 30` arrives 60 s after the start.
- **Hidden chats:** a chat only appears in the list once its first line has arrived. A delayed first line makes a chat or group "appear" later:
  ```ink
  === girls ===
  Made us a group! #from: rangiku #delay: 60
  ```

## Comments

```ink
// one line
/* several
   lines */
TODO: write the ending
```

## Common mistakes

- **Choice text printed twice, as the other person's message:** you forgot the `[ ]` around the choice text.
- **A chat never starts:** its knot name doesn't exactly match the chat id or the contact id.
- **"You shouldn't use a '~' here":** the `~` is on the same line as other text. Move it to its own line.
- **A chat runs out of content with an error:** every path must end in `-> DONE` (or a jump).
- **A group line shows the wrong person:** the line is missing `#from:`.
- **The new file is ignored:** it's not `INCLUDE`d from `main.ink`.
