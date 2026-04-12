package br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel

import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarMessage

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val snackbarMessage: SnackbarMessage? = null
)
