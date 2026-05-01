package br.com.manuelasouzaa.fambudget.core.session.repository

import br.com.manuelasouzaa.fambudget.core.session.model.TokenSession
import br.com.manuelasouzaa.fambudget.core.session.model.UserSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface SessionRepository {

    val userSession: Flow<UserSession>

    val sessionExpired: SharedFlow<Unit>

    suspend fun saveUser(user: UserSession)

    suspend fun expireSession()

    suspend fun logout()

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun saveToken(tokenSession: TokenSession)

}
