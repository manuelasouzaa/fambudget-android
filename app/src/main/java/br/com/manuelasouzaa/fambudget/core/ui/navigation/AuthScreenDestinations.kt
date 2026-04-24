package br.com.manuelasouzaa.fambudget.core.ui.navigation

sealed class AuthScreenDestinations(val name: String) {
    data object LoginScreen : AuthScreenDestinations("login_screen")
    data object RegisterScreen : AuthScreenDestinations("register_screen")

    data object ResetPasswordScreen: AuthScreenDestinations("reset_password_screen")
    data object ForgotPasswordScreen : AuthScreenDestinations("forgot_password_screen")
}
