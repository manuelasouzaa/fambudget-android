package br.com.manuelasouzaa.fambudget.feature.expenses.data.remote.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExpenseListResponse(
    @field:JsonProperty("expenses")
    val expenses: List<ExpenseResponse>,

    @field:JsonProperty("total")
    val total: Double
)
