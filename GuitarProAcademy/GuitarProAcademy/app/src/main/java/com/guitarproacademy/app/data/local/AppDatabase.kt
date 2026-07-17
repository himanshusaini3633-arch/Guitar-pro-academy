package com.guitarproacademy.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        LessonProgressEntity::class,
        FavoriteEntity::class,
        PracticeSessionEntity::class,
        UserStatsEntity::class,
        QuizResultEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun lessonProgressDao(): LessonProgressDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun practiceSessionDao(): PracticeSessionDao
    abstract fun userStatsDao(): UserStatsDao
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "guitar_pro_academy.db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
        }
    }
}
