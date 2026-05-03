package br.com.manuelasouzaa.fambudget.feature.expenses.domain

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource

interface ExpensesRepository {

    suspend fun getExpensesTotal(month: Int, year: Int): Resource<Double>

    suspend fun saveExpense(
        name: String,
        categoryId: Int?,
        value: String,
        initialDate: String,
        isExpensePaid: Boolean,
        paymentDate: String,
        description: String,
        expenseType: Int,
        typePaymentId: Int
    ): Resource<Unit>

}
