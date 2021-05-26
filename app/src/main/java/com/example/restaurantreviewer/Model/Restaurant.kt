package com.example.restaurantreviewer.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Restaurant (
    @PrimaryKey (autoGenerate = true) val id: Int,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val openingHours: String,
    var avgRating: Double? = null,
        ): Serializable