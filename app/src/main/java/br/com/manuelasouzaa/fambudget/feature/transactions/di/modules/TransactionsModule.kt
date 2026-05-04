package br.com.manuelasouzaa.fambudget.feature.transactions.di.modules

import br.com.manuelasouzaa.fambudget.feature.transactions.data.repository.TransactionsRepositoryImpl
import br.com.manuelasouzaa.fambudget.feature.transactions.domain.TransactionsRepository
import br.com.manuelasouzaa.fambudget.feature.transactions.ui.viewmodel.TransactionsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val transactionsModule = module {
    singleOf(::TransactionsRepositoryImpl) bind TransactionsRepository::class
    viewModelOf(::TransactionsViewModel)
}
