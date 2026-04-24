package br.com.manuelasouzaa.fambudget.feature.auth.domain.usecase

import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText
import br.com.manuelasouzaa.fambudget.core.util.InputValidator.isPasswordValid
import br.com.manuelasouzaa.fambudget.feature.auth.domain.AuthRepository
import okhttp3.ResponseBody

class ForgotPasswordUseCase(
    private val repository: AuthRepository
) {
    suspend fun validateCode(code: String): Resource<ResponseBody?> {
        return repository.validateCode(code)
    }

    suspend fun sendForgotPasswordEmail(userEmail: String): Resource<ResponseBody?> {
        return repository.sendForgotPasswordEmail(userEmail)
    }

    suspend fun resetPassword(email: String, newPassword: String): Resource<ResponseBody?> {
        if (!isPasswordValid(newPassword))
            return Resource.Error(uiMessage = UiText.Resource(R.string.auth_error_password_invalid_format))

        return repository.resetPassword(email, newPassword)
    }

}
