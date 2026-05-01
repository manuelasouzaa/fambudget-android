package br.com.manuelasouzaa.fambudget.feature.home.data.repository

import android.icu.util.Calendar
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.session.repository.SessionRepository
import br.com.manuelasouzaa.fambudget.feature.expenses.domain.ExpensesRepository
import br.com.manuelasouzaa.fambudget.feature.home.domain.HomeRepository
import br.com.manuelasouzaa.fambudget.feature.income.domain.IncomeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(
    private val sessionRepository: SessionRepository,
    private val expensesRepository: ExpensesRepository,
    private val incomeRepository: IncomeRepository
) : HomeRepository {

    private val calendar = Calendar.getInstance()
    private val month = calendar.get(Calendar.MONTH)
    private val year = calendar.get(Calendar.YEAR)

    override suspend fun getUserName(): String {
        return sessionRepository.userSession.map { it.name }.first()
    }

    override fun getUserCurrentBalance(income: Double, expenses: Double): Double {
        return income - expenses
    }

    override suspend fun getUserExpensesTotal(): Resource<Double> {
        return expensesRepository.getExpensesTotal(month + 1, year)
    }

    override suspend fun getUserIncomeTotal(): Resource<Double> {
        return incomeRepository.getIncomeTotal(month + 1, year)
    }

}
