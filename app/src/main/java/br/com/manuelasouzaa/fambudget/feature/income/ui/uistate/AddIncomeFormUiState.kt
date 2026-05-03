package br.com.manuelasouzaa.fambudget.feature.income.ui.uistate

import br.com.manuelasouzaa.fambudget.core.ui.model.FormEvent

data class AddIncomeFormUiState(
    val value: String = "",
    val dateInitial: String = "",
    val description: String = "",
    val event: FormEvent? = null
)
