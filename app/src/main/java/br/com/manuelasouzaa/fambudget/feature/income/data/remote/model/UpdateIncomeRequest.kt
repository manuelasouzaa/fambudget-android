package br.com.manuelasouzaa.fambudget.feature.income.data.remote.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateIncomeRequest(
    @field:JsonProperty("value")
    val value: Double? = null,

    @field:JsonProperty("dateInitial")
    val dateInitial: String? = null,

    @field:JsonProperty("description")
    val description: String? = null
)
