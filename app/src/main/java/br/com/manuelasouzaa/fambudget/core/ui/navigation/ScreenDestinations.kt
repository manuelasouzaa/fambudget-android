package br.com.manuelasouzaa.fambudget.core.ui.navigation

sealed class ScreenDestinations(val name: String) {
    data object HomeScreen : ScreenDestinations("home_screen")
}
