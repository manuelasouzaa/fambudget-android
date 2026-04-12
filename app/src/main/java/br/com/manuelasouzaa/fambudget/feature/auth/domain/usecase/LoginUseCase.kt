package br.com.manuelasouzaa.fambudget.feature.auth.domain.usecase

import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.session.model.TokenSession
import br.com.manuelasouzaa.fambudget.core.session.model.UserSession
import br.com.manuelasouzaa.fambudget.core.session.repository.SessionRepository
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.model.LoginResponse
import br.com.manuelasouzaa.fambudget.feature.auth.domain.AuthRepository

class LoginUseCase(
    private val sessionRepository: SessionRepository,
    private val repository: AuthRepository
) {
    suspend fun login(email: String, password: String): Resource<LoginResponse> {
        val result = repository.login(email, password)
        if (result is Resource.Success) {
            val response = result.data

            sessionRepository.saveToken(
                TokenSession(response.token.accessToken, response.token.refreshToken)
            )

            sessionRepository.saveUser(
                UserSession(
                    id = response.user.id,
                    name = response.user.name,
                    email = response.user.email,
                    phoneNumber = response.user.phoneNumber,
                    isLoggedIn = true
                )
            )
        }

        return result
    }
}
