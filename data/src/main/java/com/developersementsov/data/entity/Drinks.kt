package com.developersementsov.data.entity


import com.google.gson.annotations.SerializedName

data class Drinks(
    @SerializedName("drinks")
    val drinks: List<Drink>
)