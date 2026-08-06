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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var refreshJob: Job? = null

    init {
        refresh()
    }

    fun refresh() {
        refreshJob?.cancel()
        refreshJob = viewModelScope.launch {
            _uiState.value =
                if (_uiState.value is HomeUiState.Success)
                    (_uiState.value as HomeUiState.Success).copy(loading = true)
                else
                    HomeUiState.Loading

            val userName = repository.getUserName()

            val expensesTotal = when (val resp = repository.getUserExpensesTotal()) {
                is Resource.Error -> {
                    _uiState.value = HomeUiState.Error(resp.uiMessage.toStringRes())
                    return@launch
                }

                is Resource.Success -> resp.data
            }

            val incomeTotal = when (val resp = repository.getUserIncomeTotal()) {
                is Resource.Error -> {
                    _uiState.value = HomeUiState.Error(resp.uiMessage.toStringRes())
                    return@launch
                }

                is Resource.Success -> resp.data
            }

            val currentBalance = repository.getUserCurrentBalance(
                income = incomeTotal,
                expenses = expensesTotal
            )

            _uiState.value = HomeUiState.Success(
                uiStateData = HomeUiStateData(
                    userName = userName,
                    expensesTotal = expensesTotal.toCurrency(),
                    incomeTotal = incomeTotal.toCurrency(),
                    currentBalance = currentBalance.toCurrency(),
                    isCurrentBalancePositive = currentBalance > 0
                ),
                loading = false
            )
        }
    }
}

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(val uiStateData: HomeUiStateData, val loading: Boolean) : HomeUiState()
    data class Error(@field:StringRes val messageRes: Int = R.string.error_unknown) : HomeUiState()
}
