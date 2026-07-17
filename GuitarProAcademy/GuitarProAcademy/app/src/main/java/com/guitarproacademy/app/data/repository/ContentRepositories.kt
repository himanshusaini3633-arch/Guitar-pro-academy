package com.guitarproacademy.app.data.repository

import com.guitarproacademy.app.data.model.*

object VideoRepository {
    val videos: List<VideoLesson> = listOf(
        VideoLesson("v1", "Perfect Posture in 3 Minutes", VideoCategory.WARMUP, 180, "🎸", progressSeconds = 180),
        VideoLesson("v2", "Chord Transition Drills", VideoCategory.TECHNIQUE, 420, "🎵", progressSeconds = 120),
        VideoLesson("v3", "Understanding Keys & Scales", VideoCategory.THEORY, 540, "🎼"),
        VideoLesson("v4", "Play Your First Full Song", VideoCategory.SONGS, 360, "🎶"),
        VideoLesson("v5", "Choosing Your First Guitar", VideoCategory.GEAR, 300, "🪕"),
        VideoLesson("v6", "Barre Chord Breakthrough", VideoCategory.TECHNIQUE, 480, "🎸", isPremium = true),
        VideoLesson("v7", "Blues Soloing Essentials", VideoCategory.TECHNIQUE, 600, "🎵", isPremium = true),
        VideoLesson("v8", "Modes Explained Simply", VideoCategory.THEORY, 660, "🎼", isPremium = true)
    )
}

object QuizRepository {
    val questions: List<QuizQuestion> = listOf(
        QuizQuestion("q1", PathId.BEGINNER, "How many strings does a standard guitar have?", listOf("4", "5", "6", "7"), 2),
        QuizQuestion("q2", PathId.BEGINNER, "What is the thickest string tuned to in standard tuning?", listOf("A", "E", "D", "G"), 1),
        QuizQuestion("q3", PathId.BEGINNER, "Which part of the guitar do you press to change notes?", listOf("Bridge", "Frets", "Soundhole", "Tuning pegs"), 1),
        QuizQuestion("q4", PathId.BEGINNER, "What does a '0' mean on a chord chart?", listOf("Muted string", "Open string", "Skip string", "Capo position"), 1),
        QuizQuestion("q5", PathId.BEGINNER, "Which of these is a major chord?", listOf("Am", "Em", "G", "Dm"), 2),
        QuizQuestion("q6", PathId.INTERMEDIATE, "A barre chord uses which finger to press multiple strings?", listOf("Pinky", "Ring finger", "Index finger", "Thumb"), 2),
        QuizQuestion("q7", PathId.INTERMEDIATE, "What technique mutes strings with the picking hand for a punchy tone?", listOf("Hammer-on", "Palm muting", "Slide", "Vibrato"), 1),
        QuizQuestion("q8", PathId.INTERMEDIATE, "How many notes are in a major scale?", listOf("5", "6", "7", "8"), 2),
        QuizQuestion("q9", PathId.INTERMEDIATE, "A power chord typically contains which intervals?", listOf("Root and third", "Root and fifth", "Root and seventh", "Root and second"), 1),
        QuizQuestion("q10", PathId.ADVANCED, "The pentatonic scale contains how many notes?", listOf("4", "5", "6", "7"), 1),
        QuizQuestion("q11", PathId.ADVANCED, "A 12-bar blues progression is most commonly built on which chords?", listOf("I-IV-V", "ii-V-I", "I-vi-IV-V", "I-V-vi-IV"), 0),
        QuizQuestion("q12", PathId.ADVANCED, "An arpeggio is best described as:", listOf("Strumming all notes together", "Playing chord notes one at a time", "A type of tuning", "A percussive technique"), 1)
    )
}

object AchievementRepository {
    val achievements: List<Achievement> = listOf(
        Achievement("a1", "First Strum", "Complete your very first lesson", "🎉", xpRequired = 0),
        Achievement("a2", "Chord Novice", "Learn your first 5 chords", "🖐️", xpRequired = 150),
        Achievement("a3", "Streak Starter", "Practice 3 days in a row", "🔥", xpRequired = 100),
        Achievement("a4", "Week Warrior", "Practice 7 days in a row", "🏆", xpRequired = 300),
        Achievement("a5", "Beginner Graduate", "Complete the Beginner path", "🎓", xpRequired = 700),
        Achievement("a6", "Rhythm Master", "Complete 10 rhythm exercises", "🥁", xpRequired = 400),
        Achievement("a7", "Theory Buff", "Score 100% on a music theory quiz", "🧠", xpRequired = 250),
        Achievement("a8", "Intermediate Graduate", "Complete the Intermediate path", "🌟", xpRequired = 1400),
        Achievement("a9", "Soloist", "Complete your first improvisation lesson", "🎤", xpRequired = 900),
        Achievement("a10", "Advanced Graduate", "Complete the Advanced path", "👑", xpRequired = 2200)
    )
}
