package com.developersementsov.cocktailbar.di

import android.content.Context
import com.developersementsov.cocktailbar.ui.fragment.IngredientFragment
import com.developersementsov.cocktailbar.ui.fragment.RandomCocktailFragment
import com.developersementsov.network.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }

    fun inject(randomCocktailFragment: RandomCocktailFragment)
    fun inject(ingredientFragment: IngredientFragment)
}