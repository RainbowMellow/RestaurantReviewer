package com.example.restaurantreviewer.Model

import androidx.room.Embedded
import androidx.room.Relation

data class ReviewWithRestaurantAndUser(
    @Embedded val review: Review,
    @Relation(
        parentColumn = "restaurantId",
        entityColumn = "id",
    )
    val restaurant: Restaurant,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id",
    )
    val user: User
)