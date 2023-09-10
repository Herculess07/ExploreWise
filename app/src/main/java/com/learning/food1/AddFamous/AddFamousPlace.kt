package com.learning.food1.AddFamous

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.learning.food1.R
import com.learning.food1.databinding.ActivityAddFamousPlaceBinding

class AddFamousPlace : AppCompatActivity() {

    private lateinit var bindingAddPlace: ActivityAddFamousPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAddPlace = ActivityAddFamousPlaceBinding.inflate(layoutInflater)
        setContentView(bindingAddPlace.root)



    }

    override fun onStart() {
        super.onStart()
    }


    private fun autoTextView(){

    }

}