package com.learning.food1.Main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
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

@SuppressLint("SetTextI18n")
class DetailsActivity : AppCompatActivity() {

    lateinit var m: ActivityDetailsBinding
    lateinit var fdb: DatabaseReference

    lateinit var pd: DetailsPlaces
    lateinit var fd: DetailsFoods
    lateinit var dd: DetailsDevotional

    lateinit var devotionId: String
    lateinit var placeId: String
    lateinit var foodId: String
    private val config = Configs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(m.root)
        init()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun init() {
        getDevPlace()
        getPlace()
        getFood()
        initText()
        m.ab.imgBack.setOnClickListener {
            finish()
        }

    }

    private fun initText() {
        m.ab.txtTitle.text = getString(R.string.details)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    // Devotional Places
    private fun getDevPlace() {
        fdb = FirebaseDatabase.getInstance().reference
        devotionId = intent.getStringExtra(config.DEVOTION_ID).toString()

        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FDevotionalPlaces%2F$devotionId?alt=media"

        val dbDevotion = fdb.child("Users/DevotionalPlaces").child(devotionId)
        dbDevotion.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    dd = snapshot.getValue(DetailsDevotional::class.java)!!
                    m.titleDetails.text = dd.devotional_name
                    m.descriptionDetails.text = dd.devotional_about
                    m.cityDetails.text = dd.devotional_city
                    try {
                        if (
                            dd.devotional_email_address != null ||
                        dd.devotional_website_url != null ||
                        dd.devotional_contact_number != null
                        ){
                            m.imgEmail.setImageResource(R.drawable.baseline_alternate_email_24)
                            m.imgWebsite.setImageResource(R.drawable.baseline_link_24)
                            m.imgPhone.setImageResource(R.drawable.baseline_phone_iphone_24)
                            m.areaDetails.text =
                                "${dd.devotional_area}, ${dd.devotional_city}, ${dd.devotional_state}, ${dd.devotional_postal_code}"
                            m.emailDetails.text = dd.devotional_email_address
                            m.websiteDetails.text = dd.devotional_website_url
                            m.phoneDetails.text = dd.devotional_contact_number
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

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

        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FFamousPlaces%2F$placeId?alt=media"

        val dbPlace = fdb.child("Users/FamousPlaces").child(placeId)
        dbPlace.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    pd = snapshot.getValue(DetailsPlaces::class.java)!!
                    m.titleDetails.text = pd.place_name
                    m.descriptionDetails.text = pd.place_about
                    m.cityDetails.text = pd.place_city
                    try {
                        m.areaDetails.text =
                            "${pd.place_area}, ${pd.place_city}, ${pd.place_state}, ${pd.place_postal_code}"
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

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

    // Famous Food
    private fun getFood() {
        fdb = FirebaseDatabase.getInstance().reference

        foodId = intent.getStringExtra(config.FOOD_ID).toString()

        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FFamousFood%2F$foodId?alt=media"

        val dbFood = fdb.child("Users/FamousFood").child(foodId)
        dbFood.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    fd = snapshot.getValue(DetailsFoods::class.java)!!
                    m.titleDetails.text = fd.food_name
                    m.descriptionDetails.text = fd.food_about
                    m.cityDetails.text = fd.food_city
                    try {
                        m.areaDetails.text =
                            "${fd.food_area}, ${fd.food_city}, ${fd.food_state}, ${fd.food_postal_code}"
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

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