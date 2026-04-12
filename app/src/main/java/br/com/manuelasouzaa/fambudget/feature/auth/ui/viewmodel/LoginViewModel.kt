package br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarMessage
import br.com.manuelasouzaa.fambudget.core.util.InputValidator.isEmailValid
import br.com.manuelasouzaa.fambudget.core.util.InputValidator.isEmpty
import br.com.manuelasouzaa.fambudget.feature.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val useCase: LoginUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun login() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, snackbarMessage = null) }

            if (!areEmailAndPasswordValid()) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            val result = useCase.login(_uiState.value.email, _uiState.value.password)

            when (result) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
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

    private fun areEmailAndPasswordValid(): Boolean {
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (isEmpty(email) && isEmpty(password)) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_empty_fields))) }
            return false
        }

        if (isEmpty(email)) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_email_required))) }
            return false
        }

        if (isEmpty(password)) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_password_required))) }
            return false
        }

        if (!isEmailValid(email)) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_invalid_email))) }
            return false
        }

        return true
    }
}
