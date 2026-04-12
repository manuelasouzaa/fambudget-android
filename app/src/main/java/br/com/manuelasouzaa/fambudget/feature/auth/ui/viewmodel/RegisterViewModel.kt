package br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText
import br.com.manuelasouzaa.fambudget.core.ui.model.SnackbarMessage
import br.com.manuelasouzaa.fambudget.core.util.InputValidator.isEmailValid
import br.com.manuelasouzaa.fambudget.core.util.InputValidator.isEmpty
import br.com.manuelasouzaa.fambudget.core.util.InputValidator.isPhoneNumberValid
import br.com.manuelasouzaa.fambudget.feature.auth.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val useCase: RegisterUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val _registeredEmail = MutableSharedFlow<String>()
    val registeredEmail = _registeredEmail.asSharedFlow()

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun onFullNameChange(name: String) {
        _uiState.update { it.copy(fullName = name) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPhoneNumberChange(phoneNumber: String) {
        _uiState.update { it.copy(phoneNumber = phoneNumber) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun onConfirmPasswordChange(password: String) {
        _uiState.update { it.copy(confirmPassword = password) }
    }

    fun register() {
        viewModelScope.launch {
            val state = uiState.value

            if (!areFieldsValid(state)) return@launch

            _uiState.update { it.copy(isLoading = true, snackbarMessage = null) }

            val result = useCase.register(
                name = state.fullName,
                phoneNumber = state.phoneNumber,
                email = state.email,
                password = state.password
            )

            when (result) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false) }
                    _registeredEmail.emit(state.email)
                }

                is Resource.Error -> {
                    val snackbar = result.uiMessage?.let { SnackbarMessage(it) }
                        ?: result.message?.let { SnackbarMessage(UiText.Dynamic(it)) }

                    _uiState.update { it.copy(isLoading = false, snackbarMessage = snackbar) }
                }
            }
        }
    }

    private fun areFieldsValid(state: RegisterUiState): Boolean {
        if (isEmpty(state.fullName) || isEmpty(state.email) || isEmpty(state.phoneNumber)
            || isEmpty(state.password) || isEmpty(state.confirmPassword)
        ) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_empty_fields))) }
            return false
        }

        if (!isEmailValid(state.email)) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_invalid_email))) }
            return false
        }

        if (!isPhoneNumberValid(state.phoneNumber)) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_invalid_phone))) }
            return false
        }

        if (state.password != state.confirmPassword) {
            _uiState.update { it.copy(snackbarMessage = SnackbarMessage(UiText.Resource(R.string.auth_error_passwords_do_not_match))) }
            return false
        }

        return true
    }
}
