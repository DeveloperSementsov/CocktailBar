package com.developersementsov.data.entity


import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("strIngredient1")
    val ingredient: String
)