package com.developersementsov.data.entity


import com.google.gson.annotations.SerializedName

data class Ingredients(
    @SerializedName("drinks")
    val ingredients: List<Ingredient>
)