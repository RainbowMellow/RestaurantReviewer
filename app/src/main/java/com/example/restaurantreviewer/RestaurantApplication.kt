package com.example.restaurantreviewer

import android.app.Application
import com.example.restaurantreviewer.Database.Room.DatabaseSeeder
import com.example.restaurantreviewer.Database.Room.RestaurantRepository

class RestaurantApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RestaurantRepository.initialize(this)
        val dbSeeder = DatabaseSeeder()
        dbSeeder.seed()
    }

}