package com.guitarproacademy.app.data.model

enum class ChordCategory { MAJOR, MINOR, SEVENTH, MAJOR7, MINOR7, SUS, DIM, AUG, POWER }

data class Chord(
    val id: String,
    val name: String,
    val category: ChordCategory,
    // 6 strings, low E to high E. -1 = muted, 0 = open, N = fret number
    val frets: List<Int>,
    val fingers: List<Int>,
    val isFavorite: Boolean = false
)

enum class SongCategory { BEGINNER, POP, ROCK, BLUES, COUNTRY, FINGERSTYLE, CHRISTMAS, CLASSICAL }

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val category: SongCategory,
    val difficulty: Difficulty,
    val chordsUsed: List<String>,
    val tabPreview: String,
    val lyrics: String,
    val durationSeconds: Int,
    val isFavorite: Boolean = false,
    val isPremium: Boolean = false
)

enum class VideoCategory { TECHNIQUE, THEORY, SONGS, WARMUP, GEAR }

data class VideoLesson(
    val id: String,
    val title: String,
    val category: VideoCategory,
    val durationSeconds: Int,
    val thumbnailEmoji: String,
    val progressSeconds: Int = 0,
    val isDownloaded: Boolean = false,
    val isPremium: Boolean = false
)

data class QuizQuestion(
    val id: String,
    val pathId: PathId,
    val question: String,
    val options: List<String>,
    val correctIndex: Int
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val xpRequired: Int,
    val isUnlocked: Boolean = false
)

data class Certificate(
    val id: String,
    val pathId: PathId,
    val title: String,
    val dateEarnedMillis: Long?
)

data class UserProfile(
    val name: String,
    val bio: String,
    val level: Int,
    val xp: Int,
    val xpToNextLevel: Int,
    val coins: Int,
    val streakDays: Int,
    val isPremium: Boolean
)
