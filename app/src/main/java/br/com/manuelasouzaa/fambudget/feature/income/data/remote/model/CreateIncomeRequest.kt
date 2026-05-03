package br.com.manuelasouzaa.fambudget.feature.income.data.remote.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateIncomeRequest(
    @field:JsonProperty("value")
    val value: Double,

    @field:JsonProperty("dateInitial")
    val dateInitial: String,

    @field:JsonProperty("description")
    val description: String? = null
)
