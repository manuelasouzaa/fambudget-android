package br.com.manuelasouzaa.fambudget.feature.auth.domain

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.LoginResponse
import okhttp3.ResponseBody

interface AuthRepository {

    suspend fun login(userEmail: String, userPassword: String): Resource<LoginResponse>
    suspend fun register(
        name: String,
        phoneNumber: String,
        userEmail: String,
        userPassword: String
    ): Resource<ResponseBody?>

}
