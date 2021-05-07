package com.example.restaurantreviewer.Database

import android.content.Context
import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.Model.User

class RestaurantRepository private constructor(context: Context){

    private var restaurantList = ArrayList<Restaurant>()

    fun addMockData() {

        restaurantList = arrayListOf<Restaurant>(Restaurant(id = 1, name = "Sunset", address = "Torvet", latitude = 1.0, longitude = 2.0, openingHours = "Monday"),
        Restaurant(id = 2, name = "McDonalds", address = "Torvet", latitude = 3.0, longitude = 4.0, openingHours = "All the time"),
        Restaurant(id = 3, name = "den Niende", address = "Torvet", latitude = 4.0, longitude = 2.0, openingHours = "Never"),
        Restaurant(id = 4, name = "Nara Sushi", address = "Byen", latitude = 5.0, longitude = 1.5, openingHours = "Wednesday"),
        Restaurant(id = 5, name = "Jensens Bøfhus", address = "Ved Bilka", latitude = 18.0, longitude = 2.7, openingHours = "Tuesday"))
    }

    fun getAll(clone: Boolean = true): ArrayList<Restaurant> {
        if (!clone) {
            return restaurantList
        } else {
            var copyList: ArrayList<Restaurant> = ArrayList()
            copyList.addAll(restaurantList)
            return copyList
        }
    }

    fun getRestaurantById(id: Int): Restaurant {
        return restaurantList.first { restaurant -> restaurant.id == id }
    }

    companion object {
        private var Instance: RestaurantRepository? = null

        fun initialize(context: Context) {
            if (Instance == null)
                Instance = RestaurantRepository(context)
        }

        fun get(): RestaurantRepository {
            if (Instance != null) return Instance!!
            throw IllegalStateException("Restaurant repo not initialized")
        }
    }

}