package br.com.manuelasouzaa.fambudget.feature.expenses.data.remote

import br.com.manuelasouzaa.fambudget.core.network.client.BaseHttpClient
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.expenses.data.remote.model.ExpensesTotalResponse

class ExpensesWebClient(private val service: ExpensesService) : BaseHttpClient() {

    suspend fun getExpensesTotal(month: Int, year: Int): Resource<ExpensesTotalResponse> {
        return safeApiCall { service.getExpensesTotal(month, year) }
    }

}
