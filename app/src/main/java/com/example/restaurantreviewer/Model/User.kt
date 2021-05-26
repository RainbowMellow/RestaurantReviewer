package com.example.restaurantreviewer.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
        ): Serializable