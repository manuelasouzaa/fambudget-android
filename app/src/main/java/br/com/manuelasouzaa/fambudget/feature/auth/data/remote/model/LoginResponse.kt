package br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LoginResponse(
    val user: UserResponse,
    val token: TokenResponse
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserResponse(
    val id: Int,

    val name: String,

    val email: String,

    val nickname: String?,

    @JsonProperty("mobileNumber")
    val phoneNumber: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
