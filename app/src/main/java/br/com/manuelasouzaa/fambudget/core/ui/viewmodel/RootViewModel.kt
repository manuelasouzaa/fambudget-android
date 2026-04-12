package br.com.manuelasouzaa.fambudget.core.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.core.session.repository.SessionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

import kotlinx.coroutines.launch

class RootViewModel(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    val uiState: StateFlow<MainScreenUiState> = sessionRepository.userSession
        .map { user ->
            if (user.isLoggedIn) MainScreenUiState.Loaded
            else MainScreenUiState.Auth
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
    data object Auth : MainScreenUiState()
    data object Loaded : MainScreenUiState()
}
