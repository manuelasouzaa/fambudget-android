package br.com.manuelasouzaa.fambudget.core.session.model

data class UserSession(
    val id: Int,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val isLoggedIn: Boolean
)
