package com.example.restaurantreviewer.Database.Room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.restaurantreviewer.Model.Review

@Dao
interface ReviewDao {

    @Query("SELECT * FROM Review WHERE id=(:id)")
    fun getReviewById(id: Int): LiveData<Review>

    @Query("SELECT * FROM Review WHERE restaurantId=(:id)")
    fun getAllRestaurantReviews(id: Int): LiveData<List<Review>>

    @Insert
    fun insertReview(review: Review)

    @Update
    fun updateReview(review: Review)

    @Delete
    fun deleteReview(review: Review)

    @Query("DELETE FROM Review")
    fun deleteAllReviews()
}