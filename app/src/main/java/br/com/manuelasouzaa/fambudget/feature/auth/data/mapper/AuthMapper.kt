package br.com.manuelasouzaa.fambudget.feature.auth.data.mapper

import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.UserResponse
import br.com.manuelasouzaa.fambudget.feature.auth.domain.model.User

fun UserResponse.toUser() = User(
    id = id,
    name = name,
    email = email,
    phoneNumber = phoneNumber,
    nickname = nickname
)
