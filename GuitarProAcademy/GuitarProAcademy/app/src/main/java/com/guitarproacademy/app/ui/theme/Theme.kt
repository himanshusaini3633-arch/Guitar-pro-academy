package com.guitarproacademy.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColors = darkColorScheme(
    primary = EmberOrange,
    onPrimary = PureWhite,
    secondary = RichGold,
    onSecondary = PureBlack,
    tertiary = PaleGold,
    background = PureBlack,
    onBackground = TextPrimaryDark,
    surface = CharcoalSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = CharcoalCard,
    onSurfaceVariant = TextSecondaryDark,
    error = ErrorRed
)

private val LightColors = lightColorScheme(
    primary = EmberOrange,
    onPrimary = PureWhite,
    secondary = RichGold,
    onSecondary = PureBlack,
    tertiary = DeepOrange,
    background = OffWhite,
    onBackground = TextPrimaryLight,
    surface = PureWhite,
    onSurface = TextPrimaryLight,
    surfaceVariant = Color(0xFFEFEDE8),
    onSurfaceVariant = TextSecondaryLight,
    error = ErrorRed
)

@Composable
fun GuitarProAcademyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    val view = LocalView.current
    if (!view.isInEditMode) {
        androidx.compose.runtime.SideEffect {
            val window = (view.context as android.app.Activity).window
            window.statusBarColor = colors.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colors,
        typography = GuitarTypography,
        content = content
    )
}
