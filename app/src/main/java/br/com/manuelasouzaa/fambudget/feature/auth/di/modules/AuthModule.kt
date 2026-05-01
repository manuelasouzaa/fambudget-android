package br.com.manuelasouzaa.fambudget.feature.auth.di.modules

import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.AuthService
import br.com.manuelasouzaa.fambudget.feature.auth.data.remote.AuthWebClient
import br.com.manuelasouzaa.fambudget.feature.auth.data.repository.AuthRepositoryImpl
import br.com.manuelasouzaa.fambudget.feature.auth.domain.AuthRepository
import br.com.manuelasouzaa.fambudget.feature.auth.domain.usecase.LoginUseCase
import br.com.manuelasouzaa.fambudget.feature.auth.domain.usecase.RegisterUseCase
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.LoginViewModel
import br.com.manuelasouzaa.fambudget.feature.auth.ui.viewmodel.RegisterViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val authModule = module {
    single { get<Retrofit>().create(AuthService::class.java) }

    viewModelOf(::LoginViewModel)

    singleOf(::LoginUseCase)

    viewModelOf(::RegisterViewModel)

    singleOf(::RegisterUseCase)

    singleOf(::AuthWebClient)

    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
}
