package br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model

data class ResetPasswordRequest(
    val email: String,
    val newPassword: String
)
