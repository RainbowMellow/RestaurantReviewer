package com.example.restaurantreviewer.Database.Room

import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.Model.Review
import com.example.restaurantreviewer.Model.User
import java.time.LocalDate

class DatabaseSeeder {

    fun seed() {
        val repo = RestaurantRepository.get()
        val restaurantList = arrayListOf<Restaurant>(
            Restaurant(id = 1, name = "Sunset", address = "Torvet", latitude = 1.0, longitude = 2.0, openingHours = "Monday"),
            Restaurant(id = 2, name = "McDonalds", address = "Torvet", latitude = 3.0, longitude = 4.0, openingHours = "All the time"),
            Restaurant(id = 3, name = "den Niende", address = "Torvet", latitude = 4.0, longitude = 2.0, openingHours = "Never"),
            Restaurant(id = 4, name = "Nara Sushi", address = "Byen", latitude = 5.0, longitude = 1.5, openingHours = "Wednesday"),
            Restaurant(id = 5, name = "Jensens Bøfhus", address = "Ved Bilka", latitude = 18.0, longitude = 2.7, openingHours = "Tuesday")
        )
        val userList = arrayListOf<User>(
            User(id = 1, name = "Benny"),
            User(id = 2, name = "Hans"),
            User(id = 3, name = "Anders"),
            User(id = 4, name = "Per"),
        )
        val reviewList = arrayListOf(
            Review(id = 1, userId = 1, restaurantId = 1, review = "Good service", rating = 5, picture = null, date = LocalDate.now()),
            Review(id = 2, userId = 2, restaurantId = 2, review = "Very bad service. The food was very bad, and I am very disappointed!", rating = 1, picture = "", date = LocalDate.now()),
            Review(id = 3, userId = 3, restaurantId = 3, review = "Okay", rating = 3, picture = null, date = LocalDate.now()),
            Review(id = 4, userId = 4, restaurantId = 4, review = "I didn't like the food", rating = 2, picture = null, date = LocalDate.now()),
            Review(id = 5, userId = 4, restaurantId = 5, review = "It tasted great", rating = 4, picture = null, date = LocalDate.now()),
            Review(id = 6, userId = 2, restaurantId = 2, review = "Bad service, bad place, bad company. Ruined my mood!!!!!!", rating = 3, picture = null, date = LocalDate.now()),
            Review(id = 7, userId = 3, restaurantId = 2, review = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", rating = 3, picture = null, date = LocalDate.now())
        )
        restaurantList.forEach { restaurant -> repo.insertRestaurant(restaurant) }
        userList.forEach { user -> repo.insertUser(user) }
        reviewList.forEach { review -> repo.insertReview(review) }
    }

    fun clean() {
        val repo = RestaurantRepository.get()
        repo.deleteAllReviews()
        repo.deleteAllUsers()
        repo.deleteAllRestaurants()
    }

}