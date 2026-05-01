package br.com.manuelasouzaa.fambudget.core.network.authenticator

import br.com.manuelasouzaa.fambudget.core.session.model.TokenSession
import br.com.manuelasouzaa.fambudget.core.session.repository.SessionRepository
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.AuthService
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.RefreshTokenRequest
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(
    private val sessionRepository: SessionRepository,
    private val authService: Lazy<AuthService>
) : Authenticator {

    private val lock = Any()

    override fun authenticate(route: Route?, response: Response): Request? {
        if (responseCount(response) >= 2) return null

        val newToken = synchronized(lock) {
            runBlocking {
                val currentToken = sessionRepository.getAccessToken()
                val requestToken = response.request.header("Authorization")?.removePrefix("Bearer ")
                if (requestToken != null && currentToken != requestToken && currentToken.isNotBlank()) {
                    return@runBlocking currentToken
                }
                try {
                    val resp = authService.value.getRefreshToken(
                        RefreshTokenRequest(sessionRepository.getRefreshToken())
                    )
                    val body = resp.body()
                    if (resp.isSuccessful && body != null) {
                        sessionRepository.saveToken(TokenSession(body.accessToken, body.refreshToken))
                        body.accessToken
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
        }

        if (newToken == null) {
            runBlocking { sessionRepository.expireSession() }
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
