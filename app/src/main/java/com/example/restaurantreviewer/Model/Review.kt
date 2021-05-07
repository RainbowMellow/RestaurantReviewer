package com.example.restaurantreviewer.Model

import java.io.Serializable
import java.time.LocalDate

data class Review (
    var id: Int,
    var userId: Int,
    var restaurantId: Int,
    var review: String = "",
    var rating: Int,
    var picture: String? = null,
    var date: LocalDate? = null
        ): Serializable