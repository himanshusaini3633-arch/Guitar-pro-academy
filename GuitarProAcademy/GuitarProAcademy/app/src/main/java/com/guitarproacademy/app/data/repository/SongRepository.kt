package com.guitarproacademy.app.data.repository

import com.guitarproacademy.app.data.model.Difficulty
import com.guitarproacademy.app.data.model.Song
import com.guitarproacademy.app.data.model.SongCategory

object SongRepository {

    val songs: List<Song> = listOf(
        Song(
            "song_1", "Three Chord Sunrise", "Academy Sessions", SongCategory.BEGINNER, Difficulty.BEGINNER,
            listOf("G Major", "C Major", "D Major"),
            "e|--0---0---2---|\nB|--1---1---3---|\nG|--0---0---2---|",
            "Verse: G ... C ... D ...\nChorus: G ... C ... D ... G",
            145
        ),
        Song(
            "song_2", "Open Road", "Academy Sessions", SongCategory.POP, Difficulty.BEGINNER,
            listOf("C Major", "A Minor", "F Major", "G Major"),
            "e|--3---0---1---3---|",
            "Verse: C - Am - F - G (repeat)\nChorus: C - G - Am - F",
            198
        ),
        Song(
            "song_3", "Redline", "The Amp Kings", SongCategory.ROCK, Difficulty.INTERMEDIATE,
            listOf("E5", "A5", "D5", "G5"),
            "E|--0--0--3--0--|\nA|--2--2--0--2--|",
            "Verse: E5 - A5 (palm muted)\nChorus: full power chords, no mute",
            210
        ),
        Song(
            "song_4", "Midnight Blues", "Delta Session Trio", SongCategory.BLUES, Difficulty.INTERMEDIATE,
            listOf("E7", "A7", "B7"),
            "12-bar blues in E: E7 E7 E7 E7 A7 A7 E7 E7 B7 A7 E7 B7",
            "Instrumental — 12 bar blues shuffle",
            240
        ),
        Song(
            "song_5", "Backroad Home", "Route 9", SongCategory.COUNTRY, Difficulty.BEGINNER,
            listOf("G Major", "C Major", "D Major", "E Minor"),
            "e|--3---1---0---0---|",
            "Verse: G - Em - C - D\nChorus: G - C - G - D",
            187
        ),
        Song(
            "song_6", "Autumn Fingers", "Solo Sessions", SongCategory.FINGERSTYLE, Difficulty.ADVANCED,
            listOf("C Major", "A Minor", "F Major", "G Major"),
            "Travis picking pattern across Am - F - C - G",
            "Instrumental fingerstyle piece",
            212
        ),
        Song(
            "song_7", "Silent Strings", "Academy Sessions", SongCategory.CHRISTMAS, Difficulty.BEGINNER,
            listOf("D Major", "G Major", "A Major"),
            "e|--2---3---0---|",
            "Verse: D - G - D - A\nChorus: G - D - A - D",
            176
        ),
        Song(
            "song_8", "Prelude in Frets", "Classical Guitar Society", SongCategory.CLASSICAL, Difficulty.ADVANCED,
            listOf("Am", "Dm", "E7", "Am"),
            "Fingerstyle arpeggio pattern, classical right-hand technique (p-i-m-a)",
            "Instrumental classical study",
            230
        ),
        Song(
            "song_9", "Campfire Anthem", "Academy Sessions", SongCategory.BEGINNER, Difficulty.BEGINNER,
            listOf("A Major", "D Major", "E Major"),
            "e|--0---2---0---|",
            "Verse: A - D - E - A\nChorus: D - A - E - A",
            165,
            isPremium = true
        ),
        Song(
            "song_10", "Neon Heartbeat", "Pulse", SongCategory.POP, Difficulty.INTERMEDIATE,
            listOf("Em", "C", "G", "D"),
            "e|--0---1---3---2---|",
            "Verse: Em - C - G - D (repeat)\nChorus: C - G - D - Em",
            201,
            isPremium = true
        )
    )
}
