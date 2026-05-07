package br.com.manuelasouzaa.fambudget.feature.income.ui.uistate

import br.com.manuelasouzaa.fambudget.core.ui.model.FormEvent

data class AddIncomeFormUiState(
    val incomeId: Int? = null,
    val value: String = "",
    val dateInitial: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val event: FormEvent? = null
) {
    val isEditMode: Boolean get() = incomeId != null
}
