package com.guitarproacademy.app.data.repository

import com.guitarproacademy.app.data.model.*

/**
 * In-memory content source for lessons across all 3 learning paths.
 * Swap this for a remote/Firestore-backed repository later without touching
 * any ViewModel or screen — they only depend on the function signatures below.
 */
object LessonRepository {

    private val beginnerTitles = listOf(
        "Introduction to Guitar" to "Meet your instrument and understand what makes guitar such a rewarding thing to learn.",
        "Parts of the Guitar" to "Headstock, neck, frets, bridge, soundhole — learn every part and what it does.",
        "Holding the Guitar" to "Correct sitting and standing posture so you can play comfortably for hours.",
        "Finger Position" to "Train your fretting hand for clean, buzz-free notes from day one.",
        "How to Tune Your Guitar" to "Get your ears (and the built-in tuner) trained on standard EADGBE tuning.",
        "Reading Tabs" to "Guitar tablature explained — read your first tab line in five minutes.",
        "Reading Chord Charts" to "Decode chord diagrams so you can pick up any chord sheet and play it.",
        "Playing Open Strings" to "Pluck all six open strings cleanly with proper right-hand technique.",
        "Major Chords" to "Learn G, C, D and E major — the backbone of thousands of songs.",
        "Minor Chords" to "Add Am, Em and Dm to your vocabulary for a full emotional range.",
        "Practice Exercise: Chord Switching" to "Build the muscle memory to switch chords smoothly and on time.",
        "Your First Easy Song" to "Put every skill so far together and play a complete simple song.",
        "Beginner Quiz" to "Test what you've learned across the beginner path.",
        "Final Practice Session" to "A guided practice routine that ties the whole path together.",
        "Beginner Certificate" to "Claim your Beginner Guitarist certificate."
    )

    private val intermediateTitles = listOf(
        "Major Scale" to "Learn the major scale pattern and why it's the map for everything else.",
        "Minor Scale" to "The natural minor scale and how it changes the mood of your playing.",
        "Power Chords" to "Root-fifth shapes that drive rock, punk and metal rhythm playing.",
        "Barre Chords" to "The technique that unlocks every major and minor chord shape, movable.",
        "Finger Exercises" to "Independence and strength drills for a faster, cleaner fretting hand.",
        "Chord Switching Speed" to "Push your chord-change speed with timed drills.",
        "Rhythm Guitar" to "Strumming patterns, accents and groove.",
        "Palm Muting" to "Add punch and control with the palm-muting technique.",
        "Hammer-On" to "Add legato flavor by hammering onto a fretted note.",
        "Pull-Off" to "The reverse of the hammer-on — smooth, connected descending notes.",
        "Slides" to "Slide between notes for expressive, vocal-like phrasing.",
        "Music Theory Basics" to "Intervals, keys and the number system behind chord progressions.",
        "Intermediate Songs" to "Apply your new techniques across a set of intermediate-level songs.",
        "Intermediate Quiz" to "Check your understanding of the intermediate path.",
        "Intermediate Certificate" to "Claim your Intermediate Guitarist certificate."
    )

    private val advancedTitles = listOf(
        "Lead Guitar Fundamentals" to "Phrasing, bending and vibrato for expressive solos.",
        "Fingerstyle Technique" to "Play bass, melody and harmony together with your picking hand.",
        "Improvisation" to "Learn to solo confidently over a chord progression in real time.",
        "Pentatonic Scale" to "The five-note scale behind most rock and blues solos.",
        "Blues Guitar" to "12-bar blues form, turnarounds and blues phrasing.",
        "Rock Guitar" to "Riff writing, palm-muted rhythm and classic rock techniques.",
        "Jazz Chords" to "Extended and altered chord voicings for jazz harmony.",
        "Arpeggios" to "Outline chords note-by-note for melodic, harmonic solo lines.",
        "Advanced Rhythm Techniques" to "Syncopation, odd meters and dynamic strumming control.",
        "Solo Practice Session" to "A structured practice routine for building solo vocabulary.",
        "Advanced Songs" to "Full arrangements combining lead, rhythm and technique.",
        "Speed Exercises" to "Alternate picking and legato drills for clean speed.",
        "Advanced Music Theory" to "Modes, chord extensions and reharmonization.",
        "Final Exam" to "A comprehensive test across everything in the advanced path.",
        "Advanced Certificate" to "Claim your Advanced Guitarist certificate."
    )

    private fun buildPath(pathId: PathId, titles: List<Pair<String, String>>, difficulty: Difficulty, colorHex: Long, subtitle: String): LearningPath {
        val lessons = titles.mapIndexed { index, (title, desc) ->
            val order = index + 1
            val isQuiz = title.contains("Quiz")
            val isCert = title.contains("Certificate")
            Lesson(
                id = "${pathId.name.lowercase()}_$order",
                pathId = pathId,
                orderIndex = order,
                title = title,
                description = desc,
                difficulty = difficulty,
                estimatedMinutes = if (isQuiz) 10 else if (isCert) 5 else (8 + (index % 5) * 3),
                xpReward = if (isCert) 200 else if (isQuiz) 100 else 50 + (index % 4) * 10,
                videoUrl = "https://cdn.guitarproacademy.com/videos/${pathId.name.lowercase()}_$order.mp4",
                audioUrl = "https://cdn.guitarproacademy.com/audio/${pathId.name.lowercase()}_$order.mp3",
                thumbnailEmoji = listOf("🎸", "🎵", "🎶", "🎼", "🪕")[index % 5],
                steps = listOf(
                    "Watch the video walkthrough",
                    "Follow the step-by-step guide",
                    "Try the interactive practice drill",
                    if (isQuiz) "Answer all quiz questions" else "Repeat until it feels smooth",
                    "Mark the lesson complete to earn XP"
                ),
                isQuizLesson = isQuiz,
                isCertificateLesson = isCert
            )
        }
        return LearningPath(pathId, "${pathId.name.lowercase().replaceFirstChar { it.uppercase() }}", subtitle, colorHex, lessons)
    }

    val paths: List<LearningPath> by lazy {
        listOf(
            buildPath(PathId.BEGINNER, beginnerTitles, Difficulty.BEGINNER, 0xFFFF6B1A, "Start your journey from zero"),
            buildPath(PathId.INTERMEDIATE, intermediateTitles, Difficulty.INTERMEDIATE, 0xFFD4AF37, "Build real technique and theory"),
            buildPath(PathId.ADVANCED, advancedTitles, Difficulty.ADVANCED, 0xFF17171B, "Master lead, theory and improvisation")
        )
    }

    fun allLessons(): List<Lesson> = paths.flatMap { it.lessons }

    fun lessonById(id: String): Lesson? = allLessons().find { it.id == id }

    fun pathById(id: PathId): LearningPath = paths.first { it.id == id }
}
