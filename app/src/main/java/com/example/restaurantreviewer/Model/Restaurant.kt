package com.example.restaurantreviewer.Model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Restaurant (
    @PrimaryKey (autoGenerate = true) var id: Int,
    var name: String,
    var address: String,
    var latitude: Double,
    var longitude: Double,
    var openingHours: String,
    var avgRating: Double? = null,
    @Ignore var reviews: List<Review>? = null
        ): Serializable {
            constructor(): this(0, "", "", 0.0, 0.0, "", null, null)
        }