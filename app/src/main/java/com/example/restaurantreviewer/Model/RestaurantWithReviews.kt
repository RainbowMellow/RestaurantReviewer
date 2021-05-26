package com.example.restaurantreviewer.Model

import androidx.room.Embedded
import androidx.room.Relation

data class RestaurantWithReviews(
    @Embedded val restaurant: Restaurant,
    @Relation(
        parentColumn = "id",
        entityColumn = "restaurantId",
    )
    val reviews: List<ReviewWithRestaurantAndUser>
)