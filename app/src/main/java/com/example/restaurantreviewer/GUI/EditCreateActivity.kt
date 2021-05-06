package com.example.restaurantreviewer.GUI

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import com.example.restaurantreviewer.Database.RestaurantRepository
import com.example.restaurantreviewer.Database.UserRepository
import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.Model.Review
import com.example.restaurantreviewer.Model.User
import com.example.restaurantreviewer.R
import kotlinx.android.synthetic.main.activity_editcreate.*
import java.io.File

class EditCreateActivity : AppCompatActivity() {

    private lateinit var review: Review
    private lateinit var restRepo: RestaurantRepository
    private lateinit var userRepo: UserRepository
    private val pictureFile: File? = null
    private val REVIEW_INTENT = "review"
    private val ERROR_INTENT = "error"
    private val RESULT_UPDATED = 1
    private val RESULT_CREATED = 2
    private val RESULT_CANCELLED = 3
    private val RESULT_FAILED = 4
    private val TAG = "EditCreateActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editcreate)
        setupRatingsSpinner()
        if (intent.extras != null) {
            // remember to take room into account later
            restRepo = RestaurantRepository()
            userRepo = UserRepository()
            val extras: Bundle = intent.extras!!
            review = extras.getSerializable(REVIEW_INTENT) as Review
            try {
                val restaurant: Restaurant = restRepo.getRestaurantById(review.restaurantId)
                val user: User = userRepo.getUserById(review.userId)
                tvRestaurantInfo.text = getString(R.string.restaurantInfo, restaurant.name)
                tvReviewerInfo.text = getString(R.string.reviewerInfo, user.name)
            } catch (e: NoSuchElementException) {
                val errorMsg = "Restaurant or user not found"
                Log.d(TAG, errorMsg)
                failure(errorMsg)
            }
            // if new review (id has not been assigned yet)
            if (review.id == 0) {
                // put stuff here if necessary
            }
            // if old review (id is different from 0 as it has been assigned already)
            else {
                setImageFromFileString(imgReview, review.picture)
                spRating.setSelection(review.rating-1)
                if (review.review.isBlank()) {
                    etReview.setHint("No detailed review written yet...")
                } else {
                    etReview.setText(review.review)
                }
            }
        } else {
            val errorMsg = "Intent is empty"
            Log.d(TAG, errorMsg)
            failure(errorMsg)
        }
    }

    fun onClickSaveReview(view: View) {
        review.review = etReview.getText().toString().trim()
        if (spRating.selectedItem == null) {
            Toast.makeText(this, "You must select a rating", Toast.LENGTH_LONG).show()
            return
        } else {
            review.rating = spRating.selectedItem as Int
        }
        val intent = Intent()
        intent.putExtra(REVIEW_INTENT, review)
        if (review.id == 0) {
            setResult(RESULT_CREATED, intent)
        } else {
            setResult(RESULT_UPDATED, intent)
        }
        Log.d(TAG, "Saving review: Id: ${review.id}, Rating: ${review.rating}, Review: ${review.review}, Uri: ${review.picture}")
        finish()
    }

    fun onClickGoBack(view: View) {
        setResult(RESULT_CANCELLED)
        finish()
    }

    private fun failure(errorMsg: String) {
        val intent = Intent()
        intent.putExtra(ERROR_INTENT, errorMsg)
        setResult(RESULT_FAILED)
        finish()
    }

    private fun setupRatingsSpinner() {
        spRating.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.ratings,
            android.R.layout.simple_spinner_dropdown_item
        )
    }

    fun onClickTakePicture(view: View) {}

    fun onClickRemovePicture(view: View) {}

    private fun setImageFromFileString(img: ImageView, fileString: String?) {
        if (fileString != null) {
            var uri = Uri.parse(fileString)
            img.setImageURI(uri)
            img.adjustViewBounds = true
            img.scaleType = ImageView.ScaleType.CENTER_INSIDE
        }
    }
}