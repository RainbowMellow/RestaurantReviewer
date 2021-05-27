package com.example.restaurantreviewer.Model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDate

@Entity
data class Review (
    @PrimaryKey (autoGenerate = true) var id: Int?,
    var userId: Int?,
    var restaurantId: Int?,
    var review: String = "",
    var rating: Int,
    var picture: String? = null,
    var date: LocalDate? = null,
    @Ignore var user: User? = null
    ): Serializable {
        constructor(): this(null, null, null, "", 0, null, null, null)
    }