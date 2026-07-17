package com.guitarproacademy.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.guitarproacademy.app.data.local.LessonProgressEntity
import com.guitarproacademy.app.data.local.UserStatsEntity
import com.guitarproacademy.app.data.repository.LessonRepository
import com.guitarproacademy.app.data.repository.ProgressRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class AppUiState(
    val stats: UserStatsEntity = UserStatsEntity(),
    val completedLessonIds: Set<String> = emptySet(),
    val totalLessons: Int = LessonRepository.allLessons().size
) {
    val level: Int get() = ProgressRepository.levelForXp(stats.xp)
    val xpIntoLevel: Int get() = ProgressRepository.xpIntoLevel(stats.xp)
    val xpToNextLevel: Int get() = ProgressRepository.xpToNextLevel(stats.xp)
    val overallProgress: Float get() = if (totalLessons == 0) 0f else completedLessonIds.size.toFloat() / totalLessons
}

class AppViewModel(private val repo: ProgressRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState

    init {
        viewModelScope.launch { repo.ensureStatsInitialized() }
        viewModelScope.launch {
            combine(repo.observeStats(), repo.observeLessonProgress()) { stats, lessons ->
                AppUiState(
                    stats = stats,
                    completedLessonIds = lessons.filter { it.isCompleted }.map { it.lessonId }.toSet()
                )
            }.collect { _uiState.value = it }
        }
    }

    fun isLessonCompleted(lessonId: String): Boolean = _uiState.value.completedLessonIds.contains(lessonId)

    fun completeLesson(lessonId: String, xpReward: Int) {
        viewModelScope.launch { repo.completeLesson(lessonId, xpReward) }
    }

    fun recordPractice(durationSeconds: Int, category: String) {
        viewModelScope.launch { repo.recordPracticeAndStreak(durationSeconds, category) }
    }

    fun recordQuizResult(pathId: String, score: Int, total: Int) {
        viewModelScope.launch { repo.recordQuizResult(pathId, score, total) }
    }

    class Factory(private val repo: ProgressRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T = AppViewModel(repo) as T
    }
}
