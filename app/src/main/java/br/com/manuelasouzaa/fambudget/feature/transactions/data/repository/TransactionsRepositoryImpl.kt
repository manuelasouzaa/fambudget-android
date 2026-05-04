package br.com.manuelasouzaa.fambudget.feature.transactions.data.repository

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.expenses.data.remote.ExpensesWebClient
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.IncomeWebClient
import br.com.manuelasouzaa.fambudget.feature.transactions.domain.TransactionsRepository
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.model.TransactionGroup
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.model.TransactionItem
import java.time.LocalDate

class TransactionsRepositoryImpl(
    private val expensesWebClient: ExpensesWebClient,
    private val incomeWebClient: IncomeWebClient
) : TransactionsRepository {

    override suspend fun getTransactions(month: Int, year: Int): Resource<List<TransactionGroup>> {
        val expensesResult = expensesWebClient.getPaidExpenses(month, year)
        val incomeResult = incomeWebClient.getIncomes(month, year)

        if (expensesResult is Resource.Error) return expensesResult
        if (incomeResult is Resource.Error) return incomeResult

        val expenses = (expensesResult as Resource.Success).data.expenses
        val incomes = (incomeResult as Resource.Success).data.incomes

        val items = buildList {
            expenses.forEach { e ->
                val date = e.payment?.datePayment?.toLocalDateOrNull() ?: return@forEach
                add(TransactionItem.Expense(name = e.name, value = e.value, date = date))
            }
            incomes.forEach { i ->
                val date = i.dateInitial?.toLocalDateOrNull() ?: return@forEach
                add(TransactionItem.Income(
                    description = i.description,
                    value = i.value,
                    date = date
                ))
            }
        }

        val groups = items
            .groupBy { it.date() }
            .entries
            .sortedByDescending { it.key }
            .map { (date, groupItems) -> TransactionGroup(date, groupItems) }

        return Resource.Success(groups)
    }

    private fun TransactionItem.date() = when (this) {
        is TransactionItem.Expense -> date
        is TransactionItem.Income -> date
    }

    private fun String.toLocalDateOrNull(): LocalDate? = try {
        LocalDate.parse(this)
    } catch (e: Exception) {
        null
    }
}
