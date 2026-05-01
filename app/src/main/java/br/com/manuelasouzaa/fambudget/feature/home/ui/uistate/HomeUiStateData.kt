package br.com.manuelasouzaa.fambudget.feature.home.ui.uistate

data class HomeUiStateData(
    val userName: String = "",
    val currentBalance: String = "",
    val isCurrentBalancePositive: Boolean = true,
    val expensesTotal: String = "",
    val incomeTotal: String = ""
)
