package com.guitarproacademy.app.navigation

sealed class Destination(val route: String) {
    object Splash : Destination("splash")
    object Login : Destination("login")
    object Register : Destination("register")

    // Bottom nav roots
    object Home : Destination("home")
    object Learn : Destination("learn")
    object Practice : Destination("practice")
    object Progress : Destination("progress")
    object Profile : Destination("profile")

    // Learn subgraph
    object LessonDetail : Destination("lesson/{lessonId}") {
        fun build(lessonId: String) = "lesson/$lessonId"
    }
    object ChordLibrary : Destination("chords")
    object Quiz : Destination("quiz/{pathId}") {
        fun build(pathId: String) = "quiz/$pathId"
    }

    // Practice subgraph
    object Tuner : Destination("tuner")
    object Metronome : Destination("metronome")
    object Songs : Destination("songs")
    object SongDetail : Destination("song/{songId}") {
        fun build(songId: String) = "song/$songId"
    }
    object VideoLessons : Destination("videos")

    // Profile subgraph
    object Settings : Destination("settings")
    object AdminPanel : Destination("admin")

    // Global
    object Search : Destination("search")
}

val bottomNavDestinations = listOf(
    Destination.Home,
    Destination.Learn,
    Destination.Practice,
    Destination.Progress,
    Destination.Profile
)
