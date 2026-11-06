# Auge — an emotional screensaver

**Auge** (German for *eye*) is a generative screensaver for Android and the web. A single, photorealistic human eye opens out of white (or black), looks, blinks, holds an expression — and closes again. Every time it reopens, it is a different eye: new iris, new lid shape, new lashes, new expression. No eye ever returns.

## About the work

A screensaver is perhaps the most emotionless genre of software: something that runs while nobody is looking. *Auge* reverses this. When the phone is idle, it begins to look back.

The work operates on several overlapping ambiguities:

- **Being watched.** The eye seeks eye contact. Running on a phone, it produces the familiar sensation of being observed — by a device that is otherwise silent.
- **The device as observer.** The image makes visible what usually stays invisible: phones are built to sense, register and evaluate their surroundings. Here the device shows a face to that function. *(Note: Auge itself does not access the camera and collects no data. It only performs the gesture of looking.)*
- **Emotion as projection.** The eye does not *have* emotions; it displays muscle configurations that human viewers read as emotions. Whether the screensaver is "emotional" — in the sense of feeling, or in the sense of moving its audience — is left open. The meaning is produced by the person looking at it.
- **Transience.** Each eye exists for a single *Augenblick* (German: "moment", literally "eye-glance") and is then gone for good.

## Scientific basis

The expressive behaviour of the eyes was designed with reference to research on facial expression and eye perception. It is an artistic translation informed by this literature — **not** an empirically validated model. Whether viewers recognise the intended expressions has not been tested.

| Aspect | Implementation | Reference |
|---|---|---|
| The eye region alone conveys complex mental states | Only the eye is shown; skin and face dissolve | Baron-Cohen et al. (2001) |
| Expressions as muscle actions | Expressions are built from FACS action units of the eye region: AU1 inner brow raiser, AU4 brow lowerer, AU5 upper lid raiser, AU6 cheek raiser, AU7 lid tightener | Ekman & Friesen (1978) |
| Genuine smile | "Joy" and "Mona Lisa" use AU6 (raised lower lid, crow's feet) | Ekman, Davidson & Friesen (1990) |
| Pupil and arousal | Pupils dilate with fear and interest | Hess (1965) |
| Dynamics aid recognition | Expressions set in at individual speeds (onset → apex) | Krumhuber, Kappas & Manstead (2013) |
| The Mona Lisa | A deliberately ambiguous, slow, low-intensity smile with mostly direct gaze | Livingstone (2000); Horstmann & Loth (2019) |
| Attribution of intention | Minimal cues are sufficient for viewers to perceive agency | Heider & Simmel (1944) |

### Expressions

`neutral` · `Mona Lisa` · `joy` · `sadness` · `fear` · `surprise` · `anger` · `suspicion` · `fatigue` · `melancholy`

Each expression also defines its gaze behaviour (share of direct eye contact, looking down or away), blink rate, pupil size and moistness of the eye.

## Features

- Physically based rendering in a single WebGL shader: refracting cornea, procedurally generated iris (fibres, crypts, collarette, limbal ring), sclera with blood vessels at three scales, anatomically asymmetric lids
- Eight base eye shapes (almond, round, narrow, hooded, downturned, feline, monolid, deep-set), blended and varied for every eye
- Individually varied lashes that dissolve into single pixels and swing with spring–mass inertia
- Natural blinks: the upper lid closes laterally first; the lower lid barely moves
- Light mode (eye embedded in white) and dark mode (black background, light lashes)
- Adaptive rendering resolution for smooth playback on phones

## Installation (Android)

1. Download **`Auge.apk`** from the [latest release](../../releases/latest).
2. Open the file on your phone and allow installation from this source when asked.
3. Start the app **Auge**. **Double-tap** to switch between light and dark.
4. To use it as a screensaver: *Settings → Display → Screen saver → Auge* (menu names vary by manufacturer). The screensaver uses the last mode chosen in the app.

Requires Android 7.0 or newer. Also runs on Android TV. On Google TV, third-party screensavers may need to be activated via ADB:
`adb shell settings put secure screensaver_components io.github.krtrttx.auge/.AugeDream`

## Web version

Open [`app/src/main/assets/auge.html`](app/src/main/assets/auge.html) in a desktop browser (click or press **F** for fullscreen). URL parameters, combinable with `&`:

| Parameter | Effect |
|---|---|
| `?dark` | black background, light lashes |
| `?e=0` … `?e=9` | fixed expression (order as listed above) |
| `?y=0.3` | vertical eye position (fraction of screen height) |
| `?q=0.7` | fixed render resolution instead of adaptive |
| `?kiosk` | hides the fullscreen hint (for installations) |

## Building from source

Push to `main` builds the APK automatically (Actions → latest run → Artifacts). Pushing a tag such as `v1.0.1` additionally publishes a release. Locally: open the project in Android Studio and run *Build → Build APK(s)*.

## Credits

Concept and artistic direction: **Rico Graupner**
Code developed in collaboration with Claude (Anthropic).

## License

**Noncommercial use only.** Auge is licensed under the [PolyForm Noncommercial License 1.0.0](LICENSE.md). You may use, study and modify it for any noncommercial purpose. Commercial use — including exhibition or installation for commercial purposes — requires a separate license from the author.

Required Notice: Copyright (c) 2026 Rico Graupner (https://github.com/krtrttx)

## References

Baron-Cohen, S., Wheelwright, S., Hill, J., Raste, Y., & Plumb, I. (2001). The "Reading the Mind in the Eyes" Test revised version: A study with normal adults, and adults with Asperger syndrome or high-functioning autism. *Journal of Child Psychology and Psychiatry, 42*(2), 241–251.

Ekman, P., Davidson, R. J., & Friesen, W. V. (1990). The Duchenne smile: Emotional expression and brain physiology II. *Journal of Personality and Social Psychology, 58*(2), 342–353.

Ekman, P., & Friesen, W. V. (1978). *Facial Action Coding System: A technique for the measurement of facial movement*. Consulting Psychologists Press.

Heider, F., & Simmel, M. (1944). An experimental study of apparent behavior. *The American Journal of Psychology, 57*(2), 243–259.

Hess, E. H. (1965). Attitude and pupil size. *Scientific American, 212*(4), 46–54.

Horstmann, G., & Loth, S. (2019). The Mona Lisa illusion—Scientists see her looking at them though she isn't. *i-Perception, 10*(1).

Krumhuber, E. G., Kappas, A., & Manstead, A. S. R. (2013). Effects of dynamic aspects of facial expressions: A review. *Emotion Review, 5*(1), 41–46.

Livingstone, M. S. (2000). Is it warm? Is it real? Or just low spatial frequency? *Science, 290*(5495), 1299.
