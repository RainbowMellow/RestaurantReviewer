package com.example.restaurantreviewer.GUI

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantreviewer.Database.ReviewRepository
import com.example.restaurantreviewer.Database.UserRepository
import com.example.restaurantreviewer.Model.RecycleAdapter
import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.Model.Review
import com.example.restaurantreviewer.R
import kotlinx.android.synthetic.main.activity_restaurant.*
import kotlinx.android.synthetic.main.pop_up.*
import java.time.format.DateTimeFormatter


class RestaurantActivity: AppCompatActivity() {

    lateinit var reviewRepo: ReviewRepository

    lateinit var restaurantAdapter: RecycleAdapter

    lateinit var userRepository: UserRepository

    var chosenRestaurant = Restaurant(0, "", "", 0.0, 0.0, "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        ReviewRepository.initialize(this)
        reviewRepo = ReviewRepository.get()
        reviewRepo.addMockData()

        UserRepository.initialize(this)
        userRepository = UserRepository.get()
        userRepository.addMockData()

        if (intent.extras != null) {
            val extras: Bundle = intent.extras!!
            val restaurant = extras.getSerializable("restaurant") as Restaurant

            chosenRestaurant = restaurant

            tvName.text = chosenRestaurant.name

            addStars()

            tvAddress.text = chosenRestaurant.address
            tvOpeningHours.text = chosenRestaurant.openingHours
        }

        handleRecycler()
    }

    fun handleRecycler()
    {
        // Find the RecyclerView and make a reference to it
        val recycler = findViewById<RecyclerView>(R.id.recyclerView)

        // Setting the recyclers layoutmanager to be a LinearLayoutManager
        recycler.layoutManager = LinearLayoutManager(this)

        // Adding the lines in between the rows
        recycler.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        // Sets the items in the recycler to have a fixed size
        recycler.setHasFixedSize(true)

        // Gets the list of friends from the repository
        // and converts it to a list the recycler can use.
        val reviews = reviewRepo.getForOneRestaurant(chosenRestaurant.id)

        restaurantAdapter = RecycleAdapter(reviews)
        recycler.adapter = restaurantAdapter

        restaurantAdapter.itemClickListener = { position, review ->

            val user = userRepository.getOne(review.userId)

            val inflater = layoutInflater
            val dialoglayout: View = inflater.inflate(R.layout.pop_up, null)


            val name = dialoglayout.findViewById(R.id.tvPopUpName) as TextView
            name.text = user.name

            val date = dialoglayout.findViewById(R.id.tvPopUpDate) as TextView
            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            date.text = review.date!!.format(formatter)

            addPopUpStars(review, dialoglayout)

            val reviewText = dialoglayout.findViewById(R.id.tvPopUpReview) as TextView
            reviewText.text = review.review

            if(review.picture != null)
            {
                val picture = dialoglayout.findViewById(R.id.ivPopUpPicture) as ImageView
                picture.setImageURI(Uri.parse(review.picture))
            }


            val builder = AlertDialog.Builder(this)

            builder.setView(dialoglayout)

            val alertDialog: AlertDialog = builder.create()

            alertDialog.show()
        }
    }


    fun addStars() {
        val reviewList = reviewRepo.getForOneRestaurant(chosenRestaurant.id)

        var counter = 0

        reviewList.forEach{ r ->
            counter += r.rating
        }

        val stars = Math.round((counter / reviewList.count()).toDouble())

        for (i in 1..stars) {
            val imgView = ImageView(this)

            imgView.setBackgroundResource(R.drawable.full_star)

            llStars.addView(imgView)
        }

        val emptyStars = 5-stars

        for (i in 1..emptyStars) {
            val imgView = ImageView(this)

            imgView.setBackgroundResource(R.drawable.empty_star)

            llStars.addView(imgView)
        }
    }

    fun addPopUpStars(review: Review, layout: View) {
        for (i in 1..review.rating) {
            val imgView = ImageView(this)

            imgView.setBackgroundResource(R.drawable.small_full_star)

            val stars = layout.findViewById(R.id.llPopUpReviewStars) as LinearLayout?
            stars!!.addView(imgView)
        }

        val emptyStars = 5-review.rating

        for (i in 1..emptyStars) {
            val imgView = ImageView(this)

            imgView.setBackgroundResource(R.drawable.small_empty_star)

            val stars = layout.findViewById(R.id.llPopUpReviewStars) as LinearLayout?
            stars!!.addView(imgView)
        }
    }



    fun onClickBack(view: View) { finish() }
    fun onClickLocation(view: View) {}

}