package com.guitarproacademy.app.data.repository

import com.guitarproacademy.app.data.model.Chord
import com.guitarproacademy.app.data.model.ChordCategory

object ChordRepository {

    // frets: low E, A, D, G, B, high E. -1 = mute, 0 = open
    val chords: List<Chord> = listOf(
        Chord("c_major", "C Major", ChordCategory.MAJOR, listOf(-1, 3, 2, 0, 1, 0), listOf(0, 3, 2, 0, 1, 0)),
        Chord("g_major", "G Major", ChordCategory.MAJOR, listOf(3, 2, 0, 0, 3, 3), listOf(2, 1, 0, 0, 3, 4)),
        Chord("d_major", "D Major", ChordCategory.MAJOR, listOf(-1, -1, 0, 2, 3, 2), listOf(0, 0, 0, 1, 3, 2)),
        Chord("a_major", "A Major", ChordCategory.MAJOR, listOf(-1, 0, 2, 2, 2, 0), listOf(0, 0, 1, 2, 3, 0)),
        Chord("e_major", "E Major", ChordCategory.MAJOR, listOf(0, 2, 2, 1, 0, 0), listOf(0, 2, 3, 1, 0, 0)),
        Chord("f_major", "F Major", ChordCategory.MAJOR, listOf(1, 3, 3, 2, 1, 1), listOf(1, 3, 4, 2, 1, 1)),

        Chord("a_minor", "A Minor", ChordCategory.MINOR, listOf(-1, 0, 2, 2, 1, 0), listOf(0, 0, 2, 3, 1, 0)),
        Chord("e_minor", "E Minor", ChordCategory.MINOR, listOf(0, 2, 2, 0, 0, 0), listOf(0, 2, 3, 0, 0, 0)),
        Chord("d_minor", "D Minor", ChordCategory.MINOR, listOf(-1, -1, 0, 2, 3, 1), listOf(0, 0, 0, 2, 3, 1)),
        Chord("b_minor", "B Minor", ChordCategory.MINOR, listOf(-1, 2, 4, 4, 3, 2), listOf(0, 1, 3, 4, 2, 1)),
        Chord("c_minor", "C Minor", ChordCategory.MINOR, listOf(-1, 3, 5, 5, 4, 3), listOf(0, 1, 3, 4, 2, 1)),

        Chord("g7", "G7", ChordCategory.SEVENTH, listOf(3, 2, 0, 0, 0, 1), listOf(3, 2, 0, 0, 0, 1)),
        Chord("c7", "C7", ChordCategory.SEVENTH, listOf(-1, 3, 2, 3, 1, 0), listOf(0, 3, 2, 4, 1, 0)),
        Chord("d7", "D7", ChordCategory.SEVENTH, listOf(-1, -1, 0, 2, 1, 2), listOf(0, 0, 0, 2, 1, 3)),
        Chord("e7", "E7", ChordCategory.SEVENTH, listOf(0, 2, 0, 1, 0, 0), listOf(0, 2, 0, 1, 0, 0)),
        Chord("a7", "A7", ChordCategory.SEVENTH, listOf(-1, 0, 2, 0, 2, 0), listOf(0, 0, 2, 0, 3, 0)),

        Chord("cmaj7", "C Major 7", ChordCategory.MAJOR7, listOf(-1, 3, 2, 0, 0, 0), listOf(0, 3, 2, 0, 0, 0)),
        Chord("gmaj7", "G Major 7", ChordCategory.MAJOR7, listOf(3, 2, 0, 0, 0, 2), listOf(3, 2, 0, 0, 0, 1)),
        Chord("dmaj7", "D Major 7", ChordCategory.MAJOR7, listOf(-1, -1, 0, 2, 2, 2), listOf(0, 0, 0, 1, 1, 1)),
        Chord("fmaj7", "F Major 7", ChordCategory.MAJOR7, listOf(-1, -1, 3, 2, 1, 0), listOf(0, 0, 3, 2, 1, 0)),

        Chord("am7", "A Minor 7", ChordCategory.MINOR7, listOf(-1, 0, 2, 0, 1, 0), listOf(0, 0, 2, 0, 1, 0)),
        Chord("em7", "E Minor 7", ChordCategory.MINOR7, listOf(0, 2, 0, 0, 0, 0), listOf(0, 2, 0, 0, 0, 0)),
        Chord("dm7", "D Minor 7", ChordCategory.MINOR7, listOf(-1, -1, 0, 2, 1, 1), listOf(0, 0, 0, 2, 1, 1)),

        Chord("dsus2", "D sus2", ChordCategory.SUS, listOf(-1, -1, 0, 2, 3, 0), listOf(0, 0, 0, 1, 2, 0)),
        Chord("dsus4", "D sus4", ChordCategory.SUS, listOf(-1, -1, 0, 2, 3, 3), listOf(0, 0, 0, 1, 2, 3)),
        Chord("asus2", "A sus2", ChordCategory.SUS, listOf(-1, 0, 2, 2, 0, 0), listOf(0, 0, 1, 2, 0, 0)),
        Chord("asus4", "A sus4", ChordCategory.SUS, listOf(-1, 0, 2, 2, 3, 0), listOf(0, 0, 1, 2, 3, 0)),

        Chord("bdim", "B Diminished", ChordCategory.DIM, listOf(-1, 2, 3, 4, 3, -1), listOf(0, 1, 2, 4, 3, 0)),
        Chord("cdim", "C Diminished", ChordCategory.DIM, listOf(-1, 3, 4, 5, 4, -1), listOf(0, 1, 2, 4, 3, 0)),

        Chord("caug", "C Augmented", ChordCategory.AUG, listOf(-1, 3, 2, 1, 1, 0), listOf(0, 4, 3, 1, 1, 0)),
        Chord("gaug", "G Augmented", ChordCategory.AUG, listOf(3, 2, 1, 0, 0, 3), listOf(3, 2, 1, 0, 0, 4)),

        Chord("e5", "E5 (Power Chord)", ChordCategory.POWER, listOf(0, 2, 2, -1, -1, -1), listOf(0, 1, 2, 0, 0, 0)),
        Chord("a5", "A5 (Power Chord)", ChordCategory.POWER, listOf(-1, 0, 2, 2, -1, -1), listOf(0, 0, 1, 2, 0, 0)),
        Chord("d5", "D5 (Power Chord)", ChordCategory.POWER, listOf(-1, -1, 0, 2, 3, -1), listOf(0, 0, 0, 1, 2, 0)),
        Chord("g5", "G5 (Power Chord)", ChordCategory.POWER, listOf(3, 5, 5, -1, -1, -1), listOf(1, 3, 4, 0, 0, 0))
    )
}
