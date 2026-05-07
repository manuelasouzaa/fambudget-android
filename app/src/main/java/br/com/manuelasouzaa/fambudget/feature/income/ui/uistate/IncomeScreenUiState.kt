package br.com.manuelasouzaa.fambudget.feature.income.ui.uistate

import androidx.annotation.StringRes
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.IncomeResponse

sealed class IncomeScreenUiState {
    data object Loading : IncomeScreenUiState()
    data class Error(@field:StringRes val messageRes: Int = R.string.error_unknown) :
        IncomeScreenUiState()

    data class Success(
        val incomes: List<IncomeResponse>,
        val total: String
    ) : IncomeScreenUiState()
}
