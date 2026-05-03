package br.com.manuelasouzaa.fambudget.feature.income.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.ui.model.FormEvent
import br.com.manuelasouzaa.fambudget.feature.income.domain.IncomeRepository
import br.com.manuelasouzaa.fambudget.feature.income.ui.uistate.AddIncomeFormUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddIncomeFormViewModel(
    private val incomeRepository: IncomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddIncomeFormUiState())
    val uiState: StateFlow<AddIncomeFormUiState> = _uiState.asStateFlow()

    fun onValueChange(value: String) {
        _uiState.value = uiState.value.copy(value = value)
    }

    fun onDateChange(date: String) {
        _uiState.value = uiState.value.copy(dateInitial = date)
    }

    fun onDescriptionChange(description: String) {
        _uiState.value = uiState.value.copy(description = description)
    }

    fun saveIncome() {
        val state = uiState.value
        if (state.value.isBlank() || state.value == "0" || state.dateInitial.isBlank()) {
            _uiState.value = state.copy(event = FormEvent.Error(R.string.error_empty_fields, navigateBack = false))
            return
        }
        viewModelScope.launch {
            when (incomeRepository.saveIncome(state.value, state.dateInitial, state.description)) {
                is Resource.Success -> _uiState.value = state.copy(event = FormEvent.Success(R.string.success_income_created))
                is Resource.Error -> _uiState.value = state.copy(event = FormEvent.Error(R.string.error_unknown, navigateBack = false))
            }
        }
    }
}
