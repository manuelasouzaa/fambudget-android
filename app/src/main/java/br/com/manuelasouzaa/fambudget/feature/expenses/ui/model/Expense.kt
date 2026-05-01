package br.com.manuelasouzaa.fambudget.feature.expenses.ui.model

data class Expense(
    val name: String,
    val categoryId: Int,
    val typeExpenseId: Int,
    val description: String,
    val value: Double,
    val dateInitial: String,
    val familyId: Int,
    val paid: Boolean,
    val datePayment: String,
    val typePaymentId: Int
)
