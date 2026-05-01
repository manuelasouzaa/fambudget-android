package br.com.manuelasouzaa.fambudget.feature.income.data.remote

import br.com.manuelasouzaa.fambudget.core.network.client.BaseHttpClient
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.IncomeTotalResponse

class IncomeWebClient(private val service: IncomeService) : BaseHttpClient() {

    suspend fun getIncomeTotal(month: Int, year: Int): Resource<IncomeTotalResponse> {
        return safeApiCall { service.getIncomeTotal(month, year) }
    }

}
