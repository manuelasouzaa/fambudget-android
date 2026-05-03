package br.com.manuelasouzaa.fambudget.feature.income.data.repository

import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.IncomeWebClient
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.CreateIncomeRequest
import br.com.manuelasouzaa.fambudget.feature.income.domain.IncomeRepository

class IncomeRepositoryImpl(
    private val webClient: IncomeWebClient
) : IncomeRepository {

    override suspend fun getIncomeTotal(month: Int, year: Int): Resource<Double> {
        return when (val resp = webClient.getIncomeTotal(month, year)) {
            is Resource.Error -> resp
            is Resource.Success -> Resource.Success(data = resp.data.total)
        }
    }

    override suspend fun saveIncome(value: String, dateInitial: String, description: String): Resource<Unit> {
        val request = CreateIncomeRequest(
            value = value.filter { it.isDigit() }.toLongOrNull()?.div(100.0)
                ?: return Resource.Error(uiMessage = UiText.Resource(R.string.error_unknown)),
            dateInitial = dateInitial,
            description = description.ifBlank { null }
        )
        return when (val resp = webClient.createIncome(request)) {
            is Resource.Error -> resp
            is Resource.Success -> Resource.Success(Unit)
        }
    }

}
