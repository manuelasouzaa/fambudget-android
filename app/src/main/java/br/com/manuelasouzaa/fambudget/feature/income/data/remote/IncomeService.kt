package br.com.manuelasouzaa.fambudget.feature.income.data.remote

import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.IncomeTotalResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IncomeService {

    @GET("v1/income/user/total")
    suspend fun getIncomeTotal(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): Response<IncomeTotalResponse>

}
