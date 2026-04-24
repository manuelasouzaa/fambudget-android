package br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText
import br.com.manuelasouzaa.fambudget.core.session.repository.SessionRepository
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarMessage
import br.com.manuelasouzaa.fambudget.core.util.InputValidator.isEmpty
import br.com.manuelasouzaa.fambudget.feature.auth.domain.usecase.ForgotPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val useCase: ForgotPasswordUseCase,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ResetPasswordUiState())
    val uiState = _uiState.asStateFlow()

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun onCodeChange(code: String) {
        _uiState.update { it.copy(code = code) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onConfirmPasswordChange(password: String) {
        _uiState.update { it.copy(confirmPassword = password) }
    }

    fun confirm() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, snackbarMessage = null) }

            if (!isCodeValid()) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            val result = useCase.validateCode(_uiState.value.code)

            when (result) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, isValid = true) }
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

    fun confirmResetPassword(email: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, snackbarMessage = null) }

            if (!areFieldsValid()) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            val result = useCase.resetPassword(email, _uiState.value.password)

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

    private fun isCodeValid(): Boolean {
        val code = _uiState.value.code

        if (isEmpty(code)) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_code_required))) }
            return false
        }

        if (code.length < 5) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_code_required))) }
            return false
        }

        return true
    }

    private fun areFieldsValid(): Boolean {
        if (isEmpty(_uiState.value.password) || isEmpty(_uiState.value.confirmPassword)) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_empty_fields))) }
            return false
        }

        if (_uiState.value.password != _uiState.value.confirmPassword) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_passwords_do_not_match))) }
            return false
        }

        return true
    }

}
