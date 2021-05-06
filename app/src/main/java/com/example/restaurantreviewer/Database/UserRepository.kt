package com.example.restaurantreviewer.Database

import android.content.Context
import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.Model.User

class UserRepository private constructor(context: Context) {

    private var userList = ArrayList<User>()

    fun addMockData() {

        userList = arrayListOf<User>(
            User(id = 1, name = "Benny"),
            User(id = 2, name = "Hans"),
            User(id = 3, name = "Anders"),
            User(id = 4, name = "Per"),
        )
    }

    fun getAll(): ArrayList<User> {
        return userList
    }

    fun getOne(id: Int): User {
        return userList.first { u -> u.id == id }
    }

    companion object {
        private var Instance: UserRepository? = null

        fun initialize(context: Context) {
            if (Instance == null)
                Instance = UserRepository(context)
        }

        fun get(): UserRepository {
            if (Instance != null) return Instance!!
            throw IllegalStateException("Restaurant repo not initialized")
        }
    }
}