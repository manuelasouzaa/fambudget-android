package br.com.manuelasouzaa.fambudget.feature.auth.data.remote

import br.com.manuelasouzaa.fambudget.core.session.model.TokenSession
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.LoginRequest
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.LoginResponse
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.RefreshTokenRequest
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.RegisterRequest
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.ResetPasswordRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {

    @POST("v1/auth/refresh_token")
    suspend fun getRefreshToken(@Body request: RefreshTokenRequest): Response<TokenSession>

    @POST("v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("v1/user/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<ResponseBody?>

    @POST("v1/user/forgot-password/email/{emailUser}")
    suspend fun forgotPassword(@Path("emailUser") emailUser: String): Response<ResponseBody?>

    @GET("v1/user/validate-code")
    suspend fun validateCode(@Query("code") code: String): Response<ResponseBody?>

    @POST("v1/user/reset-password")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<ResponseBody?>

}
