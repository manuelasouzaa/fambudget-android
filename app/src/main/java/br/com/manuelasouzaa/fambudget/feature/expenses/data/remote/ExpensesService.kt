package br.com.manuelasouzaa.fambudget.feature.expenses.data.remote

import br.com.manuelasouzaa.fambudget.feature.expenses.data.remote.model.ExpensesTotalResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExpensesService {

    @GET("v1/expense/user/total")
    suspend fun getExpensesTotal(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): Response<ExpensesTotalResponse>

}
