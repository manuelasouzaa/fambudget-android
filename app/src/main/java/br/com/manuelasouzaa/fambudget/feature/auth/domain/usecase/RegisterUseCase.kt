package br.com.manuelasouzaa.fambudget.feature.auth.domain.usecase

import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText
import br.com.manuelasouzaa.fambudget.core.util.InputValidator.isPasswordValid
import br.com.manuelasouzaa.fambudget.feature.auth.domain.AuthRepository
import okhttp3.ResponseBody

class RegisterUseCase(
    private val repository: AuthRepository
) {
    suspend fun register(
        name: String,
        phoneNumber: String,
        email: String,
        password: String,
    ): Resource<ResponseBody?> {
        if (!isPasswordValid(password))
            return Resource.Error(uiMessage = UiText.Resource(R.string.auth_error_password_invalid_format))

        return repository.register(name, phoneNumber, email, password)
    }
}
