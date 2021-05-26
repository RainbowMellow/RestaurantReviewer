package com.example.restaurantreviewer.GUI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreviewer.Database.RestaurantRepository
import com.example.restaurantreviewer.Database.ReviewRepository
import com.example.restaurantreviewer.Database.UserRepository
import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.Model.Review
import com.example.restaurantreviewer.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

private lateinit var restRepo: RestaurantRepository
private lateinit var revRepo: ReviewRepository
private lateinit var userRepo: UserRepository
private var restaurants: ArrayList<Restaurant> = ArrayList()
private val TAG = "MainActivity"
private val RESTAURANTS_DATA = "restaurants" // turnsafety


class MainActivity : AppCompatActivity(), IItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // testing this
        RestaurantRepository.initialize(this)
        ReviewRepository.initialize(this)
        UserRepository.initialize(this)
        restRepo = RestaurantRepository.get()
        revRepo = ReviewRepository.get()
        userRepo = UserRepository.get()
        revRepo.addMockData()
        restRepo.addMockData()
        userRepo.addMockData()
        if (savedInstanceState != null) {
            restaurants = savedInstanceState.getSerializable(RESTAURANTS_DATA) as ArrayList<Restaurant>
        } else {
            restaurants = restRepo.getAll()
            restaurants.forEach { restaurant -> calculateAverageRating(restaurant) }
        }
        rvMain.layoutManager = LinearLayoutManager(this)

        // Adding the lines in between the rows
        rvMain.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        updateList()
    }

    fun updateList() {
        rvMain.adapter = RecyclerAdapter(this, restaurants, this)
    }

    fun calculateAverageRating(restaurant: Restaurant) {
        var totalScore: Double = 0.0
        var reviews: Int = 0
        revRepo.getAll().forEach { review ->
            if (review.restaurantId == restaurant.id) {
                totalScore += review.rating
                reviews++
            }
        }
        if (reviews > 0) {
            restaurant.avgRating = Math.round((totalScore / reviews)).toInt()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_map -> {
                openMap()
                true
            }
            R.id.filter_reset -> {
                restaurants = restRepo.getAll()
                restaurants.forEach { restaurant -> calculateAverageRating(restaurant) }
                updateList()
                true
            }
            R.id.filter_byname_asc -> {
                restaurants.sortBy { it.name }
                updateList()
                true
            }
            R.id.filter_byname_desc -> {
                restaurants.sortByDescending { it.name }
                updateList()
                true
            }
            R.id.filter_byrating_asc -> {
                restaurants.sortBy { it.avgRating }
                updateList()
                true
            }
            R.id.filter_byrating_desc -> {
                restaurants.sortByDescending { it.avgRating }
                updateList()
                true
            }
            R.id.filter_minRating_2 -> {
                restaurants = restRepo.getAll()
                restaurants.forEach { restaurant -> calculateAverageRating(restaurant) }
                restaurants.removeAll { restaurant -> restaurant.avgRating == null || restaurant.avgRating!! < 2.0  }
                updateList()
                true
            }
            R.id.filter_minRating_3 -> {
                restaurants = restRepo.getAll()
                restaurants.forEach { restaurant -> calculateAverageRating(restaurant) }
                restaurants.removeAll { restaurant -> restaurant.avgRating == null || restaurant.avgRating!! < 3.0  }
                updateList()
                true
            }
            R.id.filter_minRating_4 -> {
                restaurants = restRepo.getAll()
                restaurants.forEach { restaurant -> calculateAverageRating(restaurant) }
                restaurants.removeAll { restaurant -> restaurant.avgRating == null || restaurant.avgRating!! < 4.0  }
                updateList()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun filterRestaurants() {

    }

    override fun onRestaurantClick(restaurant: Restaurant, position: Int) {
        val intent = Intent(this, RestaurantActivity::class.java) // needs specific class
        intent.putExtra(getString(R.string.RESTAURANT_DETAILS_INTENT), restaurant)
        startActivity(intent) // maybe for result - depends on if reviews are saved on detail view
    }

    fun openMap() {
        intent = Intent(this, MapsActivity::class.java) //needs specific class
        intent.putExtra(getString(R.string.ALL_RESTAURANTS_INTENT), restaurants) // is typed array necessary?
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(RESTAURANTS_DATA, restaurants)
    }



}