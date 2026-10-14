# Multimedia pendiente: lista de comprobación (caso RUK-93429049)

Todo lo que hay que crear a mano para que el caso tenga sentido. Marca cada casilla al añadir el archivo.

- **Qué debe verse u oírse** en cada archivo está en [MULTIMEDIA.md](MULTIMEDIA.md) (galería), [GONPI.md](GONPI.md) (publicaciones) y [story/LLAMADAS.md](story/LLAMADAS.md) (guiones). Esta lista solo dice **qué archivo** va **dónde**.
- Rutas relativas a `cases/ruk-93429049/`. Nombres en minúscula y sin espacios. Fotos en `.jpg`/`.webp`, audios en `.m4a`/`.ogg`.
- 🔎 = lleva una pista: el detalle indicado tiene que **leerse bien** en la imagen.
- Esta lista se amplía con cada día que se escribe. **Estado: historia completa (D1-D7).**

### Cómo se generan las imágenes (prompts con fichas y fotos de referencia)

Cada imagen lleva un **prompt en inglés** (casi todas las IA de imagen funcionan mejor así) de 60-150 palabras: medio y encuadre, quién sale, dónde, qué tiene que leerse, luz y cámara y, cuando la pista depende de ello, **lo que NO debe verse**.

Las personas, los animales y los lugares que se repiten **no se describen en cada prompt**: se escriben como `{alicia}`, `{nuria}`, `{mas_rosalia}`… y cada ficha se define **una sola vez**, en la sección 0. Así una cara no cambia de una foto a otra, y si cambias un rasgo lo cambias en un único sitio.

1. **Crea primero las fotos de referencia** (sección 0), empezando por las protagonistas. `node tools/prompt.mjs --ref alicia` imprime el prompt de su retrato neutro. Guarda el resultado como `tools/refs/ruk-93429049/alicia.jpg` (también vale `.png` o `.webp`).
2. **Para cada imagen**: `node tools/prompt.mjs IMG_0393.jpg` (o `gonpi/guia_a.jpg`, `photos/mama.jpg`…). Imprime el prompt completo (y lo copia al portapapeles) y **la lista de archivos que hay que adjuntar, en orden**.
3. **Adjunta los archivos en ese orden y pega el prompt.** «Reference image 1» del texto es el primer adjunto, «reference image 2» el segundo, y así. Las fichas que aún no tienen foto no se adjuntan: el script las sustituye por su descripción en texto.

- `--max N`: adjunta como mucho N referencias (las primeras que aparecen en el prompt; las protagonistas van siempre primero). Útil si tu IA solo admite 1-4 imágenes; el resto de personas va con texto.
- `--corto`: cuando una ficha tiene foto, la descripción se reduce a «una chica de 18 años (exactly as in reference image 1)». Úsalo si tu IA corta el prompt o si el texto y la foto se contradicen.
- `node tools/prompt.mjs --refs`: cuántos prompts usan cada ficha y cuáles aún no tienen foto. Crea antes las que más salen.
- Ninguna IA acepta 10.000 caracteres útiles: un prompt final queda entre 700 y 3.000. Si tu IA lo corta, usa `--corto`.
- Sin script: copia el prompt a mano, sustituye cada `{ficha}` por su texto de la sección 0 y añade al final «No watermark, no logos of real brands, no text other than the text quoted in this prompt, no distorted hands.».

**Reglas de coherencia** (para cualquiera que retoque un prompt):
- **El lugar también se repite.** L'Hostalnou y la Vall de Bianya: *stone houses with terracotta tile roofs and wooden balconies, green meadows, forested hills*. Las masías: *rough grey-brown stone*. La Garrotxa: *lush volcanic hills, beech and oak forests, morning mist*. Olot: *old streets with Catalan shop signs, volcanic cones behind*. Los carteles del pueblo, en catalán. Los sitios que salen más de una vez son fichas (`{forn}`, `{casal}`…).
- **Rasgos mediterráneos realistas, de la Garrotxa (Catalunya), nunca de catálogo.** Caras corrientes, piel con poros, sin filtro de belleza: los prompts lo piden y conviene no quitarlo.
- **Texto dentro de la imagen.** Las IA suelen escribir mal. En las 🔎 donde el texto es la pista (la libreta, el billete, el mapa, la tarjeta de la nevera, «l'Ali i la Trufa», el dintel «1782» de Gonpi, el lomo del libro), genera la imagen y escribe o corrige el texto después con un editor.
- **Cuidado con dar demasiado.** Varias fotos se dejan a propósito incompletas (IMG_0391 sin dintel ni casa entera, `places/mas_rosalia.jpg` sin puerta azul): el prompt dice qué NO debe aparecer. Respétalo.
- Ninguna foto debe parecerse a una persona real. Las cuentas de grupos, marcas y locales son inventadas: no pidas logos reales.

---

## 0. Fichas y fotos de referencia (`tools/refs/ruk-93429049/`)

Una línea por ficha. El texto es el que el script inserta donde el prompt dice `{ficha}`: **primero qué es** (hasta los dos puntos; es lo que queda con `--corto`) y **después los rasgos**. Solo rasgos de cara, pelo, cuerpo y accesorios que identifican; la ropa la pone cada escena. Marca la casilla cuando exista `tools/refs/ruk-93429049/<ficha>.jpg`.

Si dos fichas son la misma persona en otra edad (`alicia_nina`, `ignasi_50`…), su foto de referencia se genera **a partir de la de la ficha principal** (adjúntala y pide la misma persona más joven).

### Primero: las protagonistas

- [x] `alicia`: an 18-year-old Catalan girl of Mediterranean look, slim athletic build of a volleyball setter, about 1.65 m: oval face, light olive skin with faint freckles across the nose and cheeks, thick dark brown hair just past the shoulders, usually in a loose low ponytail with loose strands framing the face, straight dark eyebrows, warm brown almond-shaped eyes with a slightly tired look, small straight nose, full lips, no makeup, small silver stud earrings
- [ ] `alicia_nina`: an 8-year-old Catalan girl, small and thin for her age: thick dark brown hair in two tight braids, round child's face with light olive skin and faint freckles, big dark-brown slightly wary eyes, straight dark eyebrows, a scabbed knee
- [ ] `alicia_12`: a 12-year-old Catalan girl, gangly and growing fast: thick dark brown hair in one long braid over her shoulder, light olive skin with faint freckles, straight dark eyebrows, big brown almond-shaped eyes, a shy closed-mouth smile
- [x] `nuria`: an 18-year-old Catalan girl, tall and sporty, about 1.70 m: round face, wide expressive smile with dimples, light tanned skin, long straight light brown hair with sun-bleached ends reaching mid-back, honey-brown eyes, small gold hoop earrings
- [x] `montse`: a 52-year-old Catalan woman, very thin and frail, Alicia's mother: very thin face with hollow cheeks, short greying brown bob, light olive skin, dark circles under calm, slightly absent brown eyes, thin pale lips, a serene fixed half-smile (she has Alicia's nose and mouth)
- [x] `berta`: a 24-year-old Catalan woman, slim and upright, Alicia's older sister: long straight glossy chestnut hair parted in the middle, oval face with sharper, more angular features than her sister, olive skin, light brown eyes with an intense calm stare, a fixed serene smile, no jewellery, no makeup
- [ ] `merce`: the yaya Mercè as she was ten years ago, a Catalan grandmother in her late 60s, short and sturdy: short curly white hair, round kind face with deep laugh lines, tanned weathered skin, small bright dark eyes, a wide open laugh, strong hands of a countrywoman
- [ ] `ignasi`: Ignasi, a 61-year-old Catalan man, tall and lean with an upright posture: neat full white beard trimmed close, short white hair combed back, tanned skin with fine wrinkles, pale grey-blue eyes with a gentle unblinking gaze, a calm practised half-smile, a wooden bead bracelet on the left wrist
- [ ] `ignasi_50`: the same man eleven years younger, 50 years old: neat salt-and-pepper beard that is greying but not yet white, short dark-grey hair combed back, tanned skin, pale grey-blue eyes with a calm unblinking gaze, fewer wrinkles, no bracelet
- [ ] `iris`: a 20-year-old woman, small and slight: pale skin, long straight light ash-blonde hair, thin face, light grey eyes with dark circles under them, chapped lips, a faint sad smile
- [ ] `ona`: an 18-year-old Catalan girl, tall: short glossy black bob, dark eyes, light olive skin, gentle smile
- [ ] `mireia`: an 18-year-old Catalan girl, soft build: voluminous curly black hair, round tortoiseshell glasses, dark eyes, olive skin, a nervous sweet smile
- [ ] `carla`: an 18-year-old girl: long straight dyed blonde hair with darker roots, green eyes, sharp groomed eyebrows, light skin, fashionable makeup with winged eyeliner, a butterfly hair clip
- [ ] `pol`: a 19-year-old Catalan young man, about 1.80 m: short dark brown hair, light stubble, brown eyes, olive skin, broad shoulders, a calm slightly shy look
- [ ] `dani`: a 22-year-old Spanish young man with a gym build: muscular, dark buzz cut, thick eyebrows, olive skin, a small stud earring in the left ear, a confident smirk
- [ ] `eric`: a 19-year-old Catalan young man, tall and gangly: curly ginger hair, pale skin with lots of freckles, green eyes, a goofy shy smile, often a bit of flour on his cheek
- [ ] `jordi`: a 55-year-old Catalan man, medium height, slightly stooped: balding with short grey hair on the sides, grey stubble, rectangular glasses, kind tired brown eyes (the same eyes as Alicia), olive skin, an awkward friendly smile
- [ ] `marta`: a 58-year-old Catalan woman, stocky and strong, Jordi's older sister: short dyed copper-red hair, strong cheekbones, direct brown eyes, sun-tanned skin, a big warm smile
- [ ] `arnau`: a 16-year-old Catalan boy, skinny: messy dark brown hair, braces on his teeth, light olive skin, a cheeky grin

### Después: el resto de personas

- [ ] `dani_20`: the same young man two years younger, 20 years old: slimmer, dark buzz cut, thick eyebrows, small stud earring, a tired neutral expression
- [ ] `marc`: a 19-year-old farm boy from the Vall de Bianya: short messy brown hair, sunburnt cheeks, strong forearms with a farmer's tan, a proud stubborn grin
- [ ] `marc_18`: the same farm boy a year younger, 18 years old: short messy brown hair, sunburnt cheeks, a stubborn frown, thinner
- [ ] `laura`: an 18-year-old Catalan girl: wavy honey-blond hair, sweet round face, light freckles, blue-grey eyes, an eager smile
- [ ] `sergi`: a 19-year-old Catalan young man, slim: neat short brown hair, thin metal-rimmed glasses, a serious tidy look
- [ ] `aina`: an 18-year-old girl: messy light-brown bun, round face, big hazel eyes with tired circles, a lovely sudden smile when she forgets herself
- [ ] `paula`: an 18-year-old girl: straight dark hair with a blunt fringe, dark eyes, a sarcastic half-smile, a tiny nose stud
- [ ] `oriol`: a lean 20-year-old Catalan cyclist: curly brown hair, sunburnt nose, a bright smile, tanned forearms with a sharp tan line at the sleeve
- [ ] `elena`: a 44-year-old Catalan woman, a teacher: shoulder-length brown hair with a few greys, glasses with thin dark frames, light freckles, kind direct eyes
- [ ] `pilar`: a 63-year-old Catalan woman, strong build: grey hair in a bun, round rosy face, strong forearms, stern but kind eyes, a little flour on her forehead
- [ ] `sonia`: an athletic 38-year-old Catalan woman, a volleyball coach: high dark ponytail, tanned, strong shoulders, a whistle on a lanyard, a knee brace on the right knee
- [ ] `judit`: a tall lanky 17-year-old girl: long thin braids, big loud laugh, always mid-joke
- [ ] `mar_roig`: a professional volleyball setter in her early 30s, tall and athletic: dark hair in a tight braid, strong jaw, a big confident grin
- [ ] `teresa`: a 56-year-old Catalan woman, a librarian: grey-brown hair in a low bun, soft features, reading glasses on a chain, a gentle patient smile
- [ ] `ramon`: a cheerful 79-year-old Catalan man: flat cap, white bushy eyebrows, deep smile wrinkles, big ears, a cardigan
- [ ] `gemma`: a 34-year-old Catalan woman: wavy chestnut hair to the shoulders, a wide smile, hoop earrings, tanned
- [ ] `xavier`: a 50-year-old Catalan man, a choir conductor: salt-and-pepper hair swept back, thin face, expressive dark eyebrows, long fingers
- [ ] `roser`: a 74-year-old Catalan woman: permed short grey hair, small round face, pink lipstick, sharp eyes, many fine wrinkles
- [ ] `pep`: a 68-year-old Catalan man: big grey moustache, a straw hat, a sun-beaten face, bushy eyebrows, calloused hands
- [ ] `lluisa`: a 61-year-old Catalan woman: short grey hair, tanned, square jaw, kind tired eyes
- [ ] `toni`: a 26-year-old man: short dark beard, lean, friendly eyes, usually a cycling cap
- [ ] `fatima`: a 29-year-old woman of Moroccan origin: a light-coloured headscarf framing her face, warm dark eyes, a warm smile
- [ ] `claudia`: a 40-year-old yoga teacher: long brown hair in a braid, calm face, lean toned arms, a serene expression
- [ ] `imma`: a 47-year-old woman: shoulder-length faded brown hair, thin, worried eyes, a hesitant smile
- [ ] `pau`: a 21-year-old young man: scruffy brown hair, sunburnt, an easy smile, a camp neck scarf
- [ ] `clara`: a 19-year-old girl: short dark hair with a pink streak, freckles, big laugh, paint on her hands
- [ ] `biel`: a 19-year-old young man: short dark hair, a backwards cap, a cheeky grin
- [ ] `quim`: a 45-year-old man, a municipal councillor: receding brown hair, round friendly face, stubble, shirt sleeves rolled up
- [ ] `conxita`: a 71-year-old Catalan woman: white hair in a neat bun, bright inquisitive eyes, rosy cheeks
- [ ] `enric`: a 64-year-old Catalan man: thick grey hair, weathered face, big nose, reading glasses, clean-shaven
- [ ] `rafa`: a 52-year-old driving instructor: thinning dark hair, aviator sunglasses, tanned, strong forearms
- [ ] `hugo`: an 18-year-old young man: curly brown hair, big ears, a goofy smile
- [ ] `dolors`: a 60-year-old woman, a theatre director: silver bob, dramatic dark eyebrows, expressive hands, a colourful scarf
- [ ] `queralt`: a 23-year-old woman, an actress: long straight dark hair with a heavy fringe, red lipstick, sharp cheekbones, theatrical eyes
- [ ] `jan`: a 20-year-old young man: floppy light-brown hair, rubbery expressive face, always pulling a funny face
- [ ] `anna`: a 41-year-old woman, a nurse: dark hair in a tidy ponytail, minimal makeup, a tired kind smile
- [ ] `silvia`: a 39-year-old woman, an estate agent: sleek shoulder-length chestnut hair, polished professional smile, small gold earrings

### Estilo (no es una persona: no lleva foto)

Se añade al final de **toda foto hecha con el móvil** (selfies, fotos de perfil, fotos sueltas de la galería y de Gonpi). Va como `{movil}` y se cambia aquí, en un único sitio. No va en capturas de pantalla, memes, copias escaneadas, carteles, logos, ilustraciones, fotos «profesionales» de cuentas con estilo propio (Casal, Guía, Berta, Policía) ni en las 🔎 donde hay que leer un texto (esas llevan `{movil_texto}`).

- [ ] `movil`: Photographic look, a low-quality unedited snapshot from a cheap old phone or compact digital camera from around 2008-2012, low resolution with a small sensor, soft focus and slightly out of focus, details smudged and not razor-sharp, noticeable digital noise and grain, strong JPEG compression artifacts, muddy shadows and blown-out highlights with low dynamic range, slightly washed-out colours with a faint warm cast, a little camera shake or motion blur, dim underexposed low available light with lifted noisy shadows, imperfect casual framing, harsh direct on-camera flash only if the scene allows it (flat hard shadows right behind the subject), real skin with pores and blemishes, no retouching, no studio lighting, no cinematic colour grading, not clear, not crisp, not a professional photograph.
- [ ] `movil_texto`: Photographic look, a low-quality unedited snapshot from a cheap old phone or compact digital camera from around 2008-2012, low resolution with a small sensor, a slightly soft overall image, noticeable digital noise and grain, visible JPEG compression, muddy shadows and slightly blown highlights with low dynamic range, slightly washed-out colours with a faint warm cast, dim underexposed low available light, a little camera shake, imperfect casual framing, harsh direct on-camera flash only if the scene allows it, no studio lighting, no cinematic colour grading, not a professional photograph, but any printed or written text in the picture stays sharp, in focus and perfectly legible

Se usa **`{movil_texto}`** (más suave) en las fotos de móvil que no llevan `{movil}`: las 🔎 donde hay que leer un texto, las de las cuentas de Gonpi y las demás fotos sin personas de la galería. Mantiene el aire de móvil de hace unos años (no todo súper nítido) sin tapar el texto.

### Animales

- [ ] `trufa`: Trufa, a big shaggy brown long-haired farm dog about ten years old, mixed Pyrenean-mastiff type: floppy ears, a white patch on the chest, a greying muzzle, gentle patient eyes
- [ ] `trufa_cachorra`: Trufa as a puppy, a fluffy brown long-haired puppy: floppy ears, a white patch on the chest, big clumsy paws
- [ ] `rocky`: Rocky, a medium-large black mixed-breed dog: short glossy fur, a red collar, a small tan spot over each eye, floppy ears, a friendly open mouth

### Lugares

Para repetir un lugar entre fotos. Su foto de referencia es **una vista general**, no un primer plano.

- [ ] `mas_rosalia`: the Mas de la Rosalia, an isolated traditional Catalan masia of rough grey-brown fieldstone with a low terracotta tile roof, two storeys, small deep-set windows with old wooden shutters, a bright blue wooden door under a carved stone lintel engraved "1782", a round stone well with a rounded rim in front of the door, a rough stone bench beside the door, dry-stone walls and dense beech forest behind it, halfway up a narrow road to the Collada de Bracons; no electricity poles, no cables, no satellite dish, no cars, no solar panels
- [ ] `can_pericot`: Can Pericot, an abandoned masia in a forest clearing in Capsec: darker basalt stone than the Rosalia's house, bricked-up windows, a faded peeling pale-blue wooden door, ivy and brambles on the walls, part of the terracotta roof collapsed; no well, no bench, no animals
- [ ] `forn`: the Forn Can Batlle, a small traditional bakery in an old street of Olot, founded in 1964: whitewashed walls with a lower band of old green ceramic tiles, a wooden counter with a brass scale, wicker baskets of rustic "pa de pagès" bread, wooden shelves behind, the iron door of a wood-fired oven at the back, a round wall clock, small handwritten price tags in Catalan, fluorescent tubes mixed with warm bulbs
- [ ] `casal`: the Casal, a large restored Catalan masia near Sant Joan les Fonts: cleaned pale stone walls with new terracotta roofs, a stone-walled meditation hall, a tidy lawn garden with a low stone wall, white curtains, a small golden rose painted above the main door, everything very neat and a little too perfect
- [ ] `hostalnou`: L'Hostalnou de Bianya, a small village in the Vall de Bianya: stone houses with terracotta tile roofs and wooden balconies, a Romanesque church bell tower, a small main square with plane trees, green meadows and forested hills around it, a small roundabout at the entrance
- [ ] `calle_del_pont`: the Calle del Pont in L'Hostalnou de Bianya: a narrow street of old stone houses with wooden balconies and flowerpots, one old orange street lamp, uneven cobbles, a small stone bridge at the end
- [ ] `crater`: the Santa Margarida volcanic crater in the Garrotxa: a perfectly round deep bowl covered by green beech and oak forest, a flat grassy floor with a small stone Romanesque hermitage standing alone in the middle, a path going down from the rim
- [ ] `cocina`: Alicia's family kitchen in a stone village house: an old white fridge, pale green wall tiles, a wooden table with a plastic floral tablecloth, mismatched chairs, a gas hob, a calendar from a local butcher, dim yellowish ceiling light, tidy but worn
- [ ] `cuarto_alicia`: Alicia's small bedroom on the first floor: a single bed with a dark green quilt, a wooden desk with a lamp, a poster of a starry sky, a volleyball shirt with number 7 over a chair, an old wooden window with shutters over the street, a cork board with photos
- [ ] `pabellon`: the municipal sports hall of Olot: varnished wooden floor with painted volleyball lines, a net, a high ceiling with fluorescent panels, Catalan-language banners on the walls, folding wooden bleachers, blue and orange club colours

---

## 1. Audios de llamadas (`media/audio/`)

- [ ] `audio/laia_encargo.m4a`: D1, la sargento Puig (~60 s)
- [ ] `audio/jordi.m4a`: D2, el padre (~40 s)
- [ ] `audio/pilar.m4a`: D3, la panadería (~30 s). 🔎 Que se oiga bien «¿son para la Rosalia otra vez?»
- [ ] `audio/rosalia.m4a`: D4, Rosalia (~45 s). 🔎 Que se oigan «la nena», el pan, la perra Trufa y «la puerta azul»
- [ ] `audio/berta.m4a`: D5, Berta (~25 s). Eco, mantra de fondo y una vela; que la consigna de la rosa se entienda
- [ ] `audio/iris.m4a`: D6, Iris (~50 s). Susurro, grifo, pasos. Que se oigan claros «seis y media», «cráter de Santa Margarida» y «agua de luz»
- [ ] `audio/alicia_ubicacion.m4a`: D5 noche, Alicia por el prepago (~25 s). Susurro, viento, la perra. Que se oigan claros «Mas de la Rosalia», «desvío de Sant Salvador», «puerta azul» y «pozo»
- [ ] `audio/alicia_final.m4a`: D7, Alicia (~30 s). Sitio cerrado, radio policial bajita, la perra; sin motor ni viento, para que valga en el coche patrulla, en comisaría o junto al cráter. Sirve para los finales 1, 2 y 3
- [ ] `audio/guia_final.m4a`: D7, Ignasi (~20 s). Viento y pájaros; solo en el final malo


## 2. Galería de Alicia (`multimedia/`)

Las ve la app Multimedia, ordenadas por nombre. Son fotos de móvil corrientes, hechas **por otro motivo**: la pista está al fondo, medio tapada (ver [MULTIMEDIA.md](MULTIMEDIA.md)).

- [x] `IMG_0391.jpg` 🔎 Alicia de niña con su yaya y la cachorra; solo un trozo de puerta azul y el brocal del pozo; «l'Ali i la Trufa» a boli en el margen (**rehacer**: era demasiado evidente)
  - Prompt: `Scanned analog family print, a real snapshot about ten years old: washed-out colours with a slight yellow-magenta cast, film grain, faint creases, a dust speck, a white border around the picture and the slight tilt of a hand-placed scan. Tight close framing taken by an adult at chest height. Subjects: {alicia_nina} sits very stiff and upright on a rough stone bench, hugging {trufa_cachorra} against her chest a little nervously, holding it slightly away from her face; beside her {merce} laughs out loud with one arm around the girl, wearing a beige knitted cardigan. Background, deliberately minimal: behind the grandmother's shoulder only a narrow vertical slice of an old bright blue wooden door set in a rough grey-brown stone wall, and at the very bottom right corner the rounded stone rim of a well. The whole house must NOT be visible: no roof, no lintel, no date, no number, no windows. Bright summer afternoon sun, slightly overexposed. Text, handwritten in faded blue ballpoint on the white bottom border, small and a bit slanted: "l'Ali i la Trufa". Aspect ratio 1:1.`
- [x] `IMG_0393.jpg` 🔎 Alicia y Núria en El Port de la Selva, con el cartel «Benvinguts al Port de la Selva»
  - Prompt: `Candid smartphone photo taken by a classmate at the end-of-year school trip, a year ago, bright midday Mediterranean light: {alicia} (a year younger than now, no bracelet, hair in a messy ponytail) and {nuria} laugh with their arms around each other's shoulders, a bit sunburnt, wearing light cotton t-shirts and denim shorts, sitting fully dressed on the pebble beach of El Port de la Selva on the Costa Brava. Behind them, slightly to the left, a blue glazed-tile sign that clearly reads "Benvinguts al Port de la Selva"; further back whitewashed houses, a church tower, fishing boats pulled up on the shore and the dry brown Cap de Creus hills. Slight motion blur on one of the hands, small lens flare, ordinary amateur framing with a little too much sky. {movil} Aspect ratio 4:3.`
- [ ] `IMG_0394.jpg` Equipo de vóley, Alicia con el 7
  - Prompt: `Smartphone group photo taken by a parent from a few metres away after a home match in {pabellon}: a girls' junior volleyball team of only six players plus the coach, posing close together in two rows and all looking straight at the camera, so every face is large, in focus and clearly visible, blue and orange jerseys with white numbers, sweaty, red-faced, happy, some making peace signs. In the front row, centre-left, {alicia} wearing number 7, arm around the player next to her; next to her {ona} wearing number 4; {judit} wearing number 10, with long thin braids, at the back; at the right end, standing, the coach {sonia} in a tracksuit with a whistle and a clipboard. The other two players wear numbers 3 and 12. Only Alicia wears number 7, no other girl has it. Plain bare pale walls behind them, no banners, no posters, no signs, no flags and no text anywhere except the numbers on the jerseys. Harsh overhead gym light, a little overexposed faces, a volleyball in the lap of the girl in the middle. {movil} Aspect ratio 4:3.`
- [ ] `IMG_0395.jpg` 🔎 Tarta de 18 años; Núria atándole la **pulsera roja**
  - Prompt: `Close-up smartphone photo, taken at night with the flash off in {cocina}, intimate and a little blurry: a homemade sponge birthday cake on the table with number candles "1" and "8" lit, warm flickering candlelight on everything. Over the cake, {nuria} concentrates on tying a thin red string bracelet around the left wrist of {alicia}, who is only partly in frame (her hand, forearm and the lower half of her face, smiling, light olive skin and faint freckles). The red string and Núria's fingers are the sharp point of the picture. Behind, the blurred pale green tiles and the white fridge. {movil} Aspect ratio 1:1.`
- [x] `IMG_0397.jpg` Meme del gato meditando (**rehacer**: el texto ha cambiado)
  - Prompt: `A forwarded-many-times meme image, slightly compressed, low-res feel: a fluffy grey cat sitting upright in a cross-legged meditation pose with its eyes closed and its paws on its knees, on a purple cushion, in a plain living room, a serene smug face. Bold white Impact-style capital letters with a black outline across the bottom: "YO UN DOMINGO A LAS 7 DE LA MAÑANA FINGIENDO PAZ INTERIOR". Aspect ratio 1:1.`
- [x] `IMG_0398.jpg` Selfi de Alicia y Ona en el coche, la bolsa de vóley detrás (**rehacer**: antes era Berta al amanecer)
  - Prompt: `Front-camera smartphone selfie inside a small hatchback on a sunny country road in the Garrotxa, taken by the driver with one hand: {ona} at the wheel, singing with her mouth wide open and her eyes half-closed, and in the passenger seat {alicia} laughing with her head tipped back, her ponytail loose; both wearing blue and orange volleyball club tracksuit tops. On the back seat, clearly visible between them, a big blue sports bag with the knee pads sticking out of the open zip. Green hills through the windows, sun strap of light across the dashboard, slightly blurry from the movement. {movil} Aspect ratio 3:4.`
- [x] `IMG_0400.jpg` 🔎 Desde la ventana, de noche: furgoneta blanca delante de casa descargando garrafas sin etiqueta; matrícula ilegible (**rehacer**: antes era la fachada del Casal)
  - Prompt: `Grainy night photo taken with a smartphone from a first-floor window of {cuarto_alicia} through slightly fogged glass, with the dark edge of the wooden window frame visible at the bottom of the picture: looking down on {calle_del_pont} wet from rain, lit only by one orange street lamp. A plain white van with no logos is parked in front of the door with its rear doors open; two blurred human silhouettes carry big plastic water jugs with no labels towards the house. The number plate is completely unreadable because of the glare of the lamp on it. Quiet, slightly eerie, ambiguous like a routine delivery; no faces visible. Heavy noise, low light, slight motion blur. {movil_texto} Aspect ratio 3:4.`
- [x] `IMG_0401.jpg` 🔎 Libreta de la madre con las facturas de casa; entre ellas «pis iaia 120.000 → CRA ✔» (**rehacer**: el texto ha cambiado)
  - Prompt: `Hurried, slightly blurry smartphone photo taken from above at a bit of an angle of an open spiral notebook on a kitchen table, with a coffee mug sitting on one top corner and covering part of the page. Adult neat cursive handwriting in blue ballpoint, one item per line, in Catalan, with small tick marks: "llum 64,20 ✔", "aigua 31,80 ✔", "comunitat ✔", "pis iaia 120.000 → CRA ✔", "M ✔  B ✔", "A: pendent", "dijous: papers al despatx". Plain squared paper, a ballpoint pen lying across the page, dim yellowish kitchen light. It must look ordinary and boring: just household bills. {movil_texto} Aspect ratio 3:4.`
- [x] `IMG_0402.jpg` 🔎 La nevera: el horario del Forn en el centro; detrás asoma media tarjeta con el cráter y la ermita (**rehacer**: antes era el folleto entero)
  - Prompt: `Smartphone photo of the door of the old white fridge in {cocina}, taken straight on from a metre away. In the centre, held by two magnets, a printed bakery shift schedule table with several rows underlined in pink highlighter. Around it: a souvenir magnet of El Port de la Selva (a small ceramic boat), a volleyball fixtures calendar, a handwritten shopping list on yellow paper, a take-away pizza magnet. Partly hidden behind the top-right corner of the schedule, only about half visible, a small cream-coloured card with a fine black line drawing of half a round volcanic crater with a tiny hermitage inside and cut-off elegant serif text: "...obada de l'Al..." and, lower, "...famílies". The card must be easy to miss but readable if you zoom in. Dim warm kitchen light, small reflection on the door. {movil_texto} Aspect ratio 3:4.`
- [x] `IMG_0403.jpg` Masa madre derramada
  - Prompt: `Smartphone photo in {forn} early in the morning: a big glass jar of sourdough starter knocked over on the stainless steel counter, pale sticky dough spilled and dripping over the edge, scattered flour dust, a wooden spoon, one tag with "6:05" written on it in marker. Cold fluorescent light mixed with the first daylight from the shop window, shot from above at a slant, a little blurry because it was taken in a hurry and in a mood. {movil} Aspect ratio 1:1.`
- [x] `IMG_0405.jpg` 🔎 Cielo estrellado desde una ventana con postigos; la montaña con la antena
  - Prompt: `Night photograph taken from inside a dark room, through an open old wooden window with rustic wooden shutters pushed open on both sides, set deep in a thick whitewashed stone wall (the thick sill is visible in the foreground, slightly out of focus). Outside, a sky full of stars with the Milky Way visible, no clouds. On the horizon the dark forested silhouette of a mountain ridge, the Collada de Bracons, with a very small telecom antenna with a tiny red light at its summit. No artificial lights anywhere, no buildings, no cars, completely quiet. Long exposure with a little noise, the slightly tilted framing of a phone resting on the sill. Aspect ratio 3:4.`
- [x] `IMG_0406.jpg` 🎭/🔎 Selfi en el bus a Girona con la chica de la capucha (Mireia: rizo negro y gafas redondas)
  - Prompt: `Front-camera smartphone selfie inside a regional bus in the early morning: {alicia} in the window seat, hair in a loose ponytail, sleepy half-closed eyes, a tired small smile, wearing a grey hoodie. Next to her, in the aisle seat, {mireia} with her hoodie hood pulled up and her head bent over her phone, so that her face is mostly hidden: only a curly black lock of hair and the round tortoiseshell frames of her glasses are visible under the hood. Through the window behind them, a blue road sign reading "Girona" and a grey sky. Flat bus lighting, cold tones, a little grain. {movil} Aspect ratio 3:4.`
- [x] `IMG_0407.jpg` Selfi con el uniforme del Forn
  - Prompt: `Front-camera smartphone selfie of {alicia} inside {forn}, wearing a white bakery apron and a white cap that holds most of her hair, a forced tired smile, a smear of flour on one cheek. Behind her, slightly out of focus: baskets of rustic Catalan bread, wooden shelves, small handwritten price tags in Catalan and the round wall clock. Flat, unflattering fluorescent light, natural skin texture, no retouching, the framing of someone taking a quick selfie between customers. {movil} Aspect ratio 3:4.`
- [x] `IMG_0409.jpg` 🔎 Captura del mapa: ruta a pie de 2 h 10 min desde la estación de Olot hasta «…de Bianya», tapada por la notificación de Núria
  - Prompt: `Smartphone screenshot (not a photo of a screen) of a generic fictional maps app, clean flat UI in light mode, no real brand logo and not a copy of any known maps app. A walking route drawn as a blue line through a green mountain valley in Catalonia, a summary card at the bottom: a walking-person icon, "2 h 10 min", "9,4 km". The starting point is labelled "Estació d'autobusos, Olot". Across the top of the screen, covering the destination field, a notification banner with a small messaging-app icon that reads "Nuri: tía mañana vienes a girona o qué"; under the banner only the end of the destination text is visible: "…de Bianya". Phone status bar with the time 22:41 and a low battery. Aspect ratio 9:16.`
- [x] `IMG_0410.jpg` 🔎 Captura del billete de bus Olot → Barcelona Nord, 23:55, 14,20 €
  - Prompt: `Smartphone screenshot of a digital bus ticket in the app of a generic fictional bus company, clean flat UI, no real brand. Header with the route in big text "Olot → Barcelona Nord", below "23:55" as the departure time, seat "12", passenger name left blank, price "14,20 €", a black and white QR code in the middle, a green "Billete válido" label. Phone status bar with the time 21:02. Aspect ratio 9:16.`
- [x] `IMG_0411.jpg` 🔎 Captura del correo «Tu reembolso de 14,20 € está en camino», 21:14
  - Prompt: `Smartphone screenshot of an email open in a generic fictional email app, clean flat UI, no real brand. Sender name of a fictional bus company, subject in bold "Tu reembolso de 14,20 € está en camino", received at "21:14", a short body: "Hemos tramitado la cancelación de tu billete Olot → Barcelona Nord. Recibirás el importe en 3-5 días hábiles." Phone status bar with the time 21:15. Aspect ratio 9:16.`
- [x] `IMG_0412.jpg` 🔎 Bolsa de tela con barras sobre el mostrador del Forn; reloj a las 20:58
  - Prompt: `Smartphone photo at closing time inside {forn}: a big cloth tote bag, stuffed with crusty "pa de pagès" loaves with their tips sticking out, sits on the wooden counter next to the brass scale. In the upper background, a round white wall clock with black numerals whose hands clearly show 20:58. Nearly empty wooden shelves, one last basket with two loaves, the shop lights half off, warm dim light from one bulb. Taken by someone in a hurry: slight tilt, a little blurry. {movil_texto} Aspect ratio 3:4.`
- [x] `IMG_0413.jpg` 🔎 Mochila: linterna, jersey, cargador sin móvil, vela, cerillas
  - Prompt: `Overhead smartphone photo of an open hiking backpack lying on a single bed with a dark green quilt in {cuarto_alicia}, its contents laid out neatly beside it: a black flashlight, a folded thick wool jumper in oatmeal colour, a white wall phone charger plugged into nothing with its cable coiled and no phone, one plain white candle and a box of matches. Soft warm desk lamp light, a wooden desk and a corner of a poster visible at the edge. Practical, calm, deliberate; nothing else on the bed. {movil_texto} Aspect ratio 4:3.`

## 3. Imágenes de Gonpi (`media/gonpi/`)

Una por publicación, con nombre `<cuenta>_<día>.jpg` (si una cuenta publica dos veces el mismo día, `_a` y `_b`). Los avisos de @hostalnou.debianya son un cartel con el texto del aviso. Son las fotos de las **cuentas** (cada una con su estilo: las de Berta y del Casal, cuidadas y de coach; las del pueblo, de móvil sin pretensiones).

**Antes del D1**
- [x] `gonpi/alicia_dm1.jpg` 🔎 Amanecer sobre el valle («a veces hay que irse para salvarse»)
  - Prompt: `Smartphone photo of a sunrise over the misty Vall de Bianya in the Garrotxa, Catalonia, taken from a hillside above the village at the first light: pink and orange sky, layers of forested volcanic hills fading into mist, a few stone farmhouses with terracotta roofs in the valley floor, a thin church tower. Calm and melancholic, slightly desaturated like a photo posted by a teenager at 7 a.m., no filter text, no people. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/alicia_dm12.jpg` 🔎 La misma vista estrellada que `IMG_0405`
  - Prompt: `Night photo taken from inside a dark room through an open old wooden window with rustic wooden shutters in a thick stone wall, a sky full of stars and the Milky Way above the dark forested ridge of the Collada de Bracons with a tiny telecom antenna on its summit, no artificial light, the same composition as a photo taken from the same spot, a little lighter and more cropped than the original. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/alicia_dm40.jpg` 🔎 Cumpleaños con la **pulsera roja** en primer plano
  - Prompt: `Close-up smartphone photo of the left wrist of {alicia}, light olive skin, a thin red string bracelet in sharp focus, her hand holding a plate with a slice of homemade birthday cake, a number "18" candle blurred in the background and the face of a smiling girl out of focus behind it. Warm kitchen light, a little grain. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/alicia_dm90.jpg` 🔎 Playa de El Port de la Selva con Núria
  - Prompt: `Smartphone selfie-style photo of {alicia} and {nuria} sitting close together on the beach of El Port de la Selva, both laughing and squinting in the sun, wet hair, towels, the turquoise Mediterranean and the whitewashed village behind them, bright midday light. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/alicia_dm120.jpg` Foto del equipo de vóley
  - Prompt: `Smartphone team photo of a girls' junior volleyball team in blue and orange jerseys, two rows, in {pabellon}; in the front row {alicia} wearing number 7, serious face, arms crossed, next to {ona}; behind them {judit}; a ball held in the middle. Harsh gym light, slightly off-centre framing. {movil} Aspect ratio 1:1.`
- [x] `gonpi/excursions_dm60.jpg` 🔎 El Mas de la Rosalia: **puerta azul, dintel 1782**, la perra
  - Prompt: `Hiking club photo, taken on a sunny day by a hiker on the road: {mas_rosalia} seen from the front and slightly from below, halfway up a narrow dirt road through dense beech forest. The bright blue wooden door and the carved stone lintel with "1782" are clearly legible; the round stone well is in front of the door; {trufa} sits by the door looking at the camera with her tongue out. A few hikers' backpacks on the ground at the lower edge. Lush green Garrotxa hills, midday light, honest amateur photo. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/excursions_dm45.jpg` 🎭 Can Pericot (Capsec): masía abandonada, **puerta azul descolorida**, sin pozo ni perro. Que no se confunda con la del dm60: piedra más oscura, ventanas tapiadas
  - Prompt: `Hiking club photo on an overcast day: {can_pericot} seen from the front in a forest clearing in Capsec, the faded peeling pale-blue door, the dark basalt stone, the bricked-up windows, ivy and brambles climbing the walls, the collapsed part of the roof. No well, no dog, no people, no bench. The blue of the door must look clearly different from a bright blue: washed-out, grey-blue. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/excursions_dm30.jpg` Santuari del Mont
  - Prompt: `Hiking photo from the summit of the Santuari del Mont, a small sanctuary of old stone with a bell gable on a rocky hilltop, wide views over the green volcanic Garrotxa and the blue line of the Pyrenees on the horizon, two hikers' backpacks and a water bottle in the foreground, clear afternoon light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/excursions_dm15.jpg` Cascada de la Alta Garrotxa
  - Prompt: `Hiking photo of a hidden waterfall falling about five metres into a green pool in a mossy limestone gorge of the Alta Garrotxa, beech trees leaning over it, soft light through the leaves, silky water from a slightly long exposure, a hiker's boot at the edge of the frame. Aspect ratio 1:1.`
- [x] `gonpi/guia_a.jpg`, `gonpi/guia_b.jpg`, `gonpi/guia_c.jpg`: estilo coach: amaneceres, manos, una taza de infusión, con frases sobreimpresas
  - `gonpi/guia_a.jpg`: `Minimalist spiritual coach Instagram post, professionally graded: a sunrise over misty Garrotxa hills, soft pastel gold and pink colours, lots of calm empty sky in the upper half. Overlaid in the sky in an elegant thin serif font, centred: "Lo que posees acaba poseyéndote." A tiny golden rose icon in the bottom right corner. No people, no other text. Aspect ratio 1:1.`
  - `gonpi/guia_b.jpg`: `Minimalist spiritual coach Instagram post, professionally graded: two weathered older hands cupped open and facing up in warm side light, shallow depth of field, a beige linen cuff, soft cream background. Overlaid in an elegant thin serif font: "Hay vínculos que sanan y vínculos que pesan." A tiny golden rose icon in the bottom right. No other text. Aspect ratio 1:1.`
  - `gonpi/guia_c.jpg`: `Minimalist spiritual coach Instagram post, professionally graded: a ceramic cup of herbal tea steaming on a rustic wooden table next to a single pale pink rose, soft morning light coming through a stone window, muted warm tones. Overlaid in a thin serif font: "Abril siempre vuelve. La rosa también." No other text. Aspect ratio 1:1.`
- [x] `gonpi/casal_a.jpg` Meditación en grupo, de espaldas
  - Prompt: `Photo of a group meditation in the garden of {casal}, about fifteen people in white and beige loose clothes sitting cross-legged on cushions in rows on the lawn, all seen from behind so that no face is visible, golden hour over green hills, long soft shadows, a calm, almost advertising-like image. Aspect ratio 1:1.`
- [x] `gonpi/casal_b.jpg` 🔎 Meditación donde se reconocen Montse y Berta
  - Prompt: `Photo of a group meditation inside the stone-walled hall of {casal}, seen from the side, women in white loose clothes sitting cross-legged on cushions with their eyes closed, candles on the floor. In the front row, clearly recognisable in three-quarter profile, {montse} and next to her {berta}. Other participants out of focus behind. Warm candlelight, soft contrast. Aspect ratio 1:1.`
- [x] `gonpi/casal_c.jpg` Cesta llena de móviles apagados a la entrada del Casal (retiro de silencio)
  - Prompt: `Photo of a large wicker basket full of switched-off smartphones, wristwatches and a few car keys standing on a stone bench at the entrance of {casal}, beside the open wooden door, a small wooden sign with a golden rose and the handwritten words "Silenci" on a card, soft morning light. No people. {movil} Aspect ratio 1:1.`
- [x] `gonpi/casal_d.jpg` Cartel del retiro «Sanar el alma»: bosque con niebla, cojines de meditación en círculo, tonos cálidos
  - Prompt: `Retreat advertisement image in the style of a spiritual coaching brand: a misty beech forest of the Garrotxa at dawn, a circle of cream meditation cushions arranged on the moss, warm golden tones, a lot of calm empty space at the top. Elegant text in a thin serif font: "Sanar el alma" as the title and below, smaller, "Retiro de tres días". A small gold rose logo at the bottom. Aspect ratio 1:1.`
- [x] `gonpi/casal_e.jpg` Testimonio: manos de mujer sujetando una taza, texto «Llegué rota. Hoy tengo una familia.»
  - Prompt: `Testimonial post: close-up of the hands of a thin middle-aged woman holding a ceramic cup of tea, beige linen sleeves, soft window light, creamy blurred background. Elegant serif text overlay centred in the upper part: "Llegué rota. Hoy tengo una familia." and, smaller below: "— M., 52 años". A tiny golden rose at the bottom. Aspect ratio 1:1.`
- [x] `gonpi/guia_d.jpg` Círculo de cojines vacío con velas, de noche
  - Prompt: `Night photo of an empty circle of cream meditation cushions on the wooden floor of a stone-walled room, surrounded by dozens of lit candles, dark corners, a single window with a black sky, calm but a little eerie, no people. Aspect ratio 1:1.`
- [x] `gonpi/montse_a.jpg`, `gonpi/montse_b.jpg`, `gonpi/montse_c.jpg`: flores, el valle, frases
  - `gonpi/montse_a.jpg`: `Plain smartphone photo by a middle-aged woman: pink and white flowers in a small village garden in the Vall de Bianya, a stone wall behind, soft morning light, slightly crooked framing, no filter. {movil} Aspect ratio 1:1.`
  - `gonpi/montse_b.jpg`: `Plain smartphone photo of the calm green Vall de Bianya at dusk seen from a hillside, the stone village of L'Hostalnou below with its terracotta roofs and a church bell tower, warm sky, a little too wide, no filter. {movil} Aspect ratio 1:1.`
  - `gonpi/montse_c.jpg`: `Plain smartphone photo of a bouquet of garden flowers in a glass jar on the stone windowsill of an old village house, a lace curtain at one side, soft daylight. {movil} Aspect ratio 1:1.`
- [x] `gonpi/dani_a.jpg`, `gonpi/dani_b.jpg`: moto y gimnasio
  - `gonpi/dani_a.jpg`: `Smartphone photo at night of a black sport motorcycle parked under a street lamp in a street of Olot, wet asphalt reflecting the orange light, a plane tree, the photo taken from a low angle as if showing off the bike, strong flash-less contrast. {movil} Aspect ratio 1:1.`
  - `gonpi/dani_b.jpg`: `Gym mirror selfie of {dani} in a black tank top, the phone covering the lower half of his face, flexing slightly, rows of weights and a rubber floor reflected behind him, harsh white gym light. {movil} Aspect ratio 1:1.`

**D1**
- [x] `gonpi/berta_d1.jpg` Amanecer con la frase del alba sobreimpresa («Suelta lo que pesa…»)
  - Prompt: `Spiritual coach Instagram post, professionally graded: a sunrise over the misty Vall de Bianya, a pastel pink and gold sky, forested hills fading into mist. Overlaid centred in an elegant thin serif font: "Suelta lo que pesa. Lo que es tuyo, siempre vuelve." A tiny golden rose icon in the bottom corner. No people. Aspect ratio 1:1.`
- [x] `gonpi/hostalnou_d1.jpg` Cartel del perro perdido **Rocky**
  - Prompt: `Village notice poster from the town hall of L'Hostalnou de Bianya, simple A4 layout in black text on white with a coat-of-arms placeholder at the top: a clear photo of {rocky} sitting in a garden, and below in large bold capital letters in Catalan "S'HA PERDUT" and "RESPON A ROCKY", then a small line "Si el veieu, truqueu a l'Ajuntament". Photographed as a sheet pinned on a cork board. Aspect ratio 1:1.`
- [x] `gonpi/marc_d1.jpg` Tractor en la fiesta del pueblo
  - Prompt: `Smartphone photo of a green tractor decorated with colourful paper flags and ribbons parked in the small main square of L'Hostalnou de Bianya during the village festival, old stone houses with wooden balconies around it, strings of festival bunting, a few people watching in the background, sunny, taken by someone proud of the tractor, lower angle. {movil} Aspect ratio 1:1.`
- [x] `gonpi/carla_d1.jpg` Foto antigua de las cuatro amigas
  - Prompt: `Slightly faded old smartphone photo from about three years ago of four 15-year-old girls hugging and squashed together at a house party, flash on and red-eye: {alicia} (younger, hair loose), {carla} (blonde, the hair a bit shorter), {mireia} (curly black hair and round glasses) and {nuria} (long light brown hair and gold hoops). Everyone laughing, a party garland in the background, a living room. {movil} Aspect ratio 1:1.`

**D2**
- [x] `gonpi/berta_d2.jpg` Amanecer con frase («Dar es la forma más pura de recibir.»)
  - Prompt: `Spiritual coach Instagram post: a sunrise over rolling Garrotxa hills, pastel sky, a thin line of mist in the valley. Overlaid centred in an elegant thin serif font: "Dar es la forma más pura de recibir." A tiny golden rose icon in the corner. No people. Aspect ratio 1:1.`
- [x] `gonpi/dani_c.jpg` 🎭 Terraza de bar de noche con una mochila negra sobre la mesa (placeholder: copia de `dani_a.jpg`)
  - Prompt: `Smartphone photo at night of a table on the bar terrace of a small Catalan town, a black backpack on the table next to a half-empty glass of beer and a phone, warm string lights, the rest of the terrace blurred, a casual ordinary shot that hides nothing. {movil} Aspect ratio 1:1.`
- [x] `gonpi/montse_d2.jpg` 🔎 Manos abiertas al sol («Cerrar etapas también es cuidar de los tuyos»)
  - Prompt: `Photo of two thin open hands raised towards the morning sun, light shining through the fingers, a green valley blurred behind, soft and spiritual, slightly overexposed like a photo posted by a middle-aged woman with a clean filter. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/casal_d2.jpg` 🔎 El tejado nuevo de la sala del Casal (media hora después de la transferencia; **rehacer**)
  - Prompt: `Smartphone photo of a brand-new terracotta tile roof on a new stone extension of {casal}, scaffolding still up, a worker's ladder, a small golden rose painted above the new door, sunny morning, clean construction site, a proud documentary shot. Aspect ratio 1:1.`
- [x] `gonpi/eric_d2.jpg` 🔎 Cesta de pan vacía en el Forn
  - Prompt: `Smartphone photo of an empty wicker bread basket on the wooden counter of {forn}, only a few crumbs left, and next to it a note torn from a pad with a handwritten "6?" in black marker, playful, taken by {eric} at an angle, a corner of his flour-dusted forearm in the frame. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/arnau_d2.jpg` 🔎 Arnau en la rotonda del Hostalnou; al fondo, una chica con mochila y **barras que asoman**, con la chaqueta verde
  - Prompt: `Blurry action smartphone photo at night, taken by a friend with the flash on, at the small roundabout at the entrance of L'Hostalnou de Bianya under orange street lights: {arnau} in mid-fall off his bike, arms flailing, a startled grin. In the background, slightly out of focus and small, a girl with thick dark brown hair in a loose low ponytail ({alicia}) wearing a green corduroy jacket, carrying a backpack and a big cloth bag from which long bread loaves stick out, walking away along the road. {movil} Aspect ratio 1:1.`
- [x] `gonpi/pol_d2.jpg` Salpicadero de noche, reloj a las **23:41**, la estación de Olot por la ventana
  - Prompt: `Night smartphone photo of a car dashboard: the digital clock clearly showing 23:41, the steering wheel and a worn gear lever, and through the windscreen the small bus station of Olot with a sign that reads "Estació d'autobusos", orange street lights and an empty bus bay. Slightly blurry, a bit of dashboard reflection on the glass. {movil} Aspect ratio 1:1.`

**D3**
- [x] `gonpi/berta_d3.jpg` Amanecer con frase («Quien de verdad te quiere, te espera…»)
  - Prompt: `Spiritual coach Instagram post: a sunrise over the valley, pastel sky, a thin ribbon of mist. Overlaid centred in an elegant thin serif font: "Quien de verdad te quiere, te espera sin hacer preguntas." A tiny golden rose icon in the corner. Aspect ratio 1:1.`
- [x] `gonpi/dani_d3.jpg` 🔎 Concierto en Barcelona con la entrada fechada
  - Prompt: `Smartphone photo taken from the crowd at a rock concert in a small club in Barcelona, stage lights in red and blue, a blurred singer on stage, raised arms and phones. In the lower right corner, a hand holding a paper ticket in sharp focus, a plain fictional venue ticket printed "ENTRADA · SALA 2 · 22:30" and, large and clearly readable, a date "14/06" (the date is fixed afterwards in an editor to the night before Alicia disappeared). Shaky, noisy, flashing lights. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/oriol_d3.jpg` 🔎 Captura de la ruta en bici «Subida nocturna a Bracons 🌙 · 1:12 h»
  - Prompt: `Screenshot of a fictional cycling app activity page, dark mode: a winding mountain road route drawn in orange on a dark map of the Garrotxa, title "Subida nocturna a Bracons 🌙", stats "1:12 h", "14,3 km", "+780 m", a small elevation chart below, a start time "00:40". Clean flat UI, no real brand. Aspect ratio 1:1.`
- [x] `gonpi/mireia_d3.jpg` Fiesta de cumpleaños de Mireia
  - Prompt: `Smartphone photo of a birthday party in a living room in a Catalan village at night: {mireia} blowing out the candles of a cake, round glasses reflecting the flames, balloons and a "18" garland, friends cheering and clapping around her with flash-lit faces, cups on a table. Warm and messy. {movil} Aspect ratio 1:1.`
- [x] `gonpi/laura_d3.jpg` 🔎 Cojines y velas en un jardín, con una rosa dorada pequeña en una esquina (el jardín del Casal)
  - Prompt: `Smartphone photo of cream meditation cushions and lit tea-light candles on the lawn of the garden of {casal} at dusk, a low stone wall behind, and in one corner of the picture a small golden rose ornament on a wooden post, taken by an enthusiastic newcomer, slightly tilted. {movil_texto} Aspect ratio 1:1.`

**D4**
- [x] `gonpi/berta_d4.jpg` Amanecer con frase («No todo el que se aleja se pierde…»)
  - Prompt: `Spiritual coach Instagram post: a sunrise, a pastel sky over hills, a small bird silhouette. Overlaid centred in an elegant thin serif font: "No todo el que se aleja se pierde. Y quien se pierde, vuelve a casa." A tiny golden rose icon in the corner. Aspect ratio 1:1.`
- [x] `gonpi/hostalnou_d4.jpg` Cartel de obras en la carretera de Bracons
  - Prompt: `Village council notice poster in Catalan, simple official A4 layout with a yellow road-works warning triangle icon and black text: "Obres a la carretera de Bracons", "Tram de Sant Salvador", "De 9 a 14 h", and a small line "Ajuntament de l'Hostalnou de Bianya". Photographed pinned on a cork board. Aspect ratio 1:1.`
- [x] `gonpi/iris_d4.jpg` Amanecer movido
  - Prompt: `Blurry shaky smartphone photo of a sunrise over the Garrotxa hills taken quickly and secretly through a window, a bit of a curtain edge and a window frame in the shot, muted colours, motion blur, tilted horizon, the feeling of a photo taken in a hurry by someone afraid of being seen. {movil} Aspect ratio 1:1.`
- [x] `gonpi/casal_d4.jpg` Jardín del Casal al atardecer, esterillas en el césped
  - Prompt: `Photo of the garden of {casal} at sunset, cream yoga mats laid out in neat rows on the lawn, warm golden tones, long shadows, the stone building with a lit window in the background, no people, clean professional look. Aspect ratio 1:1.`
- [x] `gonpi/nuria_d4.jpg` Foto antigua de Núria con Alicia
  - Prompt: `Old smartphone photo from about three years ago of {nuria} and {alicia} at fifteen hugging and laughing at the camera, flash on, slightly faded colours and low resolution, a bedroom with posters behind them. Aspect ratio 1:1.`

**D5**
- [x] `gonpi/berta_d5.jpg` Amanecer con frase («Viste el alma de blanco…»)
  - Prompt: `Spiritual coach Instagram post: a sunrise, a pastel sky, a very soft mist. Overlaid centred in an elegant thin serif font: "Viste el alma de blanco y el mundo se vuelve ligero." A tiny golden rose in the corner. Aspect ratio 1:1.`
- [ ] `gonpi/voley_d5.jpg` El equipo tras perder el partido
  - Prompt: `Smartphone photo of a disappointed girls' junior volleyball team sitting in a row on a bench in {pabellon} after losing a match, blue and orange jerseys, towels over their heads, bottles in hand, tired and sad faces, {ona} in the middle with a towel on her head, an empty place on the bench; the scoreboard in the background reads 1-3. {movil} Aspect ratio 1:1.`
- [x] `gonpi/casal_d5.jpg` 🔎 Cráter de Santa Margarida con la ermita (anuncio de la Trobada de l'Alba; **rehacer** sin el texto)
  - Prompt: `Dramatic wide photo of {crater} in the early morning, a perfectly round bowl covered in green beech and oak forest, the small stone hermitage in the middle of its flat grassy floor, a veil of mist inside the crater and the sun about to rise on the rim. Natural, no text, no people, no overlays, professional landscape quality. Aspect ratio 1:1.`
- [x] `gonpi/laura_d5.jpg` Atardecer en el Hostalnou
  - Prompt: `Smartphone photo of an orange sunset over the terracotta roofs and the church tower of {hostalnou}, taken from a hillside, slight lens flare, pretty and a little oversaturated by a phone filter. {movil} Aspect ratio 1:1.`

**D6**
- [x] `gonpi/berta_d6.jpg` Amanecer con frase («Lo mejor está a punto de amanecer.»)
  - Prompt: `Spiritual coach Instagram post: a golden sunrise, a pastel sky. Overlaid centred in an elegant thin serif font: "Lo mejor está a punto de amanecer." A tiny golden rose in the corner. Aspect ratio 1:1.`
- [x] `gonpi/hostalnou_d6.jpg` 🔎 Cartel del corte de luz de 18:00 a 22:00
  - Prompt: `Village council notice poster in Catalan, simple official A4 layout with a black lightning bolt icon and the text "Tall de subministrament elèctric", "Avui de 18:00 a 22:00", "Disculpeu les molèsties", and a small line "Ajuntament de l'Hostalnou de Bianya". Photographed pinned on a cork board. Aspect ratio 1:1.`
- [x] `gonpi/montse_d6.jpg` 🔎 Mesa puesta para tres
  - Prompt: `Photo of a dinner table set for three in a simple old village kitchen with a stone wall, a white tablecloth, three white plates, three glasses, one lit candle in the middle, nothing else on the table, no people, an eerie stillness, cold evening light from a window. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/excursions_d6.jpg` 🔎 Camino de tierra cortado; una furgoneta blanca al fondo
  - Prompt: `Hiking club photo on an overcast day: a dirt track in a beech forest on the way up to Bracons blocked by a red and white striped barrier, a white van with no logos parked further up the track, slightly out of focus, wet leaves on the ground, a hiker's hand holding a map at the lower edge. {movil_texto} Aspect ratio 1:1.`

**D7**
- [x] `gonpi/guia_d7.jpg` Imagen negra («Hoy amanece para todos.»)
  - Prompt: `A completely black image with tiny thin white serif text in the exact centre: "Hoy amanece para todos." Nothing else. Aspect ratio 1:1.`
- [x] `gonpi/hostalnou_d7.jpg` Final bueno: agradecimiento a los Mossos
  - Prompt: `Village council social post: a sunrise over the Vall de Bianya, soft light on the valley, in the foreground a small blue heart icon and the text in Catalan in a clean sans-serif: "Gràcies als Mossos d'Esquadra". Calm, official but warm. Aspect ratio 1:1.`
- [x] `gonpi/berta_d7.jpg` Final malo: amanecer («Ya estamos todas.»)
  - Prompt: `Spiritual coach Instagram post: a sunrise over the misty crater of Santa Margarida with the hermitage in the middle of the bowl, an unsettling calm. Overlaid centred in an elegant thin serif font: "Ya estamos todas." A tiny golden rose in the corner. Aspect ratio 1:1.`

**Grupos del pueblo** (ruido: la vida normal de Alicia)
- [ ] `gonpi/lectura_a.jpg` Tertulia del club de lectura en la biblioteca, ejemplares de «Nada» sobre la mesa
  - Prompt: `Smartphone photo of a book club meeting in the small public library of L'Hostalnou de Bianya: people of different ages (a young woman, two retired men, a woman with a stroller) around a large wooden table with several paperback copies of the novel "Nada" by Carmen Laforet, cups of coffee, shelves of books behind, warm library light. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/coral_a.jpg` La coral cantando en la parroquia, camisas blancas
  - Prompt: `Photo of a village choir of about twenty singers singing inside a small Romanesque stone church in the Garrotxa, white shirts and black trousers, a conductor ({xavier}) with raised hands in front, sheet music in their hands, bare stone vaults, warm light from a small window. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/aliments_a.jpg` Voluntarios con cajas de comida; Alicia de espaldas con chaleco
  - Prompt: `Photo of volunteers carrying cardboard food boxes in the warehouse of the food bank of Olot, green volunteer vests, shelves with tins; in the foreground a young woman seen from behind, thick dark brown hair in a loose ponytail ({alicia}), wearing a green vest and lifting a box. Neutral warehouse light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/aliments_d2.jpg` 🔎 Cuatro cajas de donación: ropa, mantas, una cafetera y un reloj de pared
  - Prompt: `Photo of four large cardboard donation boxes side by side on a warehouse floor, overflowing with: one with folded clothes, one with wool blankets, one with an old Italian moka coffee maker and some kitchen items, one with a wooden wall clock on top; a handwritten label "DONACIÓ" on one. Neutral light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/claudia_a.jpg` Sala del polideportivo con esterillas
  - Prompt: `Photo of a room in the municipal sports centre of a Catalan town, blue yoga mats laid out in rows with folded blankets on each, a wall of mirrors, wooden bars along one wall, warm evening light through a high window, no people. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/esplai_a.jpg` Niños con globos de agua en el parque, monitores empapados
  - Prompt: `Photo of laughing kids throwing water balloons in a village park under plane trees, a few soaking-wet young camp monitors in the middle with neck scarves, water drops frozen in the air, strong sunshine, joyful chaos. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/teatre_a.jpg` Ensayo en el escenario del local social
  - Prompt: `Photo of an amateur theatre rehearsal on the small stage of a village social centre in Catalonia: about eight actors dressed in black reading from scripts, a director ({dolors}) seated in the front, wooden floor, a bare backdrop, a few folding chairs, plain light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/teatre_d4.jpg` Cartel «Buscamos Adela»
  - Prompt: `Amateur theatre group poster: a black and white ink drawing of a Spanish window with iron bars and a lace curtain, and below the text in Catalan in a bold serif font: "Busquem una Adela" with "La casa de Bernarda Alba" in smaller letters, a thin black border, off-white paper texture. Aspect ratio 1:1.`
- [ ] `gonpi/autoescola_d4.jpg` Coche de autoescuela con una «L» y confeti
  - Prompt: `Photo of a white driving school car with a rooftop sign with a big "L", covered in confetti and streamers, parked on a street in Olot, a few smiling people in the background, sunny. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/pau_a.jpg` Monitores de campamento, Alicia entre ellos
  - Prompt: `Group photo of about ten summer camp monitors in a forest clearing in the Garrotxa, all wearing yellow neck scarves and the same orange T-shirt, in two rows; among them, in the back row, {alicia} with her hair in a loose ponytail, and in front {pau} with a guitar. Warm summer light filtered by the trees. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/queralt_a.jpg` Selfie de Queralt y Alicia con vestidos negros de ensayo
  - Prompt: `Smartphone selfie backstage of two young women in plain black rehearsal dresses: {queralt} with her red lipstick and dramatic eyebrows leaning in, and {alicia} behind her, her hair tied back, smiling a little, a clothes rail with costumes and a mirror with bulbs in the background. Mixed warm and white light. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/gemma_a.jpg` Esterilla enrollada y un vermut en una terraza
  - Prompt: `Photo of a rolled-up yoga mat leaning against a chair next to a glass of vermouth with an olive and a slice of orange on a café terrace table in a Catalan town square, sunny, a small plate of crisps, relaxed. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/toni_d5.jpg` Almacén lleno de lotes preparados
  - Prompt: `Photo of the food bank warehouse in Olot full of prepared boxes stacked up to the shoulders on pallets, each with a printed label, neat rows, a pallet jack, neutral fluorescent light. {movil_texto} Aspect ratio 1:1.`

**Perfiles de los contactos** (cada contacto tiene su cuenta y se puede buscar en Gonpi). Fotos de móvil normales, cada una con el estilo de su autor: ver su ficha en [story/perfiles/](story/perfiles/).

*Familia*
- [ ] `gonpi/jordi_d6.jpg` Desde el coche, la entrada a Olot con los volcanes al fondo, cielo nublado (D6)
  - Prompt: `Smartphone photo taken through the windscreen of a car at a red light: the road entering Olot with the first houses and a roundabout, and in the background the green rounded volcanic cones of the Garrotxa under a heavy cloudy sky, a dashboard edge and a rear-view mirror at the border, flat grey light, a sad ordinary photo by a father driving. {movil} Aspect ratio 1:1.`
- [x] `gonpi/jordi_dm45.jpg` Una habitación juvenil vacía en un piso de Barcelona: cama hecha, cojín morado, estantería casi vacía
  - Prompt: `Smartphone photo of an empty teenage girl's bedroom in an old Barcelona flat with high ceilings and a balcony door with a pale curtain: a single bed neatly made with a white duvet and one purple cushion, a desk with nothing on it, an almost empty white bookshelf with three books, a bare wall with four tape marks where posters used to be, afternoon light, lonely and a bit awkward. Aspect ratio 1:1.`
- [ ] `gonpi/jordi_dm110.jpg` Selfi de un hombre de 55 con casco de bici, sudado, con Barcelona al fondo
  - Prompt: `Front-camera selfie of {jordi} wearing a bright cycling helmet, sweaty and red-faced with an awkward friendly smile, glasses slightly fogged, on a path in the Collserola hills with Barcelona and the sea far below in a hazy morning light. {movil} Aspect ratio 1:1.`
- [x] `gonpi/marta_d4.jpg` Su parada del mercado de Olot con la persiana a medio bajar, cajas de fruta tapadas (D4)
  - Prompt: `Photo of a fruit and vegetable stall in the covered market of Olot with its metal shutter half pulled down and the wooden crates of fruit covered with cloths and cardboard, a handwritten price sign in Catalan hanging crooked, the aisle empty and the neighbouring stalls busy, flat market light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/marta_dm40.jpg` Marta abrazando a Alicia en su 18 cumpleaños, las dos riendo, en una cocina
  - Prompt: `Candid smartphone photo in {cocina}: {marta} hugging {alicia} from the side in a big squeeze, both laughing with their eyes closed, Marta's copper-red hair against Alicia's dark brown ponytail, a "18" candle cake on the table in front of them, warm light. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/marta_dm3.jpg` Cajas de cerezas en la parada, con un cartelito escrito a mano «Cireres de la vall»
  - Prompt: `Photo of wooden crates of dark red cherries at a market stall in Olot, a handwritten cardboard sign in Catalan "Cireres de la vall" and a price in marker, a green apron hanging at the edge, bright morning light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/arnau_dm3.jpg` Captura de pantalla de un videojuego con el rango a punto de subir (inventado, sin marcas reales)
  - Prompt: `Screenshot of a fictional online video game ranked-match results screen: a dark purple fantasy UI, a progress bar almost full toward "Diamante", a glowing rank emblem, a list of six player names with scores, the word "VICTORIA". No real brand, no real game logo. Aspect ratio 1:1.`
- [x] `gonpi/arnau_dm25.jpg` Una bandeja de canelones gratinados en la mesa de la iaia, mantel de cuadros
  - Prompt: `Photo of a big baking tray of Catalan cannelloni with golden gratinated béchamel on a grandmother's table with a red-and-white checkered tablecloth, a wooden serving spoon, a bread basket and a bottle of water, hungry-teenager framing taken from slightly above, warm kitchen light. {movil_texto} Aspect ratio 1:1.`

*Amigos*
- [x] `gonpi/nuria_dm9.jpg` Girona de noche: las casas del Onyar iluminadas reflejadas en el río
  - Prompt: `Night photo of the colourful houses along the Onyar river in Girona reflected in the dark water, the cathedral and its staircase lit above, a pedestrian bridge, photographed from the bank by a tourist-like student, slight noise. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/nuria_dm35.jpg` Núria con el uniforme de prácticas de Enfermería, haciendo el signo de la victoria
  - Prompt: `Smartphone photo of {nuria} in a white-and-light-blue nursing student uniform with a lanyard badge, making a victory sign and a wide smile with her dimples, in a university corridor in Girona with a noticeboard behind her, fluorescent light. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/carla_dm6.jpg` Selfi de Carla en el espejo con el pelo recién cortado, mariposa en la funda del móvil
  - Prompt: `Mirror selfie of {carla} with a fresh shoulder-length cut and a blonde balayage, a butterfly sticker on her phone case, fashionable makeup, hand on hip, a tidy teenage bedroom with fairy lights behind her. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/carla_dm22.jpg` Hoguera de noche en el bosque, siluetas de cuatro chicas alrededor
  - Prompt: `Night photo of a campfire in a beech forest of the Garrotxa with the silhouettes of four girls sitting around it on logs, faces mostly in shadow, sparks in the air, orange light on the tree trunks, taken from a bit far away. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/mireia_dm15.jpg` Pies con calcetines gordos sobre una manta, el portátil con una serie de fondo
  - Prompt: `Cosy photo of a pair of feet in thick wool socks on a fleece blanket on a sofa, a laptop on the knees in the background playing a series, a mug of tea, soft evening light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/mireia_dm50.jpg` Las cuatro amigas (Núria, Carla, Mireia y Alicia) en una terraza, abrazadas
  - Prompt: `Smartphone photo of four 18-year-old girls hugging on a café terrace in Olot, the faces close together: {alicia}, {carla}, {mireia} and {nuria}, all smiling at the camera, a table with glasses of soft drinks, a plane tree and an old stone building behind them. {movil} Aspect ratio 1:1.`
- [x] `gonpi/pol_dm20.jpg` El motor de un coche abierto en un taller, manos con grasa
  - Prompt: `Photo of an open car engine bay in a small mechanic's workshop in Olot, two hands with black grease holding a wrench, tools hanging on the wall, a work light, a little oily floor. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/pol_dm75.jpg` Salpicadero de un Golf de noche, carretera de curvas iluminada por los faros
  - Prompt: `Night photo through the windscreen of a Volkswagen Golf of a winding mountain road through the Garrotxa forests lit by the headlights, the dashboard glowing softly, hands on the wheel at the edge, speed discretion. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/eric_dm4.jpg` Un tarro de masa madre volcado y seco, con una cinta de «escena del crimen» de papel
  - Prompt: `Humorous photo of a knocked-over sourdough starter jar with dried starter on a steel bakery counter, a strip of yellow paper tape with hand-drawn "ESCENA DEL CRIMEN" written on it stuck around the jar like crime scene tape, taken with a straight face. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/eric_dm12.jpg` Un tarro de masa madre recién hecha con una etiqueta que pone «PACO»
  - Prompt: `Photo of a fresh sourdough starter in a clean glass jar with a handwritten paper label "PACO" and a hand-drawn smiley face, on a wooden shelf in {forn}, a rubber band marking its height, soft morning light. {movil_texto} Aspect ratio 1:1.`

*Clase y estudios*
- [ ] `gonpi/sergi_d4.jpg` Un portátil con una presentación abierta y una taza de café, en un escritorio ordenado (D4)
  - Prompt: `Photo of a laptop showing a slide presentation titled "La soledad no deseada en el medio rural" and a white mug of coffee on a very tidy desk with a notebook and three coloured highlighters in a row, neutral light. Aspect ratio 1:1.`
- [x] `gonpi/sergi_dm6.jpg` Mesa de biblioteca con apuntes subrayados en tres colores
  - Prompt: `Photo of a library table in Olot covered with printed notes underlined in three colours (yellow, pink, green), a pencil case, an open textbook and a pair of thin glasses folded on top, neat and obsessive, soft overhead light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/sergi_dm21.jpg` Sergi cruzando la meta de una carrera popular con el dorsal puesto
  - Prompt: `Photo of {sergi} crossing the finish line of a local 10K race in the streets of Olot with his bib number pinned on his shirt, serious concentrated face, other runners behind, spectators with a banner, daylight. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/aina_d2.jpg` Un bocadillo a medio comer sobre los apuntes de psicología (D2)
  - Prompt: `Photo of a half-eaten sandwich on top of messy psychology notes and a highlighter, crumbs over the pages, a can of soda at the edge, a student desk in a bedroom, lamp light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/aina_dm4.jpg` Aina tumbada en la cama rodeada de apuntes, tapándose la cara
  - Prompt: `Photo of {aina} lying on her back on a bed surrounded by scattered notes and highlighters, covering her face with both hands in despair, messy bun, a cat-print duvet, bright afternoon light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/aina_dm30.jpg` Un gato atigrado encima de un libro abierto
  - Prompt: `Photo of a tabby cat lying on top of an open textbook on a desk, looking at the camera with a calm smug expression, a pencil rolling off, warm lamp light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/paula_d3.jpg` Tres vasos de café de máquina vacíos en fila (D3)
  - Prompt: `Photo of three empty plastic vending-machine coffee cups in a perfect row on a classroom desk, a half-open notebook and a pen beside them, a grey morning window, ironic. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/paula_dm2.jpg` Un café en la mano con el reloj del aula de fondo marcando las 11
  - Prompt: `Photo of a hand holding a paper cup of coffee in a classroom, with a round wall clock in the background clearly showing 11:00, rows of empty desks, sleepy mood. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/paula_dm18.jpg` Un bocadillo de cafetería triste, primer plano dramático
  - Prompt: `Dramatic close-up of a sad cafeteria sandwich with limp lettuce and one slice of ham on a plastic tray, under a harsh light and a very serious tone, as if it were a still from a drama. Aspect ratio 1:1.`
- [x] `gonpi/oriol_dm9.jpg` Bicicleta apoyada en un mojón de carretera de noche, con la luna
  - Prompt: `Night photo of a road bike leaning on a stone road marker on a mountain pass in the Garrotxa, a full moon above the dark forested slopes, the bike's rear light glowing red, quiet and atmospheric. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/oriol_dm30.jpg` Un frontal de bici encendido que deslumbra la cámara
  - Prompt: `Photo of a powerful bike headlamp switched on, dazzling the camera with a starburst of light, on a dark forest road at night, the shape of a bike frame barely visible. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/elena_dm10.jpg` Foto de grupo del ciclo delante de un edificio de Girona; Alicia en la segunda fila, sonriendo poco
  - Prompt: `Group photo of about twenty vocational training students in front of the brick entrance of a college building in Girona, two rows, a sunny morning; {elena} the teacher stands at the left side; in the second row {alicia} with her hair tied back smiling only a little, and {sergi} next to her with his glasses. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/elena_dm60.jpg` Un aula vacía con «Benvinguts» escrito en la pizarra
  - Prompt: `Photo of an empty classroom with neat rows of desks, and on the whiteboard written in green marker "Benvinguts", a pile of unopened folders on the teacher's desk, morning light through the blinds. {movil_texto} Aspect ratio 1:1.`

*Panadería*
- [ ] `gonpi/pilar_d7.jpg` El horno de leña del Forn encendido en la oscuridad, solo se ve el fuego (D7)
  - Prompt: `Photo of the wood-fired oven of {forn} burning in total darkness, the iron door open, only the fire's orange glow and a few loaves on the peel visible, sparks, deep shadows. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/pilar_dm7.jpg` Cocas de recapte en una bandeja de horno
  - Prompt: `Photo of Catalan "coca de recapte" flatbreads topped with roasted red peppers, aubergine and onions on a dark baking tray, still hot, a wooden peel and a kitchen cloth, early light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/pilar_dm30.jpg` El escaparate del Forn Can Batlle con globos y un cartel «60 anys»
  - Prompt: `Photo of the shop window of {forn} from the street, filled with bread and pastries, decorated with silver and gold balloons and a sign in Catalan "60 anys · Forn Can Batlle" painted in white on the glass, an old street of Olot reflected on the window. {movil_texto} Aspect ratio 1:1.`

*Vóley*
- [x] `gonpi/sonia_d5.jpg` La pista vacía después del partido, un balón en el suelo (D5)
  - Prompt: `Photo of {pabellon} empty after a volleyball match, the lights half off, a single ball resting on the floor near the net, folded bleachers, a forgotten towel on a bench. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/sonia_dm3.jpg` Entreno: una jugadora de espaldas con el 7 colocando el balón (Alicia)
  - Prompt: `Photo of a volleyball training session in {pabellon}: a girl seen from behind wearing number 7 with a dark brown ponytail ({alicia}), setting the ball with both hands above her forehead, teammates blurred in the background, tight sports hall light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/sonia_dm40.jpg` El equipo juvenil con un trofeo, todas gritando; Alicia con el 7
  - Prompt: `Photo of a girls' junior volleyball team in blue and orange kit shouting with joy and holding up a small trophy together in {pabellon}; in the middle {alicia} wearing number 7, her mouth wide open; {sonia} at the side with a clenched fist. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/judit_d4.jpg` Bolsa de deporte preparada con las rodilleras encima (D4)
  - Prompt: `Photo of a packed blue sports bag on a bedroom floor with new black volleyball knee pads placed on top, a water bottle and a pair of trainers beside it, ready for tomorrow. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/judit_dm2.jpg` Unas rodilleras nuevas, todavía con la etiqueta
  - Prompt: `Photo of a pair of brand-new black volleyball knee pads still with the cardboard tag and a price sticker, on a bedspread, taken proudly from above. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/judit_dm25.jpg` El vestuario del pabellón, bancos con mochilas y zapatillas
  - Prompt: `Photo of the changing room of a sports hall: wooden benches with sports backpacks, towels and trainers scattered, wet footprints, lockers, harsh white light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/ona_d5.jpg` Ona sentada en el banquillo con la toalla en la cabeza (D5)
  - Prompt: `Photo of {ona} sitting on a bench in {pabellon} after a match, a white towel over her head and shoulders, tired, looking at the camera with a small sad smile, bottle in her hand. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/ona_dm5.jpg` Selfi de Ona conduciendo de día, música en la pantalla del coche
  - Prompt: `Front-camera selfie of {ona} driving in daylight on a country road in the Garrotxa, sunglasses pushed up on her head, the car's screen showing a music app, a green landscape through the window, a relaxed smile. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/ona_dm55.jpg` Ona y Alicia sentadas en el capó de un coche, con la equipación del club
  - Prompt: `Photo of {ona} and {alicia} sitting side by side on the bonnet of a small car in the car park of a sports hall, both wearing blue and orange club tracksuits, legs swinging, laughing at something off-camera, golden evening light. {movil_texto} Aspect ratio 1:1.`

*Grupos del pueblo*
- [x] `gonpi/teresa_dm7.jpg` Sillas en círculo en la biblioteca, ejemplares de «Nada» encima de una mesa
  - Prompt: `Photo of chairs arranged in a circle in a small village library, a table in the middle with several copies of the novel "Nada" and a thermos, tall wooden bookshelves behind, warm lamp light, empty before the club arrives. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/teresa_dm35.jpg` 🔎 Estantería de «Secció local» con libros nuevos; en el lomo de uno se lee «Masies de la Vall de Bianya»
  - Prompt: `Photo of a library shelf with a printed label card "Secció local" and a row of new books standing, one of them with its spine clearly legible and facing the camera: "Masies de la Vall de Bianya". The other spines show other local titles in Catalan. Soft library light, shallow depth of field but the legible spine in focus. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/ramon_dm9.jpg` Corro de sardanas en la plaza, gente mayor con alpargatas
  - Prompt: `Photo of elderly Catalans dancing the sardana in a circle holding hands in a village square, the circle seen from outside with their bags piled in the middle, espadrilles, a small band on the side, a sunny Sunday. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/ramon_dm80.jpg` Un abuelo con cuatro nietos en un banco
  - Prompt: `Photo of {ramon} sitting on a stone bench with four grandchildren of different ages around him, one on his knee, all smiling, a flat cap, a plaza behind them, afternoon sun. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/ramon_dm150.jpg` Foto antigua en blanco y negro de una colla sardanista, 1968
  - Prompt: `Old black and white photo from 1968 of a group of young sardana dancers (about 16 teenagers in their Sunday best, boys in dark trousers and girls in light dresses) posing in a Catalan village square, the church behind, creased edges, a handwritten "1968" in white ink on the lower border. Aspect ratio 1:1.`
- [x] `gonpi/xavier_dm4.jpg` Partituras nuevas sobre el atril del piano de la parroquia
  - Prompt: `Photo of new sheet music on the stand of an old upright piano in a Romanesque church, stone walls and a candle in the background, a pencil resting on the keys, still, quiet light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/xavier_dm30.jpg` Ensayo de la coral: la fila de contraltos, Alicia entre ellas
  - Prompt: `Photo of a choir rehearsal in a stone church seen from the conductor's side, the alto row singing with sheet music, among them {alicia} with her mouth open in a song and her hair tied back, lit by a warm lamp. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/roser_dm1.jpg` Geranios rojos en un balcón de piedra
  - Prompt: `Photo of red geraniums in clay pots along the stone balcony of an old village house, a wooden shutter half open, a slice of blue sky, watered, taken by an older person from below. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/roser_dm70.jpg` 🔎 Foto antigua (1979) de cuatro mujeres con partituras; tres de unos cuarenta y una muy joven. Que se reconozca a la yaya Mercè (la de `IMG_0391`)
  - Prompt: `Old faded colour photo from 1979 of four Catalan women holding sheet music in front of a Romanesque church door, seventies clothes and hairstyles: three women in their forties, one of them ({merce}) with short curly hair, a round kind face and deep laugh lines, laughing, and one very young woman at the end, with a ponytail. Yellowish cast, soft focus, a white border. Aspect ratio 1:1.`
- [x] `gonpi/pep_dm3.jpg` Tomates enormes en una cesta de mimbre
  - Prompt: `Photo of huge ripe tomatoes of different shapes in a wicker basket in a vegetable garden, a hand for scale with a thumb at the edge, rows of plants behind, afternoon sun. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/pep_dm12.jpg` Una vieja furgoneta amarilla de Correos aparcada en un camino de tierra
  - Prompt: `Photo of an old yellow Spanish postal van with a faded logo parked on a dirt road among green meadows of the Vall de Bianya, a few boxes visible in the open back, summer. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/lluisa_dm14.jpg` Voluntarios del Banc d'Aliments con petos verdes entre cajas; Alicia en una esquina
  - Prompt: `Photo of food bank volunteers in green vests among stacked boxes in a warehouse, smiling; in a corner at the right {alicia}, with a thick dark brown ponytail, holding a box and looking at the camera a little shyly. Neutral light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/lluisa_dm50.jpg` La Fageda d'en Jordà en otoño
  - Prompt: `Photo of the Fageda d'en Jordà beech forest in autumn: tall grey trunks, golden leaves covering the volcanic ground, a thin path, soft low sunlight through the trees. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/toni_dm7.jpg` Cajas de lotes apiladas en una furgoneta
  - Prompt: `Photo of food boxes stacked inside the open rear of a white delivery van, neatly tied with tape, labelled with handwritten names, an early morning street behind. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/toni_dm20.jpg` Bici en la vía verde del Carrilet, con un puente de hierro
  - Prompt: `Photo of a bicycle leaning on a railing on the Carrilet greenway next to an old iron railway bridge in the Garrotxa, green vegetation, a river below, golden light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/fatima_dm6.jpg` Vaso de té con menta en la mesa de una sala de descanso de hospital
  - Prompt: `Photo of a small glass of mint tea on a table in a hospital break room, a few mint leaves floating, a plastic chair, a notice board with Catalan notices behind, flat light. Aspect ratio 1:1.`
- [x] `gonpi/fatima_dm28.jpg` Una fuente de cuscús en el centro de una mesa familiar
  - Prompt: `Photo of a big dish of couscous with vegetables and chickpeas in the centre of a family table, other dishes around it, hands reaching in, a patterned tablecloth, warm light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/gemma_dm3.jpg` Un vermut con aceitunas en una terraza
  - Prompt: `Photo of a glass of vermouth with olives, a slice of orange and ice, with a bowl of crisps on a terrace table in a Catalan town square, midday sun. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/gemma_dm16.jpg` Un libro abierto por la página 12 con un punto de libro
  - Prompt: `Photo of a paperback book lying open at page 12, with a ribbon-shaped bookmark between the pages, on a bed with white sheets, the page number "12" clearly visible, soft light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/claudia_dm8.jpg` Esterilla con vistas a los volcanes de la Garrotxa
  - Prompt: `Photo of a rolled-out yoga mat on a hillside meadow with a view of the green volcanic cones of the Garrotxa, early morning mist in the valley, a water bottle. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/claudia_dm45.jpg` Clase de ioga en el césped del Parc Nou de Olot
  - Prompt: `Photo of an outdoor yoga class on the lawn of the Parc Nou in Olot, about a dozen people on mats in a pose, plane trees, a sunny morning, seen from behind and from the side. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/imma_d2.jpg` Una mano sujetando el folleto de Rosa d'Abril (rosa dorada, «Sanar el alma») (D2)
  - Prompt: `Photo of a hand holding a cream-coloured flyer with a golden rose logo and the printed text "Rosa d'Abril" and "Sanar el alma", market stalls blurred behind, as if the hand were hesitating to throw it away, daylight. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/imma_dm3.jpg` Un brote verde en una maceta, en una ventana
  - Prompt: `Photo of a small green sprout in a terracotta pot on a windowsill, a sliver of curtain, soft light, a hopeful, quiet picture. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/pau_dm12.jpg` Niños empapados riendo, globos de agua reventados
  - Prompt: `Photo of soaked kids laughing among burst colourful water balloons on the grass of a village park, a monitor in a neck scarf laughing behind them, sunny. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/clara_d4.jpg` 🔎 El dibujo de la Laieta colgado en el local: casita con la **puerta azul**, un **perro enorme** marrón y estrellas; escrito con letra de niña «PER LA ALI» (D4)
  - Prompt: `Photo of a child's crayon drawing pinned with tape to a wall of a youth club: a little stone house drawn with grey rectangles, a big bright blue door, a huge brown dog beside it, much taller than the door, a lot of yellow stars in a dark blue sky, and across the top in childish capital letters "PER LA ALI". Paper slightly crumpled, other drawings partly visible at the edges. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/clara_dm9.jpg` Dibujos de niños pegados en una pared
  - Prompt: `Photo of children's drawings of animals, houses and suns stuck all over a wall of a youth club, a variety of colourful papers, some curled, a clothes line of drawings above. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/biel_d2.jpg` 🎭 Interior de piedra a oscuras con velas, un saco de dormir y latas; en un rincón se intuye una puerta azul desconchada (Can Pericot) (D2)
  - Prompt: `Dark photo, taken with flash on, inside an old room of rough dark basalt stone with a few lit candles stuck in bottles, a sleeping bag rolled out on the floor and several beer cans, in a far corner a peeling pale-blue wooden door barely visible, the feeling of a teenagers' hideout, grainy. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/biel_dm20.jpg` Los monitores del esplai con pañuelos al cuello en una explanada
  - Prompt: `Group photo of youth club monitors with colourful neck scarves in a meadow in the Vall de Bianya, arms around shoulders, a few kids at the edges, green hills behind. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/quim_dm14.jpg` Mesa de reunión con el programa de la Festa Major y bocadillos
  - Prompt: `Photo of a meeting table in a small council room with a printed village festival programme in Catalan, plates of bocadillos, plastic cups and papers, a window with a view of the village, flat light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/quim_dm60.jpg` Sardanas en la plaza Major del Hostalnou, vista desde el balcón del Ayuntamiento
  - Prompt: `Photo of sardana dancers in a circle in the main square of {hostalnou} seen from the town hall balcony, whose iron railing is visible at the edge, the square with plane trees and a bar terrace, a sunny festival morning. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/marc_dm8.jpg` Campo de trigo segado al atardecer
  - Prompt: `Photo of a harvested wheat field with round straw bales at sunset in the Vall de Bianya, a golden low light and the green hills in the distance, a tractor track in the stubble. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/marc_dm40.jpg` Un tractor verde recién lavado, brillando al sol
  - Prompt: `Photo of {marc}'s freshly washed green tractor shining in the sun next to a stone farmhouse, water still running off the mudguards, a bucket and a hose on the ground. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/laura_dm10.jpg` Girasoles en un campo, a contraluz
  - Prompt: `Photo of sunflowers in a field backlit by the setting sun, the petals glowing, a thin dirt path, pretty and a bit oversaturated by a phone filter. {movil} Aspect ratio 1:1.`
- [x] `gonpi/laura_dm30.jpg` Atardecer naranja sobre los tejados del Hostalnou
  - Prompt: `Photo of an orange sunset over the terracotta roofs of {hostalnou} from a terrace, a pot of flowers at the edge of the frame, a warm sky, soft filter. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/conxita_dm2.jpg` Imagen de «buenos días» con flores, taza de café y el texto «Bon dia» en letra cursiva
  - Prompt: `Cheesy forwarded "good morning" image: a bouquet of pink roses and daisies, a steaming cup of coffee on a lace doily, sparkles, and the text "Bon dia" in pink cursive with a drop shadow at the top, slightly pixelated like a meme shared on a messaging app. Aspect ratio 1:1.`
- [x] `gonpi/conxita_dm20.jpg` La calle del Pont con macetas en las ventanas
  - Prompt: `Photo of {calle_del_pont} by day: a narrow stone village street with flower pots on the windowsills and wooden balconies, geraniums, a cat sitting on a step, taken from a doorway by a neighbour. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/enric_d3.jpg` El perro Rocky (mestizo negro, collar rojo) lleno de barro, feliz (D3)
  - Prompt: `Photo of {rocky} covered in mud from nose to tail, standing in a vegetable patch with his tongue out, tail blurred from wagging, clearly very happy, a farmhouse behind. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/enric_dm5.jpg` Rocky dormido en un sofá
  - Prompt: `Photo of {rocky} sleeping curled up on a worn old sofa with a crochet blanket, his red collar visible, one paw twitching, warm afternoon light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/rafa_dm15.jpg` Un coche de autoescuela con la «L» en el techo
  - Prompt: `Photo of a white driving school car with a roof sign with a big "L" parked on a street in Olot, a learner's hand-drawn "mucho ánimo" sign on the dashboard, sunny. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/rafa_dm40.jpg` Una moto en un mirador del Collsacabra
  - Prompt: `Photo of a touring motorcycle parked at a viewpoint on the cliffs of the Collsacabra, a wide misty valley below, a helmet on the saddle, golden light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/hugo_d4.jpg` La hoja del resultado del examen teórico con un 28, sujetada con las dos manos (D4)
  - Prompt: `Photo of two hands holding a printed driving theory exam result sheet with the score "28" circled in pen and "APTO" written below, taken proudly from above, a table edge in the frame. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/hugo_dm10.jpg` Pantalla de un test de conducir con un 19 en rojo
  - Prompt: `Photo of a laptop screen of an online driving theory practice test showing a big red "19" and "No apto", a hand over the mouth of the person looking, a bedroom desk, a can of energy drink. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/dolors_dm18.jpg` Actores sentados en círculo con los textos de Lorca en las manos
  - Prompt: `Photo of amateur actors sitting in a circle on folding chairs in a village social centre, each with a printed script of a Lorca play in hand, some in black, cups of coffee on the floor, a director ({dolors}) in a colourful scarf gesturing. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/dolors_dm200.jpg` Foto antigua de un escenario de pueblo con el telón rojo, 1998
  - Prompt: `Old photo from 1998 of a small village theatre stage with a red velvet curtain half open and a painted backdrop of a Spanish courtyard, a date stamp in orange numbers in the lower right "98 6 21", slightly overexposed from the flash. Aspect ratio 1:1.`
- [ ] `gonpi/queralt_dm9.jpg` Queralt vestida de negro, de Bernarda, con un bastón
  - Prompt: `Photo of {queralt} dressed all in black as Bernarda Alba, a stern expression, holding a wooden cane, a bare stage and a white wall behind her, theatrical side light, red lipstick. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/queralt_dm40.jpg` La fachada del Institut del Teatre de Barcelona
  - Prompt: `Photo of the facade of a drama school building in Barcelona, a grey stone and glass modern building with a banner, a few students on the steps, a clear day. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/jan_dm6.jpg` Brindis con jarras de cerveza en un bar de Girona
  - Prompt: `Photo of a toast with beer mugs held together over a table in a bar in the old quarter of Girona at night, hands only and one blurred friend's face, warm lights and foam spilling. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/jan_dm18.jpg` Jan entre bastidores, sentado, con cara de aburrido
  - Prompt: `Photo of {jan} sitting on a flight case backstage with a script in his lap, pulling a bored face with one eyebrow raised, curtains and a clothes rail behind him. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/anna_dm5.jpg` Un examen de fracciones con un 7,5 en rojo, pegado en la nevera
  - Prompt: `Photo of a primary school fractions exam with a red "7,5" and a star drawn by a teacher, stuck on a fridge door with a magnet shaped like a lemon, other kid drawings around it, kitchen light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/anna_dm60.jpg` Las casas blancas de Cadaqués y el mar
  - Prompt: `Photo of the whitewashed houses of Cadaqués and its church on a hill above a calm blue Mediterranean bay, small boats at anchor, bright summer light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/silvia_dm3.jpg` Una masía reformada y luminosa con placas solares (no se parece a las de puerta azul)
  - Prompt: `Real estate photo of a bright renovated Catalan masia with a white wooden door, large glass windows, solar panels on the roof and a swimming pool, a neat green lawn, green hills, blue sky. It must look modern and clearly different from an old grey stone masia with a blue door. Aspect ratio 1:1.`
- [ ] `gonpi/silvia_dm30.jpg` Un niño de 11 años con equipación de fútbol en un campo de césped artificial
  - Prompt: `Photo of an 11-year-old boy in a red-and-white football kit and shin guards on an artificial turf pitch in Olot, the ball under his foot, a proud smile, floodlights switched on in the evening. {movil_texto} Aspect ratio 1:1.`

**Cuentas que sigue Alicia** (grupos, influencers y locales, todos inventados: camuflan el caso con lo que le gusta a ella; ver «Gustos» en [story/perfiles/alicia.md](story/perfiles/alicia.md)). Estilo de cuenta profesional: fotos cuidadas, carteles, carruseles con texto.

- [ ] `gonpi/nitsdagost_d3.jpg` Cartel del grupo: «Les Nits d'Agost · Fanals · Olot» (D3)
  - Prompt: `Indie-folk band gig poster, hand-drawn style: a crescent moon and stars over a night-blue sky, a small wooden house silhouette, text in an elegant hand-lettered font "Les Nits d'Agost" and below in smaller letters "Fanals · Olot", beige paper texture. Aspect ratio 1:1.`
- [ ] `gonpi/nitsdagost_dm30.jpg` Concierto en una sala de Girona desde el público, luces azules, móviles encendidos
  - Prompt: `Photo from the audience of a concert in a small venue in Girona, blue stage lights, a band of four on stage with an acoustic guitar and a cello, a forest of raised phones with their screens lit, silhouettes of heads. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/nitsdagost_dm50.jpg` Portada del single «Estels»: una ventana de madera abierta a un cielo estrellado
  - Prompt: `Single cover art: an old open wooden window with shutters onto a starry sky, seen from inside a dark room, the sill in the foreground, a soft blue-and-gold palette, delicate handwritten lettering "Estels" at the top. Aspect ratio 1:1.`
- [ ] `gonpi/nitsdagost_dm120.jpg` Estudio de grabación con guitarras y mantas
  - Prompt: `Photo of a cosy home recording studio with acoustic guitars on stands, wool blankets hung on the walls for sound, a microphone with a pop filter, cables on the floor, warm lamp light. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/juliavents_d5.jpg` Frase escrita a mano en una libreta: «No cal que ningú entengui per què te'n vas» (D5)
  - Prompt: `Photo of an open notebook with a handwritten line in black ink in Catalan: "No cal que ningú entengui per què te'n vas", slightly slanted handwriting, a pencil beside it, soft natural light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/juliavents_dm20.jpg` Júlia sentada en el suelo de una habitación pequeña con la guitarra
  - Prompt: `Photo of a 25-year-old Catalan female singer-songwriter with wavy dark hair sitting on the floor of a small bedroom with an acoustic guitar on her lap, window light, bare feet, a plant, a plain wall. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/juliavents_dm70.jpg` Cartel de gira con cuatro ciudades
  - Prompt: `Tour poster for a singer-songwriter: a pencil drawing of a guitar and a bird, a list of dates in a clean serif font "Girona · Olot · Vic · Manresa" with fictional dates, cream paper. Aspect ratio 1:1.`
- [ ] `gonpi/nova_d1.jpg` Portada del disco «NEÓN»: la cantante bajo luces violetas (D1, anuncio)
  - Prompt: `Pop album cover of a fictional singer: a female pop singer in a glossy jacket with her face half in shadow under violet and pink neon lights, a futuristic grid in the background, bold title "NEÓN" in glowing letters. Aspect ratio 1:1.`
- [ ] `gonpi/nova_dm15.jpg` Ensayo en un escenario enorme vacío
  - Prompt: `Photo of a huge empty concert stage during a rehearsal, purple stage lights beaming into smoke, a single microphone stand in the centre, empty seating stretching into the dark. {movil} Aspect ratio 1:1.`
- [ ] `gonpi/fanals_d2.jpg` Cartel del festival: farolillos en una calle de Olot de noche, primeros nombres (D2, anuncio)
  - Prompt: `Festival poster: paper lanterns in orange and yellow glowing along an old narrow street of Olot at night, text in an elegant font "Fanals · Festival de llum · Olot", and below a line of fictional band names in small caps. Aspect ratio 1:1.`
- [ ] `gonpi/fanals_dm300.jpg` Público con farolillos en una plaza, de noche
  - Prompt: `Photo of a crowd holding glowing paper lanterns in a town square of Olot at night, a church in the background, faces lit softly from below, a magical feeling. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/celfosc_d5.jpg` La Vía Láctea sobre la silueta de los volcanes (D5)
  - Prompt: `Astrophotography of the Milky Way arching over the silhouettes of the volcanic cones of the Garrotxa, a few lights of a village far below, deep blue sky and stars. Aspect ratio 1:1.`
- [ ] `gonpi/celfosc_dm15.jpg` Carrusel con un mapa dibujado de la Garrotxa y tres estrellas marcadas: Alta Garrotxa, Bassegoda y la subida a Bracons
  - Prompt: `Illustrated hand-drawn map of the Garrotxa region in dark blue and cream, with three gold star icons labelled in a clean font "Alta Garrotxa", "Bassegoda" and "Bracons", small mountain symbols, a compass rose. Aspect ratio 1:1.`
- [x] `gonpi/celfosc_dm45.jpg` Larga exposición con estelas de estrellas fugaces sobre una montaña
  - Prompt: `Long exposure astrophotography with several meteor trails over the Puig de Bassegoda, a dark sky with a few clouds lit by the moon, star trails slightly curved. Aspect ratio 1:1.`
- [x] `gonpi/nordenlla_d1.jpg` Aurora boreal verde y violeta sobre un pueblo nevado (D1)
  - Prompt: `Photo of a green and violet aurora borealis over a small snowy village in northern Norway, wooden houses with lit windows, snow in the foreground, stars. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/nordenlla_dm25.jpg` Cabaña de madera roja sola junto a un fiordo
  - Prompt: `Photo of a lonely red wooden cabin next to a Norwegian fjord, calm water and mountains, a grey-blue dawn light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/tintalenta_d6.jpg` Ilustración a tinta: una ventana con postigos, una manta y estrellas (D6)
  - Prompt: `Black ink illustration on white paper, fine pen hatching: a window with open wooden shutters in a thick stone wall, a folded blanket on the sill and a sky full of small stars outside, a very calm composition. Aspect ratio 1:1.`
- [x] `gonpi/tintalenta_dm10.jpg` Ilustración a tinta de una masía de piedra con la puerta abierta (puerta sin color)
  - Prompt: `Black ink illustration of a Catalan stone masia with its door open, detailed stonework and roof tiles, a path leading to the door, no colour at all, white paper, fine pen lines. Aspect ratio 1:1.`
- [x] `gonpi/tintalenta_dm40.jpg` Ilustración: chica de espaldas sentada en un muro mirando el cielo
  - Prompt: `Black ink illustration of a girl seen from behind with a ponytail, sitting on a dry stone wall and looking at a sky full of stars, fine hatching, white paper. Aspect ratio 1:1.`
- [ ] `gonpi/psicologia_d3.jpg` Carrusel de texto sobre fondo verde claro: «Señales de que un grupo te está aislando» (D3)
  - Prompt: `Instagram carousel cover slide on a light green background with clean sans-serif dark text: "Señales de que un grupo te está aislando", a small swipe arrow at the bottom right, minimalist. Aspect ratio 1:1.`
- [ ] `gonpi/psicologia_dm9.jpg` Fondo crema con la frase «Poner límites a tu familia no es dejar de quererla»
  - Prompt: `Instagram quote slide on a cream background with the text centred in a clean serif font: "Poner límites a tu familia no es dejar de quererla", a small handle at the bottom, minimalist. Aspect ratio 1:1.`
- [ ] `gonpi/psicologia_dm33.jpg` Fondo crema con la frase «Tu cuerpo no exagera. Te está avisando»
  - Prompt: `Instagram quote slide on a cream background with the text centred in a clean serif font: "Tu cuerpo no exagera. Te está avisando.", a small handle at the bottom, minimalist. Aspect ratio 1:1.`
- [ ] `gonpi/filsocial_d4.jpg` Una anciana en la puerta de una masía con una voluntaria joven (de espaldas, sin puerta azul) (D4)
  - Prompt: `Photo of an elderly Catalan woman in an apron at the white-painted wooden door of a stone farmhouse in the Garrotxa, talking to a young volunteer seen from behind with a ponytail and a backpack, a bag of groceries in her hand, a warm afternoon. The door is white or natural wood; it must NOT be blue. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/filsocial_dm28.jpg` Taller con alumnos sentados en círculo; se reconoce a Elena y a Alicia
  - Prompt: `Photo of a workshop with about twelve students sitting in a circle on chairs in a bright classroom, {elena} leading with her shoulder-length hair and glasses, and among the students {alicia} with a thick dark brown ponytail and faint freckles listening carefully; a flipchart in the background. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/protectora_d2.jpg` Perro grande, negro, con el hocico canoso, mirando a cámara (D2)
  - Prompt: `Photo of a big old black dog with a grey muzzle looking straight at the camera with a sad hopeful expression, sitting in a concrete kennel run at an animal shelter, a blanket and a metal bowl, soft light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/protectora_dm18.jpg` Perrita mestiza con un lazo saliendo con su familia
  - Prompt: `Photo of a small mixed-breed dog with a red bow leaving an animal shelter in the arms of a smiling young couple and a child, adoption day, a banner "Adopta" behind. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/marroig_d4.jpg` Mar Roig celebrando un punto en un pabellón lleno (D4)
  - Prompt: `Photo of {mar_roig} celebrating a point with a clenched fist and a shout in a packed sports hall, teammates running to her, the crowd blurred behind, a professional team in dark red kit. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/marroig_dm22.jpg` Mar Roig con el equipo juvenil de Olot; Alicia a su lado con la rodillera firmada
  - Prompt: `Photo of {mar_roig} in her pro kit posing with a girls' junior team in {pabellon}; next to her, {alicia} wearing number 7 holds up a black knee pad signed with a marker, smiling widely; a banner behind them. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/memes_d4.jpg` Meme: gato con cara de terror y el texto «quan la teva mare diu "ja en parlarem"» (D4)
  - Prompt: `Meme: a close-up of a terrified wide-eyed cat, with the caption in Catalan in white Impact text across the bottom: "quan la teva mare diu ""ja en parlarem""", and a plain dark background. Aspect ratio 1:1.`
- [ ] `gonpi/memes_d1.jpg` Meme: un tractor delante de una fila de coches en una carretera de curvas (D1)
  - Prompt: `Meme: a green tractor driving slowly in front of a long queue of cars on a winding mountain road through a forest, a driver with hands on the wheel in the first car, with a caption in white Impact text "Quan hi ha una corba i un tractor". Aspect ratio 1:1.`
- [ ] `gonpi/memes_dm6.jpg` Meme: skyline de Nueva York con el texto «Girona»
  - Prompt: `Meme: the New York skyline at night with the caption in white Impact text at the bottom "GIRONA" and above in smaller letters "Girona vista des del bus". Aspect ratio 1:1.`
- [ ] `gonpi/lacova_d5.jpg` Ejemplares de «Mujercitas» en el escaparate de la librería (D5)
  - Prompt: `Photo of several copies of "Mujercitas" (Little Women) arranged in the window of an old bookshop in Olot with a green awning and a handwritten sign "Recomanem", warm interior light, a reflection of the street. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/lacova_dm14.jpg` «Nada», de Carmen Laforet, sobre una mesa de madera con un café
  - Prompt: `Photo of the paperback novel "Nada" by Carmen Laforet on a wooden table next to a cup of coffee and a spoon, an open window with light, a bookmark, calm. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/granja_dm4.jpg` Mesas con estudiantes, apuntes y tazas de chocolate (anuncio)
  - Prompt: `Photo of marble café tables with students studying, notes and cups of thick Catalan hot chocolate with melindros on saucers in an old-style granja in Olot, mirrored walls, brass fittings, warm light. {movil_texto} Aspect ratio 1:1.`
- [x] `gonpi/granja_dm30.jpg` Bandeja de melindros recién hechos
  - Prompt: `Photo of a baking tray of freshly baked Catalan melindros ladyfingers with sugar on top, a wooden spoon, a bit of steam, a kitchen towel, warm light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/vintage_d2.jpg` Perchero con chaquetas de los 90: verdes, granates, pana (D2, anuncio)
  - Prompt: `Photo of a metal clothes rack with 90s jackets in green, burgundy and corduroy on wooden hangers in a second-hand shop in Olot, a neon sign "Vintage Olot" in the corner, retro posters. Aspect ratio 1:1.`
- [x] `gonpi/vintage_dm60.jpg` Una chaqueta verde de pana colgada sola (la de Alicia)
  - Prompt: `Photo of a single worn green corduroy jacket hanging alone on a wooden hanger against a white wall in a second-hand shop, a small price tag still on the sleeve, soft side light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/volcanica_d4.jpg` Cartel «2x1 días de entreno» con una pizza y un balón de vóley (D4, anuncio)
  - Prompt: `Advertising poster with a pizza and a volleyball side by side on a red table and the text in Catalan in bold white letters "2x1 dies d'entrenament", a small logo "La Volcànica" at the bottom, playful colours. Aspect ratio 1:1.`
- [ ] `gonpi/volcanica_dm11.jpg` Mesa larga con el equipo juvenil de vóley y pizzas
  - Prompt: `Photo of a long table in a pizzeria in Olot with a girls' volleyball team in club tracksuits eating pizzas and laughing, cans of soda, stacks of boxes, strong overhead light. {movil_texto} Aspect ratio 1:1.`
- [ ] `gonpi/cinema_d5.jpg` Cartel de la película inventada «La casa de los veranos»: una niña y su abuela en un porche (D5, anuncio)
  - Prompt: `Movie poster for a fictional film "La casa de los veranos": a young girl and her grandmother sitting on the porch of a stone farmhouse at golden hour, the title in a warm serif font at the bottom, a small "Cine Garrotxa" logo, painterly style. Aspect ratio 1:1.`
- [x] `gonpi/cinema_dm20.jpg` Palomitas y entradas sobre una butaca roja
  - Prompt: `Photo of a bucket of popcorn and two paper cinema tickets on a red velvet seat, a glowing screen blurred in the background. {movil_texto} Aspect ratio 1:1.`

## 4. Fotos de perfil (`media/photos/`)

Sirven de avatar en los chats y en Gonpi. Sin foto, se ve la inicial con el color del personaje. Cada una es un **retrato de cuenta** con la cara de su ficha (sección 0): si ya tienes la foto de referencia de ese personaje, adjúntala y el avatar saldrá con su misma cara.

- [ ] Chats: `mama`, `berta`, `jordi`, `marta`, `arnau`, `nuria`, `carla`, `mireia`, `pol`, `dani`, `sergi`, `aina`, `oriol`, `paula`, `elena`, `pilar`, `eric`, `sonia`, `judit`, `ona`, `iris`, `guia`, `teresa`, `ramon`, `gemma`, `xavier`, `roser`, `pep`, `lluisa`, `toni`, `fatima`, `claudia`, `imma`, `pau`, `clara`, `biel`, `quim`, `marc`, `laura`, `conxita`, `enric`, `rafa`, `hugo`, `dolors`, `queralt`, `jan`, `anna`, `silvia`
  - `photos/mama.jpg`: `Profile photo, head and shoulders, of {montse}: a faint serene smile, wearing a beige linen blouse, standing in a flower garden in front of a stone wall in L'Hostalnou de Bianya, taken by someone else at dusk with a cheap old phone held badly, very dim fading light and no flash, the lens focused on the flowers behind so that her face is soft and out of focus, motion blur from a shaky hand, heavy digital noise and grain, muddy dark shadows, a slightly distant look that does not quite reach the camera, a blurry low-quality low-light snapshot. {movil} Aspect ratio 1:1.`
  - `photos/berta.jpg`: `Profile photo, head and shoulders, of {berta} with her fixed serene smile and her intense calm gaze straight to the camera, a white loose blouse, taken at dawn with an old phone, dim blue-grey half-light before sunrise and no flash, hazy green hills behind her, hair in place, a little out of focus and slightly shaky, soft noisy low-light snapshot. {movil} Aspect ratio 1:1.`
  - `photos/jordi.jpg`: `Profile photo, head and shoulders, of {jordi} with an awkward friendly smile, wearing a navy fleece, a street of Barcelona with a tree and a balcony behind him, taken by someone else, slightly off-centre, daylight. {movil} Aspect ratio 1:1.`
  - `photos/marta.jpg`: `Profile photo, head and shoulders, of {marta} with a big warm smile, wearing a green apron, standing at her fruit stall in the covered market of Olot with crates of fruit behind her, bright market light. {movil} Aspect ratio 1:1.`
  - `photos/arnau.jpg`: `Front-camera smartphone selfie of {arnau} with a cheeky grin showing his braces, a gaming headset around his neck, sitting in a dark bedroom lit by the blue light of a screen, a poster half visible, a phone-flash look. {movil} Aspect ratio 1:1.`
  - `photos/nuria.jpg`: `Front-camera smartphone selfie, head and shoulders, of {nuria} at arm's length with a wide expressive smile and dimples, wearing a denim jacket and small gold hoop earrings, on a bridge over the Onyar river in Girona with colourful houses behind her, late afternoon light going dim, wind in her hair, slightly out of focus. {movil} Aspect ratio 1:1.`
  - `photos/carla.jpg`: `Front-camera smartphone selfie of {carla} at arm's length with a confident pose and a pout, a butterfly clip in her hair, wearing a black top, a pastel wall behind her, ring light reflection in her eyes. {movil} Aspect ratio 1:1.`
  - `photos/mireia.jpg`: `Front-camera smartphone selfie of {mireia} at arm's length, phone held a bit too close, with a nervous sweet smile, wearing a chunky cream knit jumper, a cosy bedroom with fairy lights softly blurred behind her. {movil} Aspect ratio 1:1.`
  - `photos/pol.jpg`: `Profile photo of {pol} in a grey hoodie, leaning on an old grey Volkswagen Golf on a road in the Garrotxa, hands in his pockets, a hint of a smile, overcast daylight. {movil} Aspect ratio 1:1.`
  - `photos/dani.jpg`: `Front-camera smartphone selfie of {dani} with a smirk, wearing a black leather jacket, sitting on a black motorcycle at night in Olot under a street lamp, a bit of flash on his face. {movil} Aspect ratio 1:1.`
  - `photos/sergi.jpg`: `Profile photo of {sergi} with a serious look, a plain shirt, a library background with shelves, a neutral light. {movil} Aspect ratio 1:1.`
  - `photos/aina.jpg`: `Profile photo of {aina} holding a tabby cat against her chest, smiling with her eyes closed, a bedroom with a bookshelf behind. {movil} Aspect ratio 1:1.`
  - `photos/oriol.jpg`: `Profile photo of {oriol} wearing a bike helmet and a headlamp on it, a bright smile, on a mountain road in the Garrotxa at dusk, a sports jersey, orange light behind him. {movil} Aspect ratio 1:1.`
  - `photos/paula.jpg`: `Front-camera smartphone selfie of {paula} holding a paper cup of coffee, a sarcastic smile, a classroom corridor behind her. {movil} Aspect ratio 1:1.`
  - `photos/elena.jpg`: `Profile photo of {elena} in a plain blouse, professional and kind, a classroom with a whiteboard blurred behind her, a window light. {movil} Aspect ratio 1:1.`
  - `photos/pilar.jpg`: `Profile photo of {pilar} in a flour-dusted white apron with a stern but kind look, standing in front of the wood-fired oven of {forn}, orange glow from the oven. {movil} Aspect ratio 1:1.`
  - `photos/eric.jpg`: `Front-camera smartphone selfie of {eric} with a goofy shy smile, flour on his cheek, in a bakery apron, shot in {forn} with the baskets of bread behind him. {movil} Aspect ratio 1:1.`
  - `photos/sonia.jpg`: `Profile photo of {sonia} with a whistle around her neck and a clipboard, in a tracksuit in {pabellon}, a confident smile. {movil} Aspect ratio 1:1.`
  - `photos/judit.jpg`: `Front-camera smartphone selfie of {judit} holding a volleyball against her hip, laughing loudly, a sports hall behind her. {movil} Aspect ratio 1:1.`
  - `photos/ona.jpg`: `Front-camera smartphone selfie of {ona}, arm slightly extended and the phone held a bit too close and slightly below her face, a gentle smile, wearing a blue and orange volleyball club tracksuit top, a sports hall car park behind her at dusk, dim low light with no flash, her face only lit by the dying evening sky and the screen, slightly out of focus. {movil} Aspect ratio 1:1.`
  - `photos/iris.jpg`: `Front-camera smartphone selfie, head and shoulders, of {iris}, phone held low and close, wearing white loose clothes with a faint sad smile, slightly blurry and dim as if taken in secret in a bathroom or a small room, no flash, grain. {movil} Aspect ratio 1:1.`
  - `photos/guia.jpg`: `Profile photo, head and shoulders, of {ignasi} with a warm practised smile and an unblinking gaze, a white linen shirt, a wooden bead bracelet, soft golden light in the garden of a restored stone masia, a clean coach-style portrait with a shallow depth of field. {movil} Aspect ratio 1:1.`
  - `photos/teresa.jpg`: `Profile photo of {teresa} in a cardigan among library bookshelves, a gentle smile, warm light. {movil} Aspect ratio 1:1.`
  - `photos/ramon.jpg`: `Profile photo of {ramon} in a flat cap and a cardigan, standing in a village square with a bench behind him, a big smile. {movil} Aspect ratio 1:1.`
  - `photos/gemma.jpg`: `Profile photo of {gemma} holding a vermouth glass with an olive, on a café terrace in a Catalan town square, a bright smile, midday sun. {movil} Aspect ratio 1:1.`
  - `photos/xavier.jpg`: `Profile photo of {xavier} in a dark shirt conducting with one raised hand in a stone church, eyes closed in concentration, side light from a small window. {movil} Aspect ratio 1:1.`
  - `photos/roser.jpg`: `Profile photo of {roser} in a floral blouse on her stone balcony with red geraniums behind her, a sharp friendly look, daylight. {movil} Aspect ratio 1:1.`
  - `photos/pep.jpg`: `Profile photo of {pep} in a straw hat among tomato plants in his vegetable garden, a proud smile under his moustache, a basket on his arm. {movil} Aspect ratio 1:1.`
  - `photos/lluisa.jpg`: `Profile photo of {lluisa} in a green volunteer vest, a kind tired smile, boxes of food behind her in a warehouse. {movil} Aspect ratio 1:1.`
  - `photos/toni.jpg`: `Profile photo of {toni} in a cycling cap, a green volunteer vest over a T-shirt, standing next to a white delivery van, a friendly smile. {movil} Aspect ratio 1:1.`
  - `photos/fatima.jpg`: `Profile photo of {fatima} holding a small glass of mint tea, a warm smile, a hospital corridor softly blurred behind her. {movil} Aspect ratio 1:1.`
  - `photos/claudia.jpg`: `Profile photo of {claudia} in a peaceful yoga pose on a hillside meadow, the green volcanic hills of the Garrotxa behind her, morning light. {movil} Aspect ratio 1:1.`
  - `photos/imma.jpg`: `Profile photo of {imma} near a window with a hesitant smile, a soft light on one side of her face, a plain cardigan. {movil} Aspect ratio 1:1.`
  - `photos/pau.jpg`: `Profile photo of {pau} with a camp neck scarf and an acoustic guitar slung on his back, an easy smile, a forest clearing behind. {movil} Aspect ratio 1:1.`
  - `photos/clara.jpg`: `Profile photo of {clara} showing both hands covered in paint to the camera, laughing, in a youth club room with drawings on the walls. {movil} Aspect ratio 1:1.`
  - `photos/biel.jpg`: `Front-camera smartphone selfie of {biel} with a backwards cap and a cheeky smile, a supermarket vest, a street of the village behind him. {movil} Aspect ratio 1:1.`
  - `photos/quim.jpg`: `Profile photo of {quim} in a shirt with rolled-up sleeves in front of the small stone town hall of {hostalnou}, a friendly official smile. {movil} Aspect ratio 1:1.`
  - `photos/marc.jpg`: `Profile photo of {marc} sitting proudly on a green tractor in the Vall de Bianya, sunburnt cheeks, a cap, a green meadow behind. {movil} Aspect ratio 1:1.`
  - `photos/laura.jpg`: `Profile photo of {laura} holding a sunflower in front of a field, an eager sweet smile, wavy honey-blond hair blowing, golden light. {movil} Aspect ratio 1:1.`
  - `photos/conxita.jpg`: `Profile photo of {conxita} leaning out of her window with pots of flowers on the sill, a curious smile, a stone wall around the window. {movil} Aspect ratio 1:1.`
  - `photos/enric.jpg`: `Profile photo of {enric} with his dog {rocky} sitting next to him, both looking at the camera, in a vegetable garden, an overcast sky. {movil} Aspect ratio 1:1.`
  - `photos/rafa.jpg`: `Profile photo of {rafa} in aviator sunglasses and a polo shirt, standing next to a white driving school car with a rooftop "L", a thumbs-up. {movil} Aspect ratio 1:1.`
  - `photos/hugo.jpg`: `Front-camera smartphone selfie of {hugo} with a goofy smile in a hoodie, a dim bedroom with a gaming poster behind him. {movil} Aspect ratio 1:1.`
  - `photos/dolors.jpg`: `Profile photo of {dolors} in an empty theatre, sitting in the front row of red seats with a script on her knees, a colourful scarf, a dramatic side light. {movil} Aspect ratio 1:1.`
  - `photos/queralt.jpg`: `Profile photo of {queralt} in theatrical side light, her chin lifted, red lipstick, a dark backdrop. {movil} Aspect ratio 1:1.`
  - `photos/jan.jpg`: `Front-camera smartphone selfie of {jan} pulling a funny face with his tongue out and crossed eyes, a backstage curtain behind him. {movil} Aspect ratio 1:1.`
  - `photos/anna.jpg`: `Profile photo of {anna} in a nurse uniform, smiling, a hospital corridor behind her, neutral light. {movil} Aspect ratio 1:1.`
  - `photos/silvia.jpg`: `Profile photo of {silvia} in a navy blazer in front of an old stone house with a "Se vende" sign, a polished professional smile. {movil} Aspect ratio 1:1.`
- [ ] Solo Gonpi: `alicia`, `casal` (la rosa dorada), `voley`, `excursions`, `hostalnou` (escudo del pueblo), `forn`, `joan`, `lectura`, `coral`, `aliments`, `esplai`, `teatre`, `autoescola`
  - `photos/alicia.jpg`: `Candid front-camera smartphone selfie, head and shoulders, of {alicia} with a shy half smile and a slightly tired look, loose strands framing her face, an oversized grey hoodie under a worn green corduroy jacket, a thin red string bracelet visible on her left wrist as her hand holds the phone, sitting by an old wooden window with shutters in a stone house in the Vall de Bianya at dusk, dim light from the window and the phone screen only, no flash, soft and a bit out of focus, a noisy grainy low-light selfie, an ordinary amateur photo. {movil} Aspect ratio 1:1.`
  - `photos/casal.jpg`: `Logo of a spiritual association: an elegant golden rose with fine line petals on a cream background, a thin circle around it, minimal and refined, no text. Aspect ratio 1:1.`
  - `photos/voley.jpg`: `Logo of a fictional local volleyball club "Vòlei Olot" in blue and orange: a stylised volleyball with a volcano silhouette, a rounded shield shape, bold sans-serif lettering. Aspect ratio 1:1.`
  - `photos/excursions.jpg`: `Logo of a hiking club: a simple hiking boot and three green rounded volcanic hills inside a circle, in dark green and cream, flat vector style. Aspect ratio 1:1.`
  - `photos/hostalnou.jpg`: `Fictional coat of arms of a Catalan village, simple heraldic design: a shield with a stone bridge, a plane tree and a small tower, in red, yellow and blue, a neat vector style, no text. Aspect ratio 1:1.`
  - `photos/forn.jpg`: `Logo of a traditional bakery "Forn Can Batlle 1964" with a wheat ear inside a circle, in brown and cream, a retro badge style, the text "Forn Can Batlle" and "1964" clearly legible. Aspect ratio 1:1.`
  - `photos/joan.jpg`: `Profile photo of a 45-year-old man with a sad look, plain grey background, a creased shirt, a soft flat light like an old ID photo. {movil} Aspect ratio 1:1.`
  - `photos/lectura.jpg`: `Logo of a library book club: an open book with a small heart, in purple and cream, flat vector. Aspect ratio 1:1.`
  - `photos/coral.jpg`: `Logo of a village choir: musical notes forming a circle, in blue and cream, flat vector. Aspect ratio 1:1.`
  - `photos/aliments.jpg`: `Logo of a food bank: a green heart holding a food can, white background, flat vector. Aspect ratio 1:1.`
  - `photos/esplai.jpg`: `Logo of a youth club: a small tent under a smiling sun in orange and yellow, flat vector. Aspect ratio 1:1.`
  - `photos/teatre.jpg`: `Logo of an amateur theatre group: comedy and tragedy masks in purple and cream, flat vector. Aspect ratio 1:1.`
  - `photos/autoescola.jpg`: `Logo of a driving school: a small car and a big letter "L", in blue and white, flat vector. Aspect ratio 1:1.`
- Cuentas que sigue Alicia (ver la sección 3):
- [ ] `nitsdagost` Logo del grupo: una luna dibujada a mano sobre azul noche
  - Prompt: `Band logo: a hand-drawn crescent moon with a thin outline on a night-blue background, a few tiny stars, no text. Aspect ratio 1:1.`
- [ ] `juliavents` Retrato de una cantautora de unos 25 años con guitarra, luz de ventana
  - Prompt: `Portrait of a 25-year-old Catalan female singer-songwriter with wavy dark hair and an acoustic guitar, sitting by a window with a soft natural light, a quiet gaze to the side, a plain wall. {movil} Aspect ratio 1:1.`
- [ ] `nova` Retrato pop de una cantante con luz de neón violeta
  - Prompt: `Pop portrait of a fictional female singer in profile under violet and pink neon light, glossy makeup, a dark background. Aspect ratio 1:1.`
- [ ] `fanals` Logo de un festival: un farolillo naranja
  - Prompt: `Festival logo: an orange paper lantern glowing on a dark blue background, flat style, no text. Aspect ratio 1:1.`
- [ ] `celfosc` Silueta de un telescopio bajo la Vía Láctea
  - Prompt: `Silhouette of a telescope on a tripod under the Milky Way above volcanic hills, deep blue sky, no text. Aspect ratio 1:1.`
- [ ] `nordenlla` Aurora verde sobre un fiordo
  - Prompt: `A green aurora over a still fjord with the silhouettes of mountains, stars, a calm night. Aspect ratio 1:1.`
- [ ] `tintalenta` Una plumilla manchada de tinta sobre papel
  - Prompt: `An ink-stained dip pen lying on a sheet of white paper, a drop of black ink and a few small drawn stars, soft light. Aspect ratio 1:1.`
- [ ] `psicologia` Retrato amable de una psicóloga de unos 35 años, fondo verde claro
  - Prompt: `Friendly portrait of a 35-year-old female psychologist with shoulder-length brown hair and a warm smile, a light green background, a neutral blazer. {movil} Aspect ratio 1:1.`
- [ ] `filsocial` Logo: un ovillo de lana con un hilo que une dos casas
  - Prompt: `Logo: a ball of wool with a thread joining two little houses with pointed roofs, in warm orange and cream, flat vector. Aspect ratio 1:1.`
- [ ] `protectora` Logo: huella de perro naranja
  - Prompt: `Logo of an animal shelter: an orange dog paw print with a small heart in the central pad, white background, flat vector. Aspect ratio 1:1.`
- [ ] `marroig` Jugadora de vóley con el 7, colocando
  - Prompt: `Photo of {mar_roig} wearing number 7, setting the ball with both hands above her forehead in mid-game in a professional sports hall, a blurred crowd behind. {movil_texto} Aspect ratio 1:1.`
- [ ] `memes` Un tractor con gafas de sol
  - Prompt: `A green tractor wearing a pair of black sunglasses, meme style, on a sunny country road, a flat comic rendering. Aspect ratio 1:1.`
- [ ] `lacova` Fachada de una librería antigua con toldo
  - Prompt: `Facade of an old bookshop with a dark green awning and a wooden window full of books in a street of Olot, a sign "La Cova", warm light. Aspect ratio 1:1.`
- [ ] `granja` Taza de chocolate espeso con melindros
  - Prompt: `A cup of thick hot chocolate with a spoon standing in it and two Catalan melindros ladyfingers on a saucer, a marble table, soft light. Aspect ratio 1:1.`
- [ ] `vintage` Perchero con chaquetas de colores
  - Prompt: `A clothes rack with colourful vintage jackets on wooden hangers in a bright shop, a retro neon sign. Aspect ratio 1:1.`
- [ ] `volcanica` Pizza saliendo de un horno de leña
  - Prompt: `A pizza coming out of a wood-fired oven on a long wooden peel, bubbling cheese, flames in the background. Aspect ratio 1:1.`
- [ ] `cinema` Fachada de un cine de pueblo con el letrero iluminado
  - Prompt: `Facade of a small-town cinema with an illuminated vertical sign "CINE GARROTXA" at dusk, a poster case beside the entrance. Aspect ratio 1:1.`
- [ ] `wallpaper` Fondo de pantalla del móvil de Alicia: ella y «Las de siempre» (Núria, Carla y Mireia), en vertical. La app lo recorta a pantalla completa bajo un velo oscuro, así que las caras van en la mitad de arriba. Referencias: sus fotos de `media/photos/`.
  - `photos/wallpaper.jpg`: `Vertical front-camera smartphone group selfie of four 18-year-old friends squeezed together, taken at arm's length by {nuria} from slightly above, each one with a different silly expression and a different hairstyle (same hair colours as in the references, only the style changes): {nuria} in her denim jacket laughing with her mouth wide open and her eyes squeezed shut, her long light brown hair gathered in a high messy bun with loose strands; {carla} in a black top with her butterfly hair clip sticking her tongue out, her blonde hair in two loose braids; {mireia} in a chunky cream knit jumper with a surprised open mouth and raised eyebrows, her curly black hair tied up in a high puffy ponytail, round glasses a little crooked; and {alicia} in her worn green corduroy jacket over a grey hoodie with her eyes closed and a big smile, her dark brown hair loose and tousled around her shoulders. They sit on the stone steps of a fountain in a small square of Olot at dusk, volcanic green hills and an old street lamp behind them, dim fading light, no flash. The whole picture is a bit out of focus and soft, with slight motion blur from the shaky arm, so the faces are not sharp, and strong grain. All four faces in the upper half of the picture and close to the camera. {movil} Aspect ratio 9:16.`

- [ ] Sin foto, a propósito: `laia`, `central`, `rosalia`, `desconocido` y `banco` (números desconocidos, la radio y los avisos)

Nombre: `photos/<id>.jpg`. Después se añade `"photo": "photos/<id>.jpg"` en `characters.json` (chats) o en `gonpi.json` (cuentas).

## 5. ~~Pregunta final de la Policía (`media/places/`)~~

Ya no se usan: la pregunta final ahora se responde en un mapa (`police/case.json` guarda el punto exacto). Se pueden borrar.

- [ ] `places/estacion_olot.jpg`
- [ ] `places/barcelona.jpg`
- [ ] `places/girona.jpg`
- [ ] `places/casa_pol.jpg`
- [ ] `places/santa_margarida.jpg`
- [ ] `places/mas_rosalia.jpg`. 🔎 Mejor que **no** se vea la puerta azul: el jugador tiene que llegar a ella por las pistas, no por esta foto.

## 6. Pendiente de código (no es multimedia, pero sin esto lo de arriba no se ve)

- [ ] Crear `gonpi/gonpi.json` a partir de [GONPI.md](GONPI.md), con las rutas de la sección 3.
- [x] El motor ya publica en Gonpi por días: usa `"at": "D3 21:00"` en publicaciones y comentarios.
- [x] El chat ya muestra fotos (`#image:`) y se abren a pantalla completa al tocarlas. Los vídeos todavía no: el «vídeo» de Arnau del D1 es ahora un meme (imagen).
- [ ] El apagón del D6 (18:00-22:00): de momento solo es el efecto `blackout` y los mensajes de Laia de `d6_apagon` (laia.ink). Ningún otro chat escribe en esa franja, salvo el grupo familiar, cuyos mensajes se leen al recuperar la conexión.

## 7. Fotos dentro del chat (`media/chat/`)

Las muestra la burbuja del mensaje que lleva `#image:`. Mientras no exista el archivo, solo se ve el texto.

- [x] `chat/nuria_mas.jpg` 🔎 Núria, D6: foto antigua de Alicia (unos 12 años) y su yaya delante de la **puerta azul** del mas. Se ve que es una foto de álbum, fotografiada con el móvil. El texto de la parte de atrás, «mas de la rosalia. estiu 2012», lo cuenta Núria en el chat.
  - Prompt: `Smartphone photo, taken by Núria a few minutes ago, of an old printed family album photo lying on a table: {alicia_12} stands in front of {mas_rosalia} next to {merce}, who has an arm around her shoulders and a big laugh; the bright blue door and a part of the carved stone lintel are visible behind them, the round stone well at their side, green Garrotxa forest behind. The print has slightly faded colours, a soft glare from the album's plastic sleeve crossing one corner, a visible edge of the album page, a thumb at one side, slightly tilted framing. The lintel text may be partly legible but no other text. Aspect ratio 3:4.`
- [ ] `chat/arnau_meme.jpg` Arnau, D1: meme sobre Berta en modo zen (luz y amaneceres). Ahora hay un placeholder con texto estilo meme
  - Prompt: `Funny reaction meme image: a serene woman in white sitting cross-legged at sunrise with glowing light rays behind her, eyes closed, hands in a mudra, exaggerated halo effect, the top caption in white Impact capital letters with a black outline "BERTA EN MODO ZEN" and the bottom caption "LUZ Y AMANECERES". Slightly compressed and low-res, like a meme forwarded through many chats. Aspect ratio 1:1.`

## 8. Pendiente: publicaciones de Gonpi según el final

`gonpi/hostalnou_d7.jpg` (final bueno) y `gonpi/berta_d7.jpg` (final malo) ya tienen imagen provisional, pero **no están en `gonpi.json`**: Gonpi todavía no sabe qué final ha ocurrido, y saldrían las dos. Hace falta que una publicación pueda depender de una variable de la historia (por ejemplo `"if": "caso_resuelto"`).

## 9. Fotos policiales (`police/resenas/`)

Salen en Multimedia y en la ficha de Policía cuando el jugador lee la ficha. Hasta que existan, la app dibuja una silueta. La placa con el id la pinta la app encima: **no la pongas en la imagen**.

Cada persona va en **dos** imágenes (frente y perfil) que deben ser la misma: genera primero la de frente y adjúntala en la de perfil.

- [ ] `dani_frontal.jpg` · `dani_perfil.jpg` (placa OLOT · 004417)
  - Prompt (frente): `Police booking photo, front view, head and upper chest: {dani_20} in a plain grey t-shirt, a neutral tired expression looking straight into the lens, standing in front of a pale wall with horizontal height-chart lines, flat harsh fluorescent light with no shadows, an unflattering official ID photo look, no text, no number board. Aspect ratio 4:5.`
  - Prompt (perfil): `Police booking photo, right profile view, head and upper chest: {dani_20} in a plain grey t-shirt, looking straight ahead to the left of the frame, standing in front of a pale wall with horizontal height-chart lines, flat harsh fluorescent light with no shadows, an unflattering official ID photo look, no text, no number board. Aspect ratio 4:5.`
- [ ] `marc_frontal.jpg` · `marc_perfil.jpg` (placa OLOT · 002961). Hace un año, tras la tractorada
  - Prompt (frente): `Police booking photo, front view, head and upper chest: {marc_18} in a worn checked flannel shirt, a stubborn frown looking into the lens, in front of a pale wall with horizontal height-chart lines, flat harsh fluorescent light with no shadows, an unflattering official ID photo look, no text, no number board. Aspect ratio 4:5.`
  - Prompt (perfil): `Police booking photo, right profile view, head and upper chest: {marc_18} in a worn checked flannel shirt, looking straight ahead to the left of the frame, in front of a pale wall with horizontal height-chart lines, flat harsh fluorescent light with no shadows, an unflattering official ID photo look, no text, no number board. Aspect ratio 4:5.`
- [ ] `ignasi_frontal.jpg` · `ignasi_perfil.jpg` (placa OLOT · 000311). Hace once años: barba ya canosa, no del todo blanca
  - Prompt (frente): `Police booking photo, front view, head and upper chest: {ignasi_50} in a white linen shirt, a calm unblinking gaze straight into the lens, in front of a pale wall with horizontal height-chart lines, flat harsh fluorescent light with no shadows, an unflattering official ID photo look, the beard greying but not yet white, no text, no number board. Aspect ratio 4:5.`
  - Prompt (perfil): `Police booking photo, right profile view, head and upper chest: {ignasi_50} in a white linen shirt, looking straight ahead to the left of the frame, in front of a pale wall with horizontal height-chart lines, flat harsh fluorescent light with no shadows, an unflattering official ID photo look, the beard greying but not yet white, no text, no number board. Aspect ratio 4:5.`
