package com.example.restaurantreviewer.Model

import java.io.Serializable

data class Restaurant (
    val id: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val openingHours: String,
    var avgRating: Double? = null,
        ): Serializable