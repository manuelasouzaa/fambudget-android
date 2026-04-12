package br.com.manuelasouzaa.fambudget.core.session.model

data class TokenSession(
    val accessToken: String,
    val refreshToken: String
)
