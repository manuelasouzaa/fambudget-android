package br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterRequest(
    val name: String,

    @JsonProperty("mobileNumber")
    val phoneNumber: String,

    val email: String,

    val password: String
)
