package br.com.manuelasouzaa.fambudget.feature.income.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.ext.toCurrency
import br.com.manuelasouzaa.fambudget.feature.income.domain.IncomeRepository
import br.com.manuelasouzaa.fambudget.feature.income.ui.uistate.IncomeScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.YearMonth

class IncomeScreenViewModel(
    private val repository: IncomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<IncomeScreenUiState>(IncomeScreenUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _currentMonth = MutableStateFlow(YearMonth.now())
    val currentMonth = _currentMonth.asStateFlow()

    fun previousMonth() {
        _currentMonth.value = _currentMonth.value.minusMonths(1)
        load()
    }

    fun nextMonth() {
        _currentMonth.value = _currentMonth.value.plusMonths(1)
        load()
    }

    fun jumpToMonth(yearMonth: YearMonth) {
        _currentMonth.value = yearMonth
        load()
    }

    fun load() {
        val month = _currentMonth.value
        viewModelScope.launch {
            _uiState.value = IncomeScreenUiState.Loading
            when (val result = repository.getIncomes(month.monthValue, month.year)) {
                is Resource.Success -> {
                    val total = result.data.sumOf { it.value }
                    _uiState.value = IncomeScreenUiState.Success(
                        incomes = result.data,
                        total = total.toCurrency()
                    )
                }

                is Resource.Error -> _uiState.value = IncomeScreenUiState.Error
            }
        }
    }

    fun deleteIncome(incomeId: Int) {
        val current = _uiState.value
        if (current is IncomeScreenUiState.Success) {
            val updatedList = current.incomes.filter { it.id != incomeId }
            val total = updatedList.sumOf { it.value }
            _uiState.value = IncomeScreenUiState.Success(
                incomes = updatedList,
                total = total.toCurrency()
            )
        }
        viewModelScope.launch {
            repository.deleteIncome(incomeId)
        }
    }
}
