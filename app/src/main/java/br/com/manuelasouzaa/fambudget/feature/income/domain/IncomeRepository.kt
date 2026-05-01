package br.com.manuelasouzaa.fambudget.feature.income.domain

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource

interface IncomeRepository {

    suspend fun getIncomeTotal(month: Int, year: Int): Resource<Double>

}
