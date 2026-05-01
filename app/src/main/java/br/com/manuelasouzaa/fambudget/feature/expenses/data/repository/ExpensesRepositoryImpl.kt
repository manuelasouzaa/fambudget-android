package br.com.manuelasouzaa.fambudget.feature.expenses.data.repository

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.expenses.data.remote.ExpensesWebClient
import br.com.manuelasouzaa.fambudget.feature.expenses.domain.ExpensesRepository

class ExpensesRepositoryImpl(
    private val webClient: ExpensesWebClient
) : ExpensesRepository {

    override suspend fun getExpensesTotal(month: Int, year: Int): Resource<Double> {
        val resp = webClient.getExpensesTotal(month, year)

        return when (resp) {
            is Resource.Error -> resp
            is Resource.Success -> return Resource.Success(data = resp.data.total)
        }
    }

}
