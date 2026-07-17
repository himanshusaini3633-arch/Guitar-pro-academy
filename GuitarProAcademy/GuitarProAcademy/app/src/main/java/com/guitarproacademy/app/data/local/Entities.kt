package com.guitarproacademy.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lesson_progress")
data class LessonProgressEntity(
    @PrimaryKey val lessonId: String,
    val isCompleted: Boolean = false,
    val isUnlocked: Boolean = false,
    val completedAtMillis: Long? = null
)

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val itemId: String,
    val type: String // "CHORD" or "SONG"
)

@Entity(tableName = "practice_sessions")
data class PracticeSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateMillis: Long,
    val durationSeconds: Int,
    val category: String
)

@Entity(tableName = "user_stats")
data class UserStatsEntity(
    @PrimaryKey val id: Int = 0, // singleton row
    val name: String = "Guitarist",
    val bio: String = "Learning to rock 🎸",
    val xp: Int = 0,
    val coins: Int = 0,
    val streakDays: Int = 0,
    val lastPracticeMillis: Long? = null,
    val isPremium: Boolean = false
)

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val pathId: String,
    val score: Int,
    val total: Int,
    val dateMillis: Long
)
