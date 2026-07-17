package com.guitarproacademy.app.data.repository

import com.guitarproacademy.app.data.local.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit

/**
 * Wraps Room DAOs and applies the app's real gamification rules:
 * - Level = floor(xp / 200) + 1, 200 xp per level
 * - Streak increments once per new calendar day of practice, resets if a day is missed
 */
class ProgressRepository(private val db: AppDatabase) {

    private val statsDao = db.userStatsDao()
    private val lessonDao = db.lessonProgressDao()
    private val favoriteDao = db.favoriteDao()
    private val sessionDao = db.practiceSessionDao()
    private val quizDao = db.quizResultDao()

    fun observeStats(): Flow<UserStatsEntity> = statsDao.observe().map { it ?: UserStatsEntity() }

    fun observeCompletedLessonCount(): Flow<Int> = lessonDao.observeCompletedCount()

    fun observeLessonProgress(): Flow<List<LessonProgressEntity>> = lessonDao.observeAll()

    fun observeFavorites(type: String): Flow<List<FavoriteEntity>> = favoriteDao.observeByType(type)

    fun observePracticeSessions(): Flow<List<PracticeSessionEntity>> = sessionDao.observeAll()

    fun observeWeeklyPracticeSeconds(): Flow<Int> {
        val weekAgo = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)
        return sessionDao.observeTotalSince(weekAgo)
    }

    suspend fun ensureStatsInitialized() {
        currentStatsOrDefault()
    }

    suspend fun addXp(amount: Int) {
        val stats = currentStatsOrDefault()
        statsDao.upsert(stats.copy(xp = stats.xp + amount))
    }

    suspend fun addCoins(amount: Int) {
        val stats = currentStatsOrDefault()
        statsDao.upsert(stats.copy(coins = stats.coins + amount))
    }

    suspend fun recordPracticeAndStreak(durationSeconds: Int, category: String) {
        sessionDao.insert(
            PracticeSessionEntity(
                dateMillis = System.currentTimeMillis(),
                durationSeconds = durationSeconds,
                category = category
            )
        )
        val stats = currentStatsOrDefault()
        val now = System.currentTimeMillis()
        val oneDayMs = TimeUnit.DAYS.toMillis(1)
        val newStreak = when {
            stats.lastPracticeMillis == null -> 1
            now - stats.lastPracticeMillis < oneDayMs -> stats.streakDays // same day, no change
            now - stats.lastPracticeMillis < oneDayMs * 2 -> stats.streakDays + 1 // consecutive day
            else -> 1 // streak broken, restart
        }
        statsDao.upsert(stats.copy(streakDays = newStreak, lastPracticeMillis = now))
    }

    suspend fun completeLesson(lessonId: String, xpReward: Int) {
        lessonDao.upsert(LessonProgressEntity(lessonId, isCompleted = true, isUnlocked = true, completedAtMillis = System.currentTimeMillis()))
        addXp(xpReward)
        addCoins(xpReward / 5)
    }

    suspend fun toggleFavorite(itemId: String, type: String, currentlyFavorite: Boolean) {
        if (currentlyFavorite) favoriteDao.remove(itemId) else favoriteDao.add(FavoriteEntity(itemId, type))
    }

    suspend fun recordQuizResult(pathId: String, score: Int, total: Int) {
        quizDao.insert(QuizResultEntity(pathId = pathId, score = score, total = total, dateMillis = System.currentTimeMillis()))
    }

    private suspend fun currentStatsOrDefault(): UserStatsEntity {
        return statsDao.getOnce() ?: UserStatsEntity().also { statsDao.upsert(it) }
    }

    companion object {
        fun levelForXp(xp: Int): Int = xp / 200 + 1
        fun xpIntoLevel(xp: Int): Int = xp % 200
        fun xpToNextLevel(xp: Int): Int = 200 - (xp % 200)
    }
}
