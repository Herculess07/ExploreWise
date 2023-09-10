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


    }

}