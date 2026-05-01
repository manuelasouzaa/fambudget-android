package br.com.manuelasouzaa.fambudget.feature.expenses.domain

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource

interface ExpensesRepository {

    suspend fun getExpensesTotal(month: Int, year: Int): Resource<Double>

}
