package br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel

import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarMessage

data class ForgotPasswordUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val snackbarMessage: SnackbarMessage? = null
)
