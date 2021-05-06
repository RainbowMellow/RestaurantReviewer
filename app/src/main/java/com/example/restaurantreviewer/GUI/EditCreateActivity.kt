package com.example.restaurantreviewer.GUI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.restaurantreviewer.R

class EditCreateActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editcreate)
    }




    fun onClickTakePicture(view: View) {}
    fun onClickRemovePicture(view: View) {}
    fun onClickGoBack(view: View) {}
    fun onClickSaveReview(view: View) {}
}