# Guitar Pro Academy

A Kotlin + Jetpack Compose Android project scaffold: every screen from the spec
exists as a real, navigable destination with working state — not a mockup.

## Open the project

1. Install [Android Studio](https://developer.android.com/studio) (Koala or newer).
2. `File → Open` and select this folder.
3. Let Gradle sync (Android Studio will offer to generate the Gradle wrapper
   automatically if it's missing — accept that prompt).
4. Run on an emulator or device with **API 26+**.

## What's genuinely functional right now

- **Navigation**: every screen in the spec is a real `NavHost` destination, wired
  from the bottom nav and from every button that should lead somewhere.
- **Room database**: lesson completion, XP, coins, streaks, favorites, and quiz
  results persist locally and survive app restarts.
- **XP / Level / Streak system**: real formulas in `ProgressRepository`
  (200 XP per level; streak increments once per new calendar day of practice
  and resets if a day is skipped).
- **Guitar Tuner**: uses `AudioRecord` + a YIN-style autocorrelation pitch
  detector (`audio/PitchDetector.kt`) — no fake needle animation, it reacts to
  actual sound.
- **Metronome**: a self-correcting timing loop (`audio/MetronomeEngine.kt`)
  using `ToneGenerator`, with tap-tempo, beat accents, and a flash indicator.
- **Practice timer, chord library (32 real chords with diagrams), 45-lesson
  curriculum across 3 paths, quiz engine with real scoring, songs, videos,
  achievements** — all backed by typed data classes in `data/model` and sample
  content in `data/repository`, so swapping in a live backend later means
  changing the repository layer, not the UI.

## What's intentionally stubbed, and why

Some things in the original spec need infrastructure this environment can't
provide (a backend, ad network credentials, payment processing, ML models).
These are wired with real UI and clear extension points rather than faked:

| Feature | Current state | To make it real |
|---|---|---|
| Cloud auth / accounts | Local-only login form, no real backend | Add Firebase Auth or your own API in `ui/screens/auth` |
| Admin panel content management | Local toggle switches (session-only) | Point `AdminLessonsTab`/`AdminSongsTab` at a real backend |
| AdMob ads | Not included | Add the Play Services Ads SDK + your AdMob unit IDs |
| Premium subscription / IAP | UI present, no purchase flow | Add Google Play Billing Library |
| AI Practice Coach | Not implemented | Would call an LLM API with the user's `ProgressRepository` data |
| Video/audio playback | UI + controls present, URLs point to placeholder CDN paths | Wire `ExoPlayer` and host real media files |
| 500+ quiz questions | 12 real sample questions across 3 paths | Extend `QuizRepository.questions` |
| Cloud sync / crash reporting / analytics | Not included | Add Firebase Crashlytics + Analytics |

## Project structure

```
app/src/main/java/com/guitarproacademy/app/
  MainActivity.kt              NavHost wiring every screen
  GuitarProAcademyApp.kt       Application class, holds the Room DB
  navigation/                  Route definitions
  audio/                       Real pitch detector + metronome engine
  data/model/                  Data classes (Lesson, Chord, Song, etc.)
  data/local/                  Room entities, DAOs, database
  data/repository/             Sample content + progress logic
  viewmodel/                   Shared app state (XP, streaks, completion)
  ui/theme/                    Material 3 theme (black/white/orange/gold)
  ui/components/               Reusable cards, chips, nav bar
  ui/screens/                  One package per screen in the spec
```

## Suggested next milestone

Pick one vertical slice and make it fully production-real end to end —
e.g. wire real backend auth + one learning path's video playback — rather
than shallow-polishing all 20+ screens at once.
