package com.example.restaurantreviewer.GUI

import com.example.restaurantreviewer.Model.Restaurant

interface IItemClickListener {
    fun onRestaurantClick(restaurant: Restaurant, position: Int)
}