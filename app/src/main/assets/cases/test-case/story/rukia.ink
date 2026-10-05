// 1-to-1 chat: lines without a #from tag are said by Rukia.

=== rukia ===
Hey, are you there?
I need to tell you something important.
* [What happened?]
    Last night I saw someone outside the lab.
    They were carrying a box with the old station logo.
    ~ trust_rukia += 1
* [Not now, I'm busy.]
    ...fine.
    I'll ask Ichigo then.
    ~ rukia_upset = true
* [Is this about the key?]
    Wait. How do you know about the key?!
    ~ knows_about_key = true
// Every branch above continues here.
- {rukia_upset: Don't ignore me next time. It matters.}
Meet me at 9, at the old station.
* [I'll be there.]
    Good. Come alone.
* {knows_about_key} [Should I bring the key?]
    Yes. Without it we can't open the door.
* [Call me.]
    Okay. Calling you now. #call: audio/testllamada.m4a
    ...the signal down here is awful.
    Just be at the station.
- See you at 9.
-> DONE
