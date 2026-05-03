package br.com.manuelasouzaa.fambudget.feature.expenses.data.repository

import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText
import br.com.manuelasouzaa.fambudget.feature.expenses.data.remote.ExpensesWebClient
import br.com.manuelasouzaa.fambudget.feature.expenses.data.remote.model.CreateExpenseRequest
import br.com.manuelasouzaa.fambudget.feature.expenses.domain.ExpensesRepository

class ExpensesRepositoryImpl(
    private val webClient: ExpensesWebClient
) : ExpensesRepository {

    override suspend fun getExpensesTotal(month: Int, year: Int): Resource<Double> {
        return when (val resp = webClient.getExpensesTotal(month, year)) {
            is Resource.Error -> resp
            is Resource.Success -> Resource.Success(data = resp.data.total)
        }
    }

    override suspend fun saveExpense(
        name: String,
        categoryId: Int?,
        value: String,
        initialDate: String,
        isExpensePaid: Boolean,
        paymentDate: String,
        description: String,
        expenseType: Int,
        typePaymentId: Int
    ): Resource<Unit> {
        val request = CreateExpenseRequest(
            name = name,
            categoryId = categoryId ?: return Resource.Error(uiMessage = UiText.Resource(R.string.error_empty_fields)),
            typeExpenseId = expenseType,
            description = description.ifBlank { null },
            value = value.filter { it.isDigit() }.toLongOrNull()?.div(100.0)
                ?: return Resource.Error(uiMessage = UiText.Resource(R.string.error_unknown)),
            dateInitial = initialDate,
            paid = isExpensePaid,
            datePayment = paymentDate.ifBlank { null },
            typePaymentId = if (isExpensePaid) typePaymentId else null
        )
        return when (val resp = webClient.createExpense(request)) {
            is Resource.Error -> resp
            is Resource.Success -> Resource.Success(Unit)
        }
    }

}
