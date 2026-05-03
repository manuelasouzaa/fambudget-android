package br.com.manuelasouzaa.fambudget.feature.expenses.di.modules

import br.com.manuelasouzaa.fambudget.feature.expenses.data.remote.ExpensesService
import br.com.manuelasouzaa.fambudget.feature.expenses.data.remote.ExpensesWebClient
import br.com.manuelasouzaa.fambudget.feature.expenses.data.repository.ExpensesRepositoryImpl
import br.com.manuelasouzaa.fambudget.feature.expenses.domain.ExpensesRepository
import br.com.manuelasouzaa.fambudget.feature.expenses.ui.viewmodel.CreateExpenseFormViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val expensesModule = module {

    single { get<Retrofit>().create(ExpensesService::class.java) }

    singleOf(::ExpensesRepositoryImpl) bind ExpensesRepository::class

    singleOf(::ExpensesWebClient)

    viewModelOf(::CreateExpenseFormViewModel)

}
