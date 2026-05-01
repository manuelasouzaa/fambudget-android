package br.com.manuelasouzaa.fambudget.feature.income.data.repository

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.IncomeWebClient
import br.com.manuelasouzaa.fambudget.feature.income.domain.IncomeRepository

class IncomeRepositoryImpl(
    private val webClient: IncomeWebClient
) : IncomeRepository {

    override suspend fun getIncomeTotal(month: Int, year: Int): Resource<Double> {
        val resp = webClient.getIncomeTotal(month, year)

        return when (resp) {
            is Resource.Error -> resp
            is Resource.Success -> return Resource.Success(data = resp.data.total)
        }
    }

}
