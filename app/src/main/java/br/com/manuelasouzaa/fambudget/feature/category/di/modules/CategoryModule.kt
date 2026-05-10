package br.com.manuelasouzaa.fambudget.feature.category.di.modules

import br.com.manuelasouzaa.fambudget.feature.category.data.remote.CategoryService
import br.com.manuelasouzaa.fambudget.feature.category.data.remote.CategoryWebClient
import br.com.manuelasouzaa.fambudget.feature.category.data.repository.CategoryRepositoryImpl
import br.com.manuelasouzaa.fambudget.feature.category.domain.CategoryRepository
import br.com.manuelasouzaa.fambudget.feature.category.ui.viewmodel.CategoriesScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val categoryModule = module {

    single { get<Retrofit>().create(CategoryService::class.java) }

    singleOf(::CategoryWebClient)

    singleOf(::CategoryRepositoryImpl) bind CategoryRepository::class

    viewModelOf(::CategoriesScreenViewModel)

}
