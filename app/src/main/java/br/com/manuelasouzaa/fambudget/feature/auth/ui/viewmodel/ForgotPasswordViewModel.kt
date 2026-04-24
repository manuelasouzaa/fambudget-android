package br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarMessage
import br.com.manuelasouzaa.fambudget.core.util.InputValidator.isEmailValid
import br.com.manuelasouzaa.fambudget.core.util.InputValidator.isEmpty
import br.com.manuelasouzaa.fambudget.feature.auth.domain.usecase.ForgotPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val useCase: ForgotPasswordUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ForgotPasswordUiState())
    val uiState = _uiState.asStateFlow()

    fun clearSuccess() {
        _uiState.update { it.copy(isSuccess = false) }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun confirm() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, snackbarMessage = null) }

            if (!isEmailValid()) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            val result = useCase.sendForgotPasswordEmail(_uiState.value.email)

            when (result) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                }

                is Resource.Error -> {
                    val snackbar = result.uiMessage?.let { SnackbarMessage(it) }
                        ?: result.message?.let { SnackbarMessage(UiText.Dynamic(it)) }

                    _uiState.update { uiState ->
                        uiState.copy(isLoading = false, snackbarMessage = snackbar)
                    }
                }
            }
        }
    }

    private fun isEmailValid(): Boolean {
        val email = _uiState.value.email

        if (isEmpty(email)) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_email_required))) }
            return false
        }

        if (!isEmailValid(email)) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_invalid_email))) }
            return false
        }

        return true
    }

}
