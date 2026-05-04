package br.com.manuelasouzaa.fambudget.feature.expenses.data.remote.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExpenseResponse(
    @field:JsonProperty("id")
    val id: Int,

    @field:JsonProperty("userId")
    val userId: Int,

    @field:JsonProperty("name")
    val name: String,

    @field:JsonProperty("categoryId")
    val categoryId: Int,

    @field:JsonProperty("typeExpenseId")
    val typeExpenseId: Int,

    @field:JsonProperty("description")
    val description: String?,

    @field:JsonProperty("value")
    val value: Double,

    @field:JsonProperty("familyId")
    val familyId: Int?,

    @field:JsonProperty("dateInitial")
    val dateInitial: String?,

    @field:JsonProperty("createdAt")
    val createdAt: String?,

    @field:JsonProperty("status")
    val status: String,

    @field:JsonProperty("payment")
    val payment: PaymentResponse?
)

data class PaymentResponse(
    @field:JsonProperty("id")
    val id: Int,

    @field:JsonProperty("expenseId")
    val expenseId: Int,

    @field:JsonProperty("datePayment")
    val datePayment: String?,

    @field:JsonProperty("typePaymentId")
    val typePaymentId: Int?,

    @field:JsonProperty("paid")
    val paid: Boolean
)
