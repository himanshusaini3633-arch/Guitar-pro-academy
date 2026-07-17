package com.guitarproacademy.app.data.model

enum class Difficulty { BEGINNER, INTERMEDIATE, ADVANCED }

enum class PathId { BEGINNER, INTERMEDIATE, ADVANCED }

data class Lesson(
    val id: String,
    val pathId: PathId,
    val orderIndex: Int,
    val title: String,
    val description: String,
    val difficulty: Difficulty,
    val estimatedMinutes: Int,
    val xpReward: Int,
    val videoUrl: String,
    val audioUrl: String,
    val thumbnailEmoji: String,
    val steps: List<String>,
    val isQuizLesson: Boolean = false,
    val isCertificateLesson: Boolean = false
)

data class LearningPath(
    val id: PathId,
    val title: String,
    val subtitle: String,
    val colorHex: Long,
    val lessons: List<Lesson>
)
