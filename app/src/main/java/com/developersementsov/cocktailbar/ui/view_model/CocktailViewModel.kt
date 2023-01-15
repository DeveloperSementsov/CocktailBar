package com.developersementsov.cocktailbar.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developersementsov.data.entity.Drink
import com.developersementsov.di.IoDispatcher
import com.developersementsov.network.CocktailBarService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CocktailViewModel @Inject constructor(
    private val cocktailBarService: CocktailBarService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val cocktailStateFlow = MutableStateFlow<Drink?>(value = null)

    fun getCocktail(id: String?) {
        viewModelScope.launch(getCoroutineExceptionHandler()) {
            withContext(dispatcher) {
                if (id.isNullOrEmpty()) {
                    getRandomCocktail()
                } else {
                    val drinks = cocktailBarService.getCocktailById(id)
                    cocktailStateFlow.emit(drinks?.drinks?.firstOrNull())
                }
            }
        }
    }

    private fun getRandomCocktail() {
        viewModelScope.launch(getCoroutineExceptionHandler()) {
            withContext(Dispatchers.IO) {
                val drinks = cocktailBarService.getRandomCocktail()
                cocktailStateFlow.emit(drinks?.drinks?.firstOrNull())
            }
        }
    }

    private fun getCoroutineExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            println(exception.message)
        }
    }

    internal fun getCocktailFlow(): Flow<Drink?> = cocktailStateFlow
}