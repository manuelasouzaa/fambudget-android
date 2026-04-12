package br.com.manuelasouzaa.fambudget.core.network.interceptors

import br.com.manuelasouzaa.fambudget.core.session.repository.SessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor(
    private val sessionRepository: SessionRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.code == 412) {
            runBlocking {
                sessionRepository.logout()
            }
        }

        return response
    }
}
