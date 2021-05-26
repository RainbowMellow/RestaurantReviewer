package com.example.restaurantreviewer.Model

import androidx.room.PrimaryKey
import java.io.Serializable

data class User (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
        ): Serializable