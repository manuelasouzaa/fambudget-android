package br.com.manuelasouzaa.fambudget.feature.auth.data.remote

import br.com.manuelasouzaa.fambudget.core.session.model.TokenSession
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.LoginRequest
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.LoginResponse
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.RefreshTokenRequest
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.RegisterRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("v1/auth/refresh_token")
    suspend fun getRefreshToken(@Body request: RefreshTokenRequest): Response<TokenSession>

    @POST("v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("v1/user/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<ResponseBody?>

}
