package com.learning.food1.AddFamous

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.learning.food1.R
import com.learning.food1.databinding.ActivityAddDevotionalPlaceBinding
import com.learning.food1.databinding.ActivityAddFamousFoodBinding

class AddFamousFood : AppCompatActivity() {

    private lateinit var bindingAddFood: ActivityAddFamousFoodBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAddFood = ActivityAddFamousFoodBinding.inflate(layoutInflater)
        setContentView(bindingAddFood.root)

        // spinner for cities
        // access spinner
        val options = resources.getStringArray(R.array.cities)
        val spinner = bindingAddFood.spSelectCityNameFood
        // Start Spinner
        if (spinner != null) {
            val adapter = ArrayAdapter(this, R.layout.spinner_layout, options)
            spinner.adapter = adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        }

    }

}