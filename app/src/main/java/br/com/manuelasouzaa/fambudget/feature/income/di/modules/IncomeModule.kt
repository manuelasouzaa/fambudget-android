package br.com.manuelasouzaa.fambudget.feature.income.di.modules

import br.com.manuelasouzaa.fambudget.feature.income.data.remote.IncomeService
import br.com.manuelasouzaa.fambudget.feature.income.data.remote.IncomeWebClient
import br.com.manuelasouzaa.fambudget.feature.income.data.repository.IncomeRepositoryImpl
import br.com.manuelasouzaa.fambudget.feature.income.domain.IncomeRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val incomeModule = module {

    single { get<Retrofit>().create(IncomeService::class.java) }

    singleOf(::IncomeWebClient)

    singleOf(::IncomeRepositoryImpl) bind IncomeRepository::class

}
