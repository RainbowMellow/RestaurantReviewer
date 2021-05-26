package com.example.restaurantreviewer.Database.Room

import android.content.Context
import androidx.room.Room
import java.util.concurrent.Executors

private const val DATABASE_NAME = "restaurant-database"

class RestaurantRepository private constructor (context: Context){

    private val database: RestaurantDatabase = Room.databaseBuilder(
        context.applicationContext,
        RestaurantDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    private val restaurantDao = database.restaurantDao()
    private val reviewDao = database.reviewDao()
    private val userDao = database.userDao()
    private val executor = Executors.newSingleThreadExecutor()

    companion object {
        private var INSTANCE: RestaurantRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = RestaurantRepository(context)
            }
        }

        fun get(): RestaurantRepository {
            return INSTANCE ?: throw IllegalStateException("Repository must be initialized")
        }
    }

}