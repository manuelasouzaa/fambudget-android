package br.com.manuelasouzaa.fambudget.application

import android.app.Application
import br.com.manuelasouzaa.fambudget.core.di.modules.appModule
import br.com.manuelasouzaa.fambudget.core.di.modules.networkModule
import br.com.manuelasouzaa.fambudget.core.di.modules.sessionModule
import br.com.manuelasouzaa.fambudget.feature.auth.di.modules.authModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FamBudgetApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@FamBudgetApplication)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    sessionModule,
                    authModule,
                )
            )
        }
    }
}
