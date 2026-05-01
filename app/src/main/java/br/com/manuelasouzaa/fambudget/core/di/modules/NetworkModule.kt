package br.com.manuelasouzaa.fambudget.core.di.modules

import br.com.manuelasouzaa.fambudget.BuildConfig
import br.com.manuelasouzaa.fambudget.core.network.authenticator.TokenAuthenticator
import br.com.manuelasouzaa.fambudget.core.network.interceptors.AuthInterceptor
import br.com.manuelasouzaa.fambudget.core.network.interceptors.ErrorInterceptor
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

val networkModule = module {
    singleOf(::AuthInterceptor)

    singleOf(::ErrorInterceptor)

    single { TokenAuthenticator(get(), inject()) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<AuthInterceptor>())
            .authenticator(get<TokenAuthenticator>())
            .addInterceptor(get<ErrorInterceptor>())
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get())
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .build()
    }

}
