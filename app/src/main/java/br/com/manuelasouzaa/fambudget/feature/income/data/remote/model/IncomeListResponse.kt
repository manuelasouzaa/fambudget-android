package br.com.manuelasouzaa.fambudget.feature.income.data.remote.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class IncomeListResponse(
    @field:JsonProperty("revenues")
    val incomes: List<IncomeResponse>,

    @field:JsonProperty("total")
    val total: Double
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class IncomeResponse(
    @field:JsonProperty("id")
    val id: Int,

    @field:JsonProperty("userId")
    val userId: Int,

    @field:JsonProperty("description")
    val description: String?,

    @field:JsonProperty("value")
    val value: Double,

    @field:JsonProperty("dateInitial")
    val dateInitial: String?,

    @field:JsonProperty("familyId")
    val familyId: Int?
)
