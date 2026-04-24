package br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel

import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarMessage

data class ResetPasswordUiState(
    val code: String = "",
    val isValid: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val snackbarMessage: SnackbarMessage? = null,
    val password: String = "",
    val confirmPassword: String = "",
)