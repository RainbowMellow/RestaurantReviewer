package com.example.restaurantreviewer.Database.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.restaurantreviewer.Model.Filter
import com.example.restaurantreviewer.Model.Restaurant

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM Restaurant")
    fun getAllRestaurants(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM Restaurant WHERE id=(:id)")
    fun getRestaurantById(id: Int): LiveData<Restaurant>

    @Query("SELECT COUNT(id) FROM Restaurant")
    fun getNumberOfRestaurants(): LiveData<Int>

    @Query("""SELECT * FROM Restaurant 
        WHERE avgRating >= (:avg) AND avgRating NOT NULL 
        ORDER BY
        CASE WHEN :asc = 1 THEN :orderBy END ASC,
        CASE WHEN :asc = 0 THEN :orderBy END DESC
    """)
    fun getFilteredRestaurants(orderBy: String, asc: Boolean, avg: Double): LiveData<List<Restaurant>>

    @Insert
    fun insertRestaurant(restaurant: Restaurant)

    @Query("DELETE FROM Restaurant")
    fun deleteAllRestaurants()
}