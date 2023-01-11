package com.developersementsov.cocktailbar.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersementsov.cocktailbar.adapter.DrinkItem
import com.developersementsov.network.CocktailBarService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IngredientViewModel @Inject constructor(
    private val cocktailBarService: CocktailBarService
) : ViewModel() {
    private val ingredientsStateFlow = MutableStateFlow<MutableList<String>?>(value = null)
    private val drinksStateFlow = MutableStateFlow<MutableList<DrinkItem>?>(value = null)

    init {
        getIngredients()
    }

    private fun getIngredients() {
        viewModelScope.launch(getCoroutineExceptionHandler()) {
            withContext(Dispatchers.IO) {
                val ingredients = cocktailBarService.getIngredients()
                val list = mutableListOf<String>()
                ingredients?.ingredients?.forEach {
                    list.add(it.ingredient)
                }
                ingredientsStateFlow.emit(list)
            }
        }
    }

    fun getByIngredient(ingredient: String) {
        viewModelScope.launch(getCoroutineExceptionHandler()) {
            withContext(Dispatchers.IO) {
                val drinks = cocktailBarService.getByIngredient(ingredient)
                val list = mutableListOf<DrinkItem>()
                drinks?.drinks?.forEach {
                    list.add(
                        DrinkItem(
                            it.strDrinkThumb,
                            it.strDrinkName
                        )
                    )
                }
                drinksStateFlow.emit(list)
            }
        }
    }

    private fun getCoroutineExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            println(exception.message)
        }
    }

    internal fun getIngredientsFlow(): Flow<MutableList<String>?> = ingredientsStateFlow
    internal fun getDrinksFlow(): Flow<MutableList<DrinkItem>?> = drinksStateFlow
}