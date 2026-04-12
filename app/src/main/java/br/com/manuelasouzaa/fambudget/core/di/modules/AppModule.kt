package br.com.manuelasouzaa.fambudget.core.di.modules

import br.com.manuelasouzaa.fambudget.core.ui.viewmodel.RootViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::RootViewModel)
}
