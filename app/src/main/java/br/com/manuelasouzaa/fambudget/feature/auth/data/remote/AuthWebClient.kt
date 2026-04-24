package br.com.manuelasouzaa.fambudget.feature.auth.data.remote

import br.com.manuelasouzaa.fambudget.core.network.client.BaseHttpClient
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.LoginRequest
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.LoginResponse
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.RegisterRequest
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.ResetPasswordRequest
import okhttp3.ResponseBody

class AuthWebClient(
    private val service: AuthService
) : BaseHttpClient() {

    suspend fun login(loginRequest: LoginRequest): Resource<LoginResponse> {
        return safeApiCall { service.login(loginRequest) }
    }

    suspend fun register(registerRequest: RegisterRequest): Resource<ResponseBody?> {
        return safeApiCall { service.register(registerRequest) }
    }

    suspend fun validateCode(code: String): Resource<ResponseBody?> {
        return safeApiCall { service.validateCode(code) }
    }

    suspend fun forgotPassword(emailUser: String): Resource<ResponseBody?> {
        return safeApiCall { service.forgotPassword(emailUser) }
    }

    suspend fun resetPassword(resetPassword: ResetPasswordRequest): Resource<ResponseBody?> {
        return safeApiCall { service.resetPassword(resetPassword) }
    }

}
