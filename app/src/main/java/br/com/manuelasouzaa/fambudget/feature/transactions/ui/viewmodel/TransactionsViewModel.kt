package br.com.manuelasouzaa.fambudget.feature.transactions.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.transactions.domain.TransactionsRepository
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.model.TransactionGroup
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.model.TransactionItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.YearMonth

class TransactionsViewModel(
    private val repository: TransactionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TransactionsUiState>(TransactionsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth = _currentMonth.asStateFlow()

    init { load() }

    fun jumpToMonth(yearMonth: YearMonth) {
        _currentMonth.value = yearMonth
        load()
    }

    fun previousMonth() {
        _currentMonth.value = _currentMonth.value.minusMonths(1)
        load()
    }

    fun nextMonth() {
        _currentMonth.value = _currentMonth.value.plusMonths(1)
        load()
    }

    private fun load() {
        val month = _currentMonth.value
        viewModelScope.launch {
            _uiState.value = TransactionsUiState.Loading
            _uiState.value = when (val result = repository.getTransactions(month.monthValue, month.year)) {
                is Resource.Success -> {
                    val groups = result.data
                    val balance = groups.flatMap { it.items }.fold(0.0) { acc, item ->
                        when (item) {
                            is TransactionItem.Income -> acc + item.value
                            is TransactionItem.Expense -> acc - item.value
                        }
                    }
                    TransactionsUiState.Success(groups, balance)
                }
                is Resource.Error -> TransactionsUiState.Error
            }
        }
    }
}

sealed class TransactionsUiState {
    data object Loading : TransactionsUiState()
    data object Error : TransactionsUiState()
    data class Success(val groups: List<TransactionGroup>, val balance: Double) : TransactionsUiState()
}
