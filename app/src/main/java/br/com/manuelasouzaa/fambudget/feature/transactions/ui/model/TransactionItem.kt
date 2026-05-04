package br.com.manuelasouzaa.fambudget.feature.transactions.ui.model

import java.time.LocalDate

sealed class TransactionItem {
    data class Income(val description: String?, val value: Double, val date: LocalDate) : TransactionItem()
    data class Expense(val name: String, val value: Double, val date: LocalDate) : TransactionItem()
}

data class TransactionGroup(
    val date: LocalDate,
    val items: List<TransactionItem>
)
