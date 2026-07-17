package com.guitarproacademy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.guitarproacademy.app.navigation.Destination
import com.guitarproacademy.app.navigation.bottomNavDestinations
import com.guitarproacademy.app.ui.components.GuitarBottomNavBar
import com.guitarproacademy.app.ui.screens.admin.AdminPanelScreen
import com.guitarproacademy.app.ui.screens.auth.LoginScreen
import com.guitarproacademy.app.ui.screens.auth.RegisterScreen
import com.guitarproacademy.app.ui.screens.chords.ChordLibraryScreen
import com.guitarproacademy.app.ui.screens.home.HomeScreen
import com.guitarproacademy.app.ui.screens.learn.LearnScreen
import com.guitarproacademy.app.ui.screens.learn.LessonDetailScreen
import com.guitarproacademy.app.ui.screens.metronome.MetronomeScreen
import com.guitarproacademy.app.ui.screens.practice.PracticeScreen
import com.guitarproacademy.app.ui.screens.profile.ProfileScreen
import com.guitarproacademy.app.ui.screens.progress.ProgressScreen
import com.guitarproacademy.app.ui.screens.quiz.QuizScreen
import com.guitarproacademy.app.ui.screens.settings.SettingsScreen
import com.guitarproacademy.app.ui.screens.songs.SongDetailScreen
import com.guitarproacademy.app.ui.screens.songs.SongsScreen
import com.guitarproacademy.app.ui.screens.splash.SplashScreen
import com.guitarproacademy.app.ui.screens.tuner.TunerScreen
import com.guitarproacademy.app.ui.screens.videos.VideoLessonsScreen
import com.guitarproacademy.app.ui.theme.GuitarProAcademyTheme
import com.guitarproacademy.app.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuitarProAcademyTheme {
                val app = application as GuitarProAcademyApp
                val appViewModel: AppViewModel = viewModel(factory = AppViewModel.Factory(app.progressRepository))
                GuitarProAcademyRoot(appViewModel)
            }
        }
    }
}

@Composable
fun GuitarProAcademyRoot(appViewModel: AppViewModel) {
    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = bottomNavDestinations.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                GuitarBottomNavBar(currentRoute = currentRoute) { dest ->
                    navController.navigate(dest.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Destination.Splash.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Destination.Splash.route) {
                SplashScreen(onFinished = {
                    navController.navigate(Destination.Login.route) {
                        popUpTo(Destination.Splash.route) { inclusive = true }
                    }
                })
            }
            composable(Destination.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(Destination.Home.route) {
                            popUpTo(Destination.Login.route) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { navController.navigate(Destination.Register.route) }
                )
            }
            composable(Destination.Register.route) {
                RegisterScreen(onRegisterSuccess = {
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Login.route) { inclusive = true }
                    }
                }, onBack = { navController.popBackStack() })
            }
            composable(Destination.Home.route) {
                HomeScreen(
                    appViewModel = appViewModel,
                    onNavigate = { navController.navigate(it.route) },
                    onOpenLesson = { navController.navigate(Destination.LessonDetail.build(it)) }
                )
            }
            composable(Destination.Learn.route) {
                LearnScreen(
                    appViewModel = appViewModel,
                    onOpenLesson = { navController.navigate(Destination.LessonDetail.build(it)) },
                    onOpenChords = { navController.navigate(Destination.ChordLibrary.route) },
                    onOpenQuiz = { navController.navigate(Destination.Quiz.build(it)) }
                )
            }
            composable(Destination.Practice.route) {
                PracticeScreen(
                    appViewModel = appViewModel,
                    onOpenTuner = { navController.navigate(Destination.Tuner.route) },
                    onOpenMetronome = { navController.navigate(Destination.Metronome.route) },
                    onOpenSongs = { navController.navigate(Destination.Songs.route) },
                    onOpenVideos = { navController.navigate(Destination.VideoLessons.route) }
                )
            }
            composable(Destination.Progress.route) {
                ProgressScreen(appViewModel = appViewModel)
            }
            composable(Destination.Profile.route) {
                ProfileScreen(
                    appViewModel = appViewModel,
                    onOpenSettings = { navController.navigate(Destination.Settings.route) },
                    onOpenAdmin = { navController.navigate(Destination.AdminPanel.route) },
                    onLogout = {
                        navController.navigate(Destination.Login.route) {
                            popUpTo(0)
                        }
                    }
                )
            }
            composable(
                Destination.LessonDetail.route,
                arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
            ) { backStack ->
                val lessonId = backStack.arguments?.getString("lessonId") ?: return@composable
                LessonDetailScreen(
                    lessonId = lessonId,
                    appViewModel = appViewModel,
                    onBack = { navController.popBackStack() },
                    onOpenQuiz = { navController.navigate(Destination.Quiz.build(it)) }
                )
            }
            composable(Destination.ChordLibrary.route) {
                ChordLibraryScreen(onBack = { navController.popBackStack() })
            }
            composable(
                Destination.Quiz.route,
                arguments = listOf(navArgument("pathId") { type = NavType.StringType })
            ) { backStack ->
                val pathId = backStack.arguments?.getString("pathId") ?: return@composable
                QuizScreen(pathId = pathId, appViewModel = appViewModel, onBack = { navController.popBackStack() })
            }
            composable(Destination.Tuner.route) {
                TunerScreen(onBack = { navController.popBackStack() })
            }
            composable(Destination.Metronome.route) {
                MetronomeScreen(onBack = { navController.popBackStack() })
            }
            composable(Destination.Songs.route) {
                SongsScreen(onBack = { navController.popBackStack() }, onOpenSong = { navController.navigate(Destination.SongDetail.build(it)) })
            }
            composable(
                Destination.SongDetail.route,
                arguments = listOf(navArgument("songId") { type = NavType.StringType })
            ) { backStack ->
                val songId = backStack.arguments?.getString("songId") ?: return@composable
                SongDetailScreen(songId = songId, onBack = { navController.popBackStack() })
            }
            composable(Destination.VideoLessons.route) {
                VideoLessonsScreen(onBack = { navController.popBackStack() })
            }
            composable(Destination.Settings.route) {
                SettingsScreen(onBack = { navController.popBackStack() })
            }
            composable(Destination.AdminPanel.route) {
                AdminPanelScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
