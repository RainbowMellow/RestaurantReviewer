package com.example.restaurantreviewer.Model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantreviewer.Database.InMemory.UserRepository
import com.example.restaurantreviewer.R
import java.time.format.DateTimeFormatter

class RecycleAdapter(private val reviews: ArrayList<Review>) : RecyclerView.Adapter<RecycleAdapter.ReviewViewHolder>() {

    var reviewList = reviews

    lateinit var context: Context

    lateinit var userRepository: UserRepository

    var itemClickListener: ((position: Int, review: Review) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_cell, parent, false)

        UserRepository.initialize(parent.context)
        userRepository = UserRepository.get()
        userRepository.addMockData()

        context = parent.context

        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {

        val element = reviewList[position]

        val user = userRepository.getUserById(element.userId)

        holder.nameTxt.text = user.name

        println(user?.name)

        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        holder.dateTxt.text = element.date!!.format(formatter)

        addStars(holder.reviewStars, context, element)

        val text = element.review.toCharArray()
        val charsToShow = String(text.take(30).toCharArray()) + "..."

        holder.reviewTxt.text = charsToShow

        if(element.picture != null)
        {
            holder.reviewImage.setImageResource(R.drawable.image)
        }

        holder.itemView.setOnClickListener {
            // Invoking itemClickListener and passing it the position and friend
            itemClickListener?.invoke(position, element)
        }
    }

    fun addStars(reviewStars: LinearLayout, context: Context, review: Review) {
        for (i in 1..review.rating) {
            val imgView = ImageView(context)

            imgView.setBackgroundResource(R.drawable.small_full_star)

            reviewStars.addView(imgView)
        }

        val emptyStars = 5-review.rating

        for (i in 1..emptyStars) {
            val imgView = ImageView(context)

            imgView.setBackgroundResource(R.drawable.small_empty_star)

            reviewStars.addView(imgView)
        }
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTxt = itemView.findViewById(R.id.tvName) as TextView
        val dateTxt = itemView.findViewById(R.id.tvDate) as TextView
        val reviewTxt = itemView.findViewById(R.id.tvReview) as TextView
        val reviewStars = itemView.findViewById(R.id.llReviewStars) as LinearLayout
        val reviewImage = itemView.findViewById(R.id.ivPicture) as ImageView
    }

}