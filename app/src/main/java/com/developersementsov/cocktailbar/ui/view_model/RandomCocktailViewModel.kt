package com.developersementsov.cocktailbar.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersementsov.data.entity.Drink
import com.developersementsov.network.CocktailBarService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RandomCocktailViewModel @Inject constructor(
    private val cocktailBarService: CocktailBarService
) : ViewModel() {
    private val randomCocktailStateFlow = MutableStateFlow<Drink?>(value = null)

    fun getRandomCocktail(){
        viewModelScope.launch(getCoroutineExceptionHandler()) {
            withContext(Dispatchers.IO) {
                val drinks = cocktailBarService.getRandomCocktail()
                randomCocktailStateFlow.emit(drinks?.drinks?.firstOrNull())
            }
        }
    }

    private fun getCoroutineExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            println(exception.message)
        }
    }

    internal fun getRandomCocktailFlow(): Flow<Drink?> = randomCocktailStateFlow
}