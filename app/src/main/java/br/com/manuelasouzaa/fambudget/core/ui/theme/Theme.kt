package br.com.manuelasouzaa.fambudget.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    onBackground = dark_theme_onBackground,
    onSecondary = dark_theme_onSecondary,
    errorContainer = dark_theme_errorContainer,
    onErrorContainer = dark_theme_onErrorContainer,
    primaryContainer = dark_theme_primary_container,
    onPrimaryContainer = dark_theme_onPrimary_container
)

private val LightColorScheme = lightColorScheme(
    primary = primary,
    onPrimary = onPrimary,
    onBackground = light_theme_onBackground,
    onSecondary = light_theme_onSecondary,
    errorContainer = light_theme_errorContainer,
    onErrorContainer = light_theme_onErrorContainer,
    primaryContainer = light_theme_primary_container,
    onPrimaryContainer = light_theme_onPrimary_container
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
