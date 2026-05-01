package br.com.manuelasouzaa.fambudget.core.network.interceptors

import br.com.manuelasouzaa.fambudget.core.session.repository.SessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val sessionRepository: SessionRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (request.url.encodedPath.contains("/auth")) {
            return chain.proceed(request)
        }

        val token = runBlocking { sessionRepository.getAccessToken() }

        if (request.header("Authorization") != null || token.isBlank()) {
            return chain.proceed(request)
        }

        return chain.proceed(
            request.newBuilder().header("Authorization", "Bearer $token").build()
        )
    }
}
