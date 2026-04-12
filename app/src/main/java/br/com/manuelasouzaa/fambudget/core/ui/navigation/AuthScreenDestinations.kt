package br.com.manuelasouzaa.fambudget.core.ui.navigation

sealed class AuthScreenDestinations(val name: String) {
    data object LoginScreen : AuthScreenDestinations("login_screen")
    data object RegisterScreen : AuthScreenDestinations("register_screen")
}
