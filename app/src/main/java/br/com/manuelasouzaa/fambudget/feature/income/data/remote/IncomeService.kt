package br.com.manuelasouzaa.fambudget.feature.income.data.remote

import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.CreateIncomeRequest
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.IncomeListResponse
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.IncomeTotalResponse
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.model.UpdateIncomeRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface IncomeService {

    @GET("v1/income/user/total")
    suspend fun getIncomeTotal(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): Response<IncomeTotalResponse>

    @POST("v1/income")
    suspend fun createIncome(@Body request: CreateIncomeRequest): Response<Unit>

    @GET("v1/income/user")
    suspend fun getIncomes(
        @Query("month") month: Int,
        @Query("year") year: Int
    ): Response<IncomeListResponse>

    @PUT("v1/income/{incomeId}")
    suspend fun updateIncome(
        @Path("incomeId") incomeId: Int,
        @Body request: UpdateIncomeRequest
    ): Response<Unit>

    @DELETE("v1/income/{incomeId}")
    suspend fun deleteIncome(@Path("incomeId") incomeId: Int): Response<Unit>

}
