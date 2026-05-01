package br.com.manuelasouzaa.fambudget.feature.home.di.modules

import br.com.manuelasouzaa.fambudget.feature.home.data.repository.HomeRepositoryImpl
import br.com.manuelasouzaa.fambudget.feature.home.domain.HomeRepository
import br.com.manuelasouzaa.fambudget.feature.home.ui.viewmodel.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {

    viewModelOf(::HomeViewModel)

    singleOf(::HomeRepositoryImpl) bind HomeRepository::class

}
