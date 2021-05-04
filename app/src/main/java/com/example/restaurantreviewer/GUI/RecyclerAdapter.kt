package com.example.restaurantreviewer.GUI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.R

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.RestaurantHolder> {

    private val restaurants: ArrayList<Restaurant>
    private val inflater: LayoutInflater
    private val clickListener: IItemClickListener

    constructor(context: Context, restaurants: ArrayList<Restaurant>, clickListener: IItemClickListener) {
        this.inflater = LayoutInflater.from(context)
        this.restaurants = restaurants
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantHolder {
        val view: View = inflater.inflate(R.layout.cell_main, parent, false)
        return RestaurantHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.tvName.text = restaurant.name
        holder.tvAddress.text = restaurant.address
        holder.tvAvgRating.text = if (restaurant.avgRating != null) restaurant.avgRating.toString() else "No ratings yet"
        holder.view.setOnClickListener { _ -> clickListener.onRestaurantClick(restaurant, position) }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    class RestaurantHolder : RecyclerView.ViewHolder {

        var view: View
        var tvName: TextView
        var tvAddress: TextView
        var tvAvgRating: TextView

        constructor(v: View) : super(v) {
            view = v
            tvName = v.findViewById(R.id.tvNameMain)
            tvAddress = v.findViewById(R.id.tvAddressMain)
            tvAvgRating = v.findViewById(R.id.tvAvgRatingMain)
        }

    }

}