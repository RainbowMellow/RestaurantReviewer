package com.example.restaurantreviewer.Database

import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.Model.User

class UserRepository {

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
}