package br.com.manuelasouzaa.fambudget.feature.income.ui.uistate

import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.IncomeResponse

sealed class IncomeScreenUiState {
    data object Loading : IncomeScreenUiState()
    data object Error : IncomeScreenUiState()
    data class Success(
        val incomes: List<IncomeResponse>,
        val total: String
    ) : IncomeScreenUiState()
}
