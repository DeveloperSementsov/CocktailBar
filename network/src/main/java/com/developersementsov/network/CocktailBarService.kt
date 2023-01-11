package com.developersementsov.network

import com.developersementsov.data.entity.Drinks
import com.developersementsov.data.entity.Ingredients
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface CocktailBarService {

    @GET("random.php")
    suspend fun getRandomCocktail() : Drinks?

    @GET("list.php?i=list")
    suspend fun getIngredients() : Ingredients?

    @GET("filter.php")
    suspend fun getByIngredient(@Query("i") ingredient: String) : Drinks?
}