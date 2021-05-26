package com.example.restaurantreviewer.Database.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.restaurantreviewer.Model.Restaurant

@Dao
interface RestaurantDao {

    @Transaction
    @Query("SELECT * FROM Restaurant")
    fun getAllRestaurants(): LiveData<List<Restaurant>>

    @Transaction
    @Query("SELECT * FROM Restaurant WHERE id=(:id)")
    fun getRestaurantById(id: Int): LiveData<Restaurant>

    @Query("SELECT COUNT(id) FROM Restaurant")
    fun getNumberOfRestaurants(): LiveData<Int>

    @Insert
    fun insertRestaurant(restaurant: Restaurant)

    @Query("DELETE FROM Restaurant")
    fun deleteAllRestaurants()
}