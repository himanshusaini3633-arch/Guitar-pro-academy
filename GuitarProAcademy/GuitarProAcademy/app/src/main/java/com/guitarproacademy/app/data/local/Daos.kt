package com.guitarproacademy.app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LessonProgressDao {
    @Query("SELECT * FROM lesson_progress")
    fun observeAll(): Flow<List<LessonProgressEntity>>

    @Query("SELECT * FROM lesson_progress WHERE lessonId = :lessonId")
    suspend fun get(lessonId: String): LessonProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: LessonProgressEntity)

    @Query("SELECT COUNT(*) FROM lesson_progress WHERE isCompleted = 1")
    fun observeCompletedCount(): Flow<Int>
}

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE type = :type")
    fun observeByType(type: String): Flow<List<FavoriteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(entity: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE itemId = :itemId")
    suspend fun remove(itemId: String)
}

@Dao
interface PracticeSessionDao {
    @Query("SELECT * FROM practice_sessions ORDER BY dateMillis DESC")
    fun observeAll(): Flow<List<PracticeSessionEntity>>

    @Insert
    suspend fun insert(entity: PracticeSessionEntity)

    @Query("SELECT COALESCE(SUM(durationSeconds), 0) FROM practice_sessions WHERE dateMillis >= :sinceMillis")
    fun observeTotalSince(sinceMillis: Long): Flow<Int>
}

@Dao
interface UserStatsDao {
    @Query("SELECT * FROM user_stats WHERE id = 0")
    fun observe(): Flow<UserStatsEntity?>

    @Query("SELECT * FROM user_stats WHERE id = 0")
    suspend fun getOnce(): UserStatsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: UserStatsEntity)
}

@Dao
interface QuizResultDao {
    @Query("SELECT * FROM quiz_results ORDER BY dateMillis DESC")
    fun observeAll(): Flow<List<QuizResultEntity>>

    @Insert
    suspend fun insert(entity: QuizResultEntity)
}
