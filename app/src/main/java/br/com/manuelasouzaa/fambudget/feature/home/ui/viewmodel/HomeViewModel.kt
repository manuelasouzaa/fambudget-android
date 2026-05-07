package br.com.manuelasouzaa.fambudget.feature.home.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.ext.toCurrency
import br.com.manuelasouzaa.fambudget.ext.toStringRes
import br.com.manuelasouzaa.fambudget.feature.home.domain.HomeRepository
import br.com.manuelasouzaa.fambudget.feature.home.ui.uistate.HomeUiStateData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiStateData = MutableStateFlow(HomeUiStateData())
    private val uiStateData = _uiStateData.asStateFlow()

    private var income: Double = 0.0
    private var expenses: Double = 0.0

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            getUserName()
            if (!getExpensesTotal()) return@launch
            if (!getIncomeTotal()) return@launch
            getCurrentBalance()

            _uiState.value = HomeUiState.Success(uiStateData.value)
        }
    }

    private suspend fun getUserName() {
        val userName = repository.getUserName()
        _uiStateData.value = uiStateData.value.copy(userName = userName)
    }

    private suspend fun getExpensesTotal(): Boolean {
        return when (val resp = repository.getUserExpensesTotal()) {
            is Resource.Error -> {
                _uiState.value = HomeUiState.Error(resp.uiMessage.toStringRes())
                false
            }

            is Resource.Success -> {
                expenses = resp.data
                _uiStateData.value = uiStateData.value.copy(expensesTotal = resp.data.toCurrency())
                true
            }
        }
    }

    private suspend fun getIncomeTotal(): Boolean {
        return when (val resp = repository.getUserIncomeTotal()) {
            is Resource.Error -> {
                _uiState.value = HomeUiState.Error(resp.uiMessage.toStringRes())
                false
            }

            is Resource.Success -> {
                income = resp.data
                _uiStateData.value = uiStateData.value.copy(incomeTotal = resp.data.toCurrency())
                true
            }
        }
    }

    private fun getCurrentBalance() {
        val currentBalance = repository.getUserCurrentBalance(income = income, expenses = expenses)
        _uiStateData.value = uiStateData.value.copy(
            currentBalance = currentBalance.toCurrency(),
            isCurrentBalancePositive = currentBalance > 0
        )
    }
}

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(val uiStateData: HomeUiStateData) : HomeUiState()
    data class Error(@field:StringRes val messageRes: Int = R.string.error_unknown) : HomeUiState()
}
