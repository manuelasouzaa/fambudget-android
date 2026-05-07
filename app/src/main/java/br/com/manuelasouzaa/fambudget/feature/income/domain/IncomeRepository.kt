package br.com.manuelasouzaa.fambudget.feature.income.domain

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.IncomeResponse

interface IncomeRepository {

    suspend fun getIncomeTotal(month: Int, year: Int): Resource<Double>

    suspend fun getIncomes(month: Int, year: Int): Resource<List<IncomeResponse>>

    suspend fun saveIncome(value: String, dateInitial: String, description: String): Resource<Unit>

    suspend fun updateIncome(
        incomeId: Int,
        value: Double?,
        dateInitial: String?,
        description: String?
    ): Resource<Unit>

    suspend fun deleteIncome(incomeId: Int): Resource<Unit>

}
