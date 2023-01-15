package com.developersementsov.cocktailbar.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedViewModel @Inject constructor() : ViewModel() {
    private val selectedIngredientStateFlow = MutableStateFlow<String?>(value = null)
    private val selectedCocktailIdStateFlow = MutableStateFlow<String?>(value = null)

    fun shareSelectedIngredient(ingredient: String){
        viewModelScope.launch(getCoroutineExceptionHandler()) {
            selectedIngredientStateFlow.emit(ingredient)
        }

    }

    fun shareSelectedCocktailId(id: String?){
        viewModelScope.launch(getCoroutineExceptionHandler()) {
            selectedCocktailIdStateFlow.value = id
        }

    }

    private fun getCoroutineExceptionHandler(): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            println(exception.message)
        }
    }

    internal fun getSelectedIngredient(): Flow<String?> = selectedIngredientStateFlow
    internal fun getSelectedCocktailId(): Flow<String?> = selectedCocktailIdStateFlow
}