package br.com.manuelasouzaa.fambudget.core.di.modules

import br.com.manuelasouzaa.fambudget.core.session.dataStore
import br.com.manuelasouzaa.fambudget.core.session.repository.SessionRepository
import br.com.manuelasouzaa.fambudget.core.session.repository.SessionRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sessionModule = module {
    single { androidContext().dataStore }
    singleOf(::SessionRepositoryImpl) bind SessionRepository::class
}
