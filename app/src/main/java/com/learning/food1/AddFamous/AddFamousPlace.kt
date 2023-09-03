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
        spinner()
    }

    private fun spinner() {
        val options = resources.getStringArray(R.array.cities)
        val spinner = bindingAddPlace.spSelectCityNamePlace

        if (spinner != null) {
            val adapter = ArrayAdapter(this, R.layout.spinner_layout, options)
            spinner.adapter = adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        }
    }

    private fun autoTextView(){

    }

}