package com.example.restaurantreviewer.Database

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.restaurantreviewer.Model.Review
import com.example.restaurantreviewer.Model.User
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class ReviewRepository private constructor(context: Context){

    private var reviewList = ArrayList<Review>()

    fun addMockData() {
        reviewList = arrayListOf(
            Review(id = 1, userId = 1, restaurantId = 1, review = "Good service", rating = 5, picture = null, date = LocalDate.now()),
            Review(id = 2, userId = 2, restaurantId = 2, review = "Very bad service. The food was very bad, and I am very disappointed!", rating = 1, picture = "", date = LocalDate.now()),
            Review(id = 3, userId = 3, restaurantId = 3, review = "Okay", rating = 3, picture = null, date = LocalDate.now()),
            Review(id = 4, userId = 4, restaurantId = 4, review = "I didn't like the food", rating = 2, picture = null, date = LocalDate.now()),
            Review(id = 5, userId = 4, restaurantId = 5, review = "It tasted great", rating = 4, picture = null, date = LocalDate.now()),
            Review(id = 6, userId = 2, restaurantId = 2, review = "Bad service, bad place, bad company. Ruined my mood!!!!!!", rating = 3, picture = null, date = LocalDate.now()),
            Review(id = 7, userId = 3, restaurantId = 2, review = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", rating = 3, picture = null, date = LocalDate.now())
        )
    }

    fun getAll(): ArrayList<Review> {
        return reviewList
    }

    fun getForOneRestaurant(id: Int): ArrayList<Review> {
        return reviewList.filter { r -> r.restaurantId == id } as ArrayList<Review>
    }

    companion object {
        private var Instance: ReviewRepository? = null

        fun initialize(context: Context) {
            if (Instance == null)
                Instance = ReviewRepository(context)
        }

        fun get(): ReviewRepository {
            if (Instance != null) return Instance!!
            throw IllegalStateException("Restaurant repo not initialized")
        }
    }

}