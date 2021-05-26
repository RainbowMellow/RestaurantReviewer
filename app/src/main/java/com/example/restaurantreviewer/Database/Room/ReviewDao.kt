package com.example.restaurantreviewer.Database.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.restaurantreviewer.Model.Review
import com.example.restaurantreviewer.Model.ReviewWithRestaurantAndUser

@Dao
interface ReviewDao {

    @Transaction
    @Query("SELECT * FROM Review WHERE id=(:id)")
    fun getReviewById(id: Int): LiveData<ReviewWithRestaurantAndUser>

    @Transaction
    @Query("SELECT * FROM Review WHERE restaurantId=(:id)")
    fun getAllRestaurantReviews(id: Int): LiveData<List<ReviewWithRestaurantAndUser>>

    @Insert
    fun insertReview(review: Review)

    @Update
    fun updateReview(review: Review)

    @Delete
    fun deleteReview(review: Review)

    @Query("DELETE FROM Review")
    fun deleteAllReviews()
}