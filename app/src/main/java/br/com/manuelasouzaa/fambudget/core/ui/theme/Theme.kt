package br.com.manuelasouzaa.fambudget.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    background = dark_theme_background,
    onBackground = dark_theme_onBackground,
    surface = dark_theme_surface,
    surfaceContainer = dark_theme_surfaceContainer,
    onSecondary = dark_theme_onSecondary,
    tertiary = dark_theme_tertiary,
    primaryContainer = dark_theme_primary_container,
    onPrimaryContainer = dark_theme_onPrimary_container,
    errorContainer = dark_theme_errorContainer,
    onErrorContainer = dark_theme_onErrorContainer,
    secondaryContainer = dark_theme_secondary_container
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    background = light_theme_background,
    onBackground = light_theme_onBackground,
    surface = light_theme_surface,
    surfaceContainer = light_theme_surfaceContainer,
    onSecondary = light_theme_onSecondary,
    tertiary = light_theme_tertiary,
    primaryContainer = light_theme_primary_container,
    onPrimaryContainer = light_theme_onPrimary_container,
    errorContainer = light_theme_errorContainer,
    onErrorContainer = light_theme_onErrorContainer,
    secondaryContainer = light_theme_secondary_container
)

@Composable
fun FamBudgetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
