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

class CocktailListViewModel @Inject constructor(
    private val cocktailBarService: CocktailBarService
) : ViewModel() {
    private val drinksStateFlow = MutableStateFlow<MutableList<DrinkItem>?>(value = null)

    fun getByIngredient(ingredient: String) {
        viewModelScope.launch(getCoroutineExceptionHandler()) {
            withContext(Dispatchers.IO) {
                val drinks = cocktailBarService.getByIngredient(ingredient)
                val list = mutableListOf<DrinkItem>()
                drinks?.drinks?.forEach {
                    list.add(
                        DrinkItem(
                            it.idDrink,
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

    internal fun getDrinksFlow(): Flow<MutableList<DrinkItem>?> = drinksStateFlow
}