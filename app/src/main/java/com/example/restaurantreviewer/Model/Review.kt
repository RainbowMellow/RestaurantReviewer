package com.example.restaurantreviewer.Model

import java.io.Serializable
import java.time.LocalDate

data class Review (
    val id: Int,
    val userId: Int,
    val restaurantId: Int,
    val review: String,
    val rating: Int,
    val picture: String?,
    val date: LocalDate?
        ): Serializable