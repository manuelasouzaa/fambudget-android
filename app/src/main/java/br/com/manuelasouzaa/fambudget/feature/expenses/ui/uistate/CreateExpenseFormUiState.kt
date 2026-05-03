package br.com.manuelasouzaa.fambudget.feature.expenses.ui.uistate

import br.com.manuelasouzaa.fambudget.core.ui.model.FormEvent
import br.com.manuelasouzaa.fambudget.feature.category.ui.model.CategoryUiModel
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.model.ExpenseType
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.model.PaymentType

data class CreateExpenseFormUiState(
    val name: String = "",
    val category: CategoryUiModel? = null,
    val expenseType: ExpenseType = ExpenseType.FIXED,
    val description: String = "",
    val value: String = "",
    val initialDate: String = "",
    val isExpensePaid: Boolean = false,
    val paymentDate: String = "",
    val paymentType: PaymentType = PaymentType.CASH,
    val event: FormEvent? = null
)
