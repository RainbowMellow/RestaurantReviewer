package com.example.restaurantreviewer.Database

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.restaurantreviewer.Model.Review
import com.example.restaurantreviewer.Model.User
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class ReviewRepository {

    private var reviewList = ArrayList<Review>()

    fun addMockData() {
        reviewList = arrayListOf(
            Review(id = 1, userId = 1, restaurantId = 1, review = "Good service", rating = 5, picture = null, date = LocalDate.now()),
            Review(id = 2, userId = 2, restaurantId = 2, review = "Bad service", rating = 1, picture = null, date = LocalDate.now()),
            Review(id = 3, userId = 3, restaurantId = 3, review = "Okay", rating = 3, picture = null, date = LocalDate.now()),
            Review(id = 4, userId = 4, restaurantId = 4, review = "I didn't like the food", rating = 2, picture = null, date = LocalDate.now()),
            Review(id = 5, userId = 4, restaurantId = 5, review = "It tasted great", rating = 4, picture = null, date = LocalDate.now())
        )
    }

    fun getAll(): ArrayList<Review> {
        return reviewList
    }

}