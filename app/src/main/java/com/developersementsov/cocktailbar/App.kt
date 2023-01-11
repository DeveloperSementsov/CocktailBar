package com.developersementsov.cocktailbar

import android.app.Application
import com.developersementsov.cocktailbar.di.ApplicationComponent
import com.developersementsov.cocktailbar.di.DaggerApplicationComponent

open class App: Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

//    override fun attachBaseContext(base: Context?) {
//        super.attachBaseContext(base)
////        setupDI()
//    }

//    private fun setupDI() {
//        applicationComponent = DaggerApplicationComponent.builder()
//            .applicationModule(ApplicationModule(this))
//            .build()
//    }
//
//    fun getApplicationComponent(): ApplicationComponent {
//        return applicationComponent
//    }
}