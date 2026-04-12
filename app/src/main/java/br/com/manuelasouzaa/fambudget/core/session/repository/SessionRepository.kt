package br.com.manuelasouzaa.fambudget.core.session.repository

import br.com.manuelasouzaa.fambudget.core.session.model.TokenSession
import br.com.manuelasouzaa.fambudget.core.session.model.UserSession
import kotlinx.coroutines.flow.Flow

interface SessionRepository {

    val userSession: Flow<UserSession>

    suspend fun saveUser(user: UserSession)

    suspend fun finishSession()

    suspend fun logout()

    suspend fun getAccessToken(): String

    suspend fun saveToken(tokenSession: TokenSession)

}
