package br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel

import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarMessage

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val snackbarMessage: SnackbarMessage? = null
)
