package com.developersementsov.network

import com.developersementsov.data.entity.Drinks
import com.developersementsov.data.entity.Ingredients
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailBarService {

    @GET("random.php")
    suspend fun getRandomCocktail() : Drinks?

    @GET("list.php?i=list")
    suspend fun getIngredients() : Ingredients?

    @GET("filter.php")
    suspend fun getByIngredient(@Query("i") ingredient: String) : Drinks?

    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") id: String) : Drinks?
}