package com.example.restaurantreviewer.Database.Room

import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.Model.Review
import com.example.restaurantreviewer.Model.User
import java.time.LocalDate

class DatabaseSeeder() {

    fun seed() {
        println("---starting to seed")
        val repo = RestaurantRepository.get()
        val restaurantList = arrayListOf<Restaurant>(
            Restaurant(id = null, name = "Sunset", address = "Torvet", latitude = 1.0, longitude = 2.0, openingHours = "Monday"),
            Restaurant(id = null, name = "McDonalds", address = "Torvet", latitude = 3.0, longitude = 4.0, openingHours = "All the time"),
            Restaurant(id = null, name = "den Niende", address = "Torvet", latitude = 4.0, longitude = 2.0, openingHours = "Never"),
            Restaurant(id = null, name = "Nara Sushi", address = "Byen", latitude = 5.0, longitude = 1.5, openingHours = "Wednesday"),
            Restaurant(id = null, name = "Jensens Bøfhus", address = "Ved Bilka", latitude = 18.0, longitude = 2.7, openingHours = "Tuesday"),
            Restaurant(id = null, name = "Burger King", address = "Broen", latitude = 6.0, longitude = 6.0, openingHours = "Every day 07-23")
        )
        val userList = arrayListOf<User>(
            User(id = null, name = "Benny"),
            User(id = null, name = "Hans"),
            User(id = null, name = "Anders"),
            User(id = null, name = "Per"),
        )
        val reviewList = arrayListOf<Review>(
            Review(id = null, userId = 1, restaurantId = 1, review = "Good service", rating = 5, picture = null, date = LocalDate.now()),
            Review(id = null, userId = 2, restaurantId = 2, review = "Very bad service. The food was very bad, and I am very disappointed!", rating = 1, picture = "", date = LocalDate.now()),
            Review(id = null, userId = 3, restaurantId = 3, review = "Okay", rating = 3, picture = null, date = LocalDate.now()),
            Review(id = null, userId = 4, restaurantId = 4, review = "I didn't like the food", rating = 2, picture = null, date = LocalDate.now()),
            Review(id = null, userId = 4, restaurantId = 5, review = "It tasted great", rating = 4, picture = null, date = LocalDate.now()),
            Review(id = null, userId = 2, restaurantId = 2, review = "Bad service, bad place, bad company. Ruined my mood!!!!!!", rating = 1, picture = null, date = LocalDate.now()),
            Review(id = null, userId = 3, restaurantId = 2, review = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", rating = 2, picture = null, date = LocalDate.now())
        )
        restaurantList.forEach { restaurant -> repo.insertRestaurant(restaurant) }
        userList.forEach { user -> repo.insertUser(user) }
        reviewList.forEach { review -> repo.insertReview(review) }
        println("---finished seeding!")
    }

    fun clean() {
        val repo = RestaurantRepository.get()
        repo.deleteAllReviews()
        repo.deleteAllUsers()
        repo.deleteAllRestaurants()
    }

}