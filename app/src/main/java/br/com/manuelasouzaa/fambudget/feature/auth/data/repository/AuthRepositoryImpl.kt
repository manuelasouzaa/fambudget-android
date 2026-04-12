package br.com.manuelasouzaa.fambudget.feature.auth.data.repository

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.AuthWebClient
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.LoginRequest
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.LoginResponse
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.RegisterRequest
import br.com.manuelasouzaa.fambudget.feature.auth.domain.AuthRepository
import okhttp3.ResponseBody

class AuthRepositoryImpl(
    private val webClient: AuthWebClient
) : AuthRepository {

    override suspend fun login(userEmail: String, userPassword: String): Resource<LoginResponse> {
        return webClient.login(LoginRequest(userEmail, userPassword))
    }

    override suspend fun register(
        name: String,
        phoneNumber: String,
        userEmail: String,
        userPassword: String
    ): Resource<ResponseBody?> {
        return webClient.register(RegisterRequest(name, phoneNumber, userEmail, userPassword))
    }
}
