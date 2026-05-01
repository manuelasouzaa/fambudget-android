package br.com.manuelasouzaa.fambudget.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.core.session.repository.SessionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RootViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    private val sessionExpiredFlag = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            sessionRepository.sessionExpired.collect {
                sessionExpiredFlag.value = true
            }
        }
    }

    val uiState: StateFlow<MainScreenUiState> = sessionRepository.userSession
        .combine(sessionExpiredFlag) { user, expired ->
            if (user.isLoggedIn) MainScreenUiState.Loaded
            else MainScreenUiState.Auth(sessionExpired = expired)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainScreenUiState.Loading)

    fun logout() {
        viewModelScope.launch {
            sessionRepository.logout()
        }
    }
}

sealed class MainScreenUiState {
    data object Loading : MainScreenUiState()
    data class Auth(val sessionExpired: Boolean = false) : MainScreenUiState()
    data object Loaded : MainScreenUiState()
}
