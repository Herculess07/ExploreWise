package com.learning.food1.Main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.learning.food1.Configs
import com.learning.food1.Model.details.DetailsDevotional
import com.learning.food1.Model.details.DetailsFoods
import com.learning.food1.Model.details.DetailsPlaces
import com.learning.food1.R
import com.learning.food1.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var m: ActivityDetailsBinding
    lateinit var fdb: DatabaseReference

    lateinit var placeData: DetailsPlaces
    lateinit var foodData: DetailsFoods
    lateinit var devotionalData: DetailsDevotional

    lateinit var devotionId: String
    lateinit var placeId: String
    lateinit var foodId: String
    private val config = Configs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(m.root)
        init()
    }

    private fun init() {
        getDevPlace()
        getPlace()
        getFood()
    }

    // Devotional Places
    private fun getDevPlace() {
        fdb = FirebaseDatabase.getInstance().reference
        devotionId = intent.getStringExtra(config.DEVOTION_ID).toString()

        val imageUrl = "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FDevotionalPlaces%2F$devotionId?alt=media"

        val dbDevotion = fdb.child("Users/DevotionalPlaces").child(devotionId)
        dbDevotion.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    devotionalData = snapshot.getValue(DetailsDevotional::class.java)!!
                    m.titleDetails.text = devotionalData.devotional_name
                    m.descriptionDetails.text = devotionalData.devotional_about

                    Glide.with(this@DetailsActivity)
                        .load(imageUrl)
                        .placeholder(R.drawable.skeleton)
                        .into(m.imageDetails)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    // Famous Places
    private fun getPlace() {
        fdb = FirebaseDatabase.getInstance().reference
        placeId = intent.getStringExtra(config.PLACE_ID).toString()

        val imageUrl = "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FFamousPlaces%2F$placeId?alt=media"

        val dbPlace = fdb.child("Users/FamousPlaces").child(placeId)
        dbPlace.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    placeData = snapshot.getValue(DetailsPlaces::class.java)!!
                    m.titleDetails.text = placeData.place_name
                    m.descriptionDetails.text = placeData.place_about


                    Glide.with(this@DetailsActivity)
                        .load(imageUrl)
                        .placeholder(com.learning.food1.R.drawable.skeleton)
                        .into(m.imageDetails)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


    // Famous Food
    private fun getFood() {
        fdb = FirebaseDatabase.getInstance().reference

        foodId = intent.getStringExtra(config.FOOD_ID).toString()

        val imageUrl = "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FFamousFood%2F$foodId?alt=media"

        val dbFood = fdb.child("Users/FamousFood").child(foodId)
        dbFood.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    foodData = snapshot.getValue(DetailsFoods::class.java)!!
                    m.titleDetails.text = foodData.food_name
                    m.descriptionDetails.text = foodData.food_about

                    Glide.with(this@DetailsActivity)
                        .load(imageUrl)
                        .placeholder(R.drawable.skeleton)
                        .into(m.imageDetails)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}