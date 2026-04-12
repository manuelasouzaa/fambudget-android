package br.com.manuelasouzaa.fambudget.feature.auth.domain.model

data class User(
    val id: Int,

    val name: String,

    val email: String,

    val nickname: String? = null,

    val phoneNumber: String,

    val family: Family? = null
)

data class Family(
    val id: Int,
    val name: String
)
