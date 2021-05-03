package com.example.restaurantreviewer.GUI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreviewer.Database.RestaurantRepository
import com.example.restaurantreviewer.Database.ReviewRepository
import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.R
import kotlinx.android.synthetic.main.activity_main.*

private lateinit var restRepo: RestaurantRepository
private lateinit var revRepo: ReviewRepository
private val TAG = "MainActivity"


class MainActivity : AppCompatActivity(), IItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        restRepo = RestaurantRepository()
        revRepo = ReviewRepository()
        revRepo.addMockData()
        restRepo.addMockData()
        val restaurants = restRepo.getAll()
        restaurants.forEach { restaurant -> calculateAverageRating(restaurant) }
        rvMain.layoutManager = LinearLayoutManager(this)
        rvMain.adapter = RecyclerAdapter(this, restaurants, this)
    }

    fun calculateAverageRating(restaurant: Restaurant) {
        var totalScore: Double = 0.0
        var reviews: Int = 0
        revRepo.getAll().forEach { review ->
            if (review.restaurantId == restaurant.id) {
                totalScore += review.rating
                reviews += 1
            }
        }
        restaurant.avgRating = totalScore / reviews
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_map -> {
                Toast.makeText(this, "Will eventually open map...", Toast.LENGTH_SHORT).show()
                // insert code for starting map activity
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRestaurantClick(restaurant: Restaurant, position: Int) {
        Toast.makeText(this, "Clicked on ${restaurant.name}", Toast.LENGTH_SHORT).show()
        // insert code for starting detail activity
    }
}