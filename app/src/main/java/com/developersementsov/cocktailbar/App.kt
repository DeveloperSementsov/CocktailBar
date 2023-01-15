package com.developersementsov.cocktailbar

import android.app.Application
import com.developersementsov.cocktailbar.di.ApplicationComponent
import com.developersementsov.cocktailbar.di.DaggerApplicationComponent

open class App: Application() {

    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }
}