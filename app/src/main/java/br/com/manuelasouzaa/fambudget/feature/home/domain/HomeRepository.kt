package br.com.manuelasouzaa.fambudget.feature.home.domain

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource

interface HomeRepository {

    suspend fun getUserName(): String

    fun getUserCurrentBalance(income: Double, expenses: Double): Double

    suspend fun getUserExpensesTotal(): Resource<Double>

    suspend fun getUserIncomeTotal(): Resource<Double>

}
