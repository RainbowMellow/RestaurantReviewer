package com.example.restaurantreviewer.GUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.restaurantreviewer.Database.RestaurantRepository
import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.R

class MainActivity : AppCompatActivity() {

    lateinit var restaurantRepo: RestaurantRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RestaurantRepository.initialize(this)
        restaurantRepo = RestaurantRepository.get()

        restaurantRepo.addMockData()
    }

    fun onClickButton(view: View) {
        val intent = Intent(this, RestaurantActivity::class.java)

        val restaurant = restaurantRepo.getAll()[1]

        intent.putExtra("restaurant", restaurant)

        startActivity(intent)
    }
}