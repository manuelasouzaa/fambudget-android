package br.com.manuelasouzaa.fambudget.core.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.manuelasouzaa.fambudget.feature.home.HomeScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier, onLogoutClick: () -> Unit) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ScreenDestinations.HomeScreen.name
    ) {
        composable(ScreenDestinations.HomeScreen.name) {
            HomeScreen(modifier, onLogoutClick = onLogoutClick)
        }
    }
}
