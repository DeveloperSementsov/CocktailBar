package com.developersementsov.cocktailbar.ui.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class CocktailsFragmentFactory: FragmentFactory() {
    override fun instantiate(
        classLoader: ClassLoader,
        className: String
    ) = when (className) {
        MainFragment::class.java.name -> MainFragment()
        CocktailFragment::class.java.name -> CocktailFragment()
        IngredientFragment::class.java.name -> IngredientFragment()
        AboutAppFragment::class.java.name -> AboutAppFragment()
        else -> createFragmentAsFallback(classLoader, className)
    }

    private fun createFragmentAsFallback(classLoader: ClassLoader, className: String): Fragment {
        Log.d(
            FragmentFactory::class.java.simpleName,
            "No creator found for class: $className. Using default constructor"
        )
        return super.instantiate(classLoader, className)
    }
}