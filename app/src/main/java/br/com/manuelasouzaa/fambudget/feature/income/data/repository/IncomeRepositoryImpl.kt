package br.com.manuelasouzaa.fambudget.feature.income.data.repository

import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.IncomeWebClient
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.CreateIncomeRequest
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.IncomeResponse
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.UpdateIncomeRequest
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

    override suspend fun getIncomes(month: Int, year: Int): Resource<List<IncomeResponse>> {
        return when (val resp = webClient.getIncomes(month, year)) {
            is Resource.Error -> resp
            is Resource.Success -> Resource.Success(data = resp.data.incomes)
        }
    }

    override suspend fun saveIncome(
        value: String,
        dateInitial: String,
        description: String
    ): Resource<Unit> {
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

    override suspend fun updateIncome(
        incomeId: Int,
        value: Double?,
        dateInitial: String?,
        description: String?
    ): Resource<Unit> {
        val request = UpdateIncomeRequest(
            value = value,
            dateInitial = dateInitial,
            description = description
        )
        return when (val resp = webClient.updateIncome(incomeId, request)) {
            is Resource.Error -> resp
            is Resource.Success -> Resource.Success(Unit)
        }
    }

    override suspend fun deleteIncome(incomeId: Int): Resource<Unit> {
        return when (val resp = webClient.deleteIncome(incomeId)) {
            is Resource.Error -> resp
            is Resource.Success -> Resource.Success(Unit)
        }
    }

}
