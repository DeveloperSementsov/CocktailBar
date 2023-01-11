package com.developersementsov.cocktailbar.di

import android.app.Application
import android.content.Context
import com.developersementsov.cocktailbar.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideContext(): Context = this.application
}