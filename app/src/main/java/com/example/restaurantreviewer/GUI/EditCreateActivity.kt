package com.example.restaurantreviewer.GUI

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.InputType
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doAfterTextChanged
import com.example.restaurantreviewer.Database.RestaurantRepository
import com.example.restaurantreviewer.Database.UserRepository
import com.example.restaurantreviewer.Model.Restaurant
import com.example.restaurantreviewer.Model.Review
import com.example.restaurantreviewer.Model.User
import com.example.restaurantreviewer.R
import kotlinx.android.synthetic.main.activity_editcreate.*
import java.io.File
import java.time.LocalDate
import java.util.*
import kotlin.NoSuchElementException

class EditCreateActivity : AppCompatActivity() {

    private lateinit var review: Review
    private lateinit var restRepo: RestaurantRepository
    private lateinit var userRepo: UserRepository
    private var pictureFile: File? = null
    private val REVIEW_INTENT = "review"
    private val ERROR_INTENT = "error"
    private val RESULT_UPDATED = 1
    private val RESULT_CREATED = 2
    private val RESULT_NOSAVE = 3
    private val RESULT_FAILED = 4
    private val PERMISSION_REQUEST_CODE = 5
    private val CAMERA_REQUEST_CODE = 6
    private val TAG = "EditCreateActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editcreate)
        setupRatingsSpinner()
        setupEditReviewText()
        if (intent.extras != null) {
            // remember to take room into account later
            restRepo = RestaurantRepository()
            userRepo = UserRepository()
            restRepo.addMockData() // delete later
            userRepo.addMockData() // delete later
            val extras: Bundle = intent.extras!!
            review = extras.getSerializable(REVIEW_INTENT) as Review
            try {
                val restaurant: Restaurant = restRepo.getRestaurantById(review.restaurantId)
                val user: User = userRepo.getUserById(review.userId)
                tvRestaurantInfo.text = getString(R.string.restaurantInfo, restaurant.name)
                tvReviewerInfo.text = getString(R.string.reviewerInfo, user.name)
            } catch (e: NoSuchElementException) {
                val errorMsg = "Restaurant or user not found"
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
                countCharacters(etReview.text.toString())
            }
        } else {
            val errorMsg = "Intent is empty"
            failure(errorMsg)
        }
    }

    fun onClickSaveReview(view: View) {
        review.review = etReview.getText().toString().trim()
        if (spRating.selectedItem == null) {
            Toast.makeText(this, "You must select a rating", Toast.LENGTH_LONG).show()
            return
        } else {
            review.rating = spRating.selectedItem.toString().toInt()
        }
        val intent = Intent()
        if (review.id == 0) {
            review.date = LocalDate.now()
            intent.putExtra(REVIEW_INTENT, review)
            setResult(RESULT_CREATED, intent)
        } else {
            intent.putExtra(REVIEW_INTENT, review)
            setResult(RESULT_UPDATED, intent)
        }
        Log.d(TAG, "Saving review:\nId: ${review.id}\nRating: ${review.rating}\nReview: ${review.review}\nUri: ${review.picture}")
        finish()
    }

    fun onClickGoBack(view: View) {
        setResult(RESULT_NOSAVE)
        finish()
    }

    private fun failure(errorMsg: String) {
        Log.d(TAG, errorMsg)
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

    private fun setupEditReviewText() {
        etReview.imeOptions = EditorInfo.IME_ACTION_DONE
        etReview.setRawInputType(InputType.TYPE_CLASS_TEXT)
        etReview.doAfterTextChanged { text -> countCharacters(text.toString()) }
    }

    private fun countCharacters(text: String) {
        tvWordCount.text = getString(R.string.tvWordCount, text.length)
        if (text.length == 150) {
            tvWordCount.setTextColor(Color.RED)
        } else if (text.length >= 140) {
            tvWordCount.setTextColor(getColor(R.color.dark_orange))
        } else {
            tvWordCount.setTextColor(Color.BLACK)
        }
    }

    fun onClickTakePicture(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions = mutableListOf<String>()
            if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (!hasPermission(Manifest.permission.CAMERA)) permissions.add(Manifest.permission.CAMERA)
            if (permissions.size > 0) {
                ActivityCompat.requestPermissions(this, permissions.toTypedArray(), PERMISSION_REQUEST_CODE)
            } else {
                takePicture()
            }
        } else {
            takePicture()
        }
    }

    private fun hasPermission(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun takePicture() {
        pictureFile = getOutputFile("RRCamera")
        if(pictureFile == null) {
            Toast.makeText(this, "Could not create file", Toast.LENGTH_LONG).show()
            return
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val applicationId = "com.example.restaurantreviewer"
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
            this, "${applicationId}.provider", pictureFile!!))
        try {
            startActivityForResult(intent, CAMERA_REQUEST_CODE)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Camera activity not found!!!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getOutputFile(folder: String): File? {
        val mediaStorageDir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), folder)
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Failed to create picture directory")
                return null
            }
        }
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val postfix = "jpg"
        val prefix = "IMG"
        return File(mediaStorageDir.path + File.separator + prefix + "_" + timeStamp + "." + postfix)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val pictureString = Uri.fromFile(pictureFile!!).toString()
                review.picture = pictureString
                setImageFromFileString(imgReview, pictureString)
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled taking picture", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onClickRemovePicture(view: View) {
        review.picture = null
        setImageFromFileString(imgReview, null)
    }

    private fun setImageFromFileString(img: ImageView, fileString: String?) {
        if (fileString != null) {
            var uri = Uri.parse(fileString)
            img.setImageURI(uri)
        } else {
            img.setImageResource(R.drawable.addimage)
        }
        img.adjustViewBounds = true
        img.scaleType = ImageView.ScaleType.CENTER_INSIDE
    }
}