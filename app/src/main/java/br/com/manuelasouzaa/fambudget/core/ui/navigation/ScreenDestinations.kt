package br.com.manuelasouzaa.fambudget.core.ui.navigation

sealed class ScreenDestinations(val name: String) {

    data object HomeScreen : ScreenDestinations("home_screen")

    data object AddExpenseFormScreen : ScreenDestinations("add_expense_form_screen")

    data object AddIncomeFormScreen : ScreenDestinations("add_income_form_screen")

    data object TransactionsScreen : ScreenDestinations("transactions_screen")

    data object BudgetsScreen : ScreenDestinations("budgets_screen")

    data object MenuScreen : ScreenDestinations("menu_screen")

    data object ReportsScreen : ScreenDestinations("reports_screen")

    data object UserProfileScreen : ScreenDestinations("user_profile_screen")

}
