package br.com.manuelasouzaa.fambudget.core.network.authenticator

import br.com.manuelasouzaa.fambudget.core.session.model.TokenSession
import br.com.manuelasouzaa.fambudget.core.session.repository.SessionRepository
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.AuthService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val sessionRepository: SessionRepository,
    private val authService: Lazy<AuthService>
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 3) return null
        val newToken = runBlocking {
            try {
                val resp = authService.value.getRefreshToken(sessionRepository.getAccessToken())

                resp.body()?.let {
                    sessionRepository.saveToken(
                        TokenSession(
                            it.accessToken,
                            it.refreshToken
                        )
                    )
                }

                sessionRepository.getAccessToken()
            } catch (e: Exception) {
                null
            }
        }

        if (newToken == null) {
            runBlocking { sessionRepository.logout() }
            return null
        }

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }


    private fun responseCount(response: Response): Int {
        var count = 1
        var priorResponse = response.priorResponse
        while (priorResponse != null) {
            count++
            priorResponse = priorResponse.priorResponse
        }
        return count
    }
}
