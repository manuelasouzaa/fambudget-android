package br.com.manuelasouzaa.fambudget.feature.expenses.data.remote.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateExpenseRequest(
    @field:JsonProperty("name")
    val name: String,

    @JsonProperty("categoryId")
    val categoryId: Int,

    @JsonProperty("typeExpenseId")
    val typeExpenseId: Int,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("value")
    val value: Double,

    @JsonProperty("dateInitial")
    val dateInitial: String,

    @JsonProperty("familyId")
    val familyId: Int? = null,

    @JsonProperty("paid")
    val paid: Boolean? = false,

    @JsonProperty("datePayment")
    val datePayment: String? = null,

    @JsonProperty("typePaymentId")
    val typePaymentId: Int? = null
)
