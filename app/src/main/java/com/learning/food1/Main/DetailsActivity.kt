package com.learning.food1.Main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.learning.food1.Configs
import com.learning.food1.Model.bookmark.BookmarkedItems
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

    private var name: String? = null
    private var city: String? = null
    private var state: String? = null
    private var postalCode: String? = null
    private var area: String? = null
    private var about: String? = null
    private var email: String? = null
    private var website: String? = null
    private var contact: String? = null
    private var id: String? = null

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
                if (!isDestroyed && !isFinishing) {
                    if (snapshot.exists()) {
                        dd = snapshot.getValue(DetailsDevotional::class.java)!!
                        id = devotionId
                        name = dd.devotional_name
                        city = dd.devotional_city
                        state = dd.devotional_state
                        postalCode = dd.devotional_postal_code
                        area = dd.devotional_area
                        about = dd.devotional_about
                        email = dd.devotional_email_address
                        website = dd.devotional_website_url
                        contact = dd.devotional_contact_number



                        m.titleDetails.text = name
                        m.descriptionDetails.text = about
                        m.cityDetails.text = city
                        m.areaDetails.text =
                            "${area}, ${city}, ${state}, ${postalCode}"

                        try {
                            if (!TextUtils.isEmpty(email)) {
                                m.layoutEmail.visibility = View.VISIBLE
                                m.imgEmail.setImageResource(R.drawable.baseline_alternate_email_24)
                                m.emailDetails.text = email
                            } else {
                                m.layoutEmail.visibility = View.GONE
                            }
                            if (!TextUtils.isEmpty(website)) {
                                m.layoutWebsite.visibility = View.VISIBLE
                                m.imgWebsite.setImageResource(R.drawable.baseline_link_24)
                                m.websiteDetails.text = website
                                m.websiteDetails.setOnClickListener {
                                    config.openUrl(
                                        this@DetailsActivity,
                                        dd.devotional_website_url!!
                                    )
                                }
                            } else {
                                m.layoutWebsite.visibility = View.GONE
                            }
                            if (!TextUtils.isEmpty(contact)) {
                                m.layoutPhone.visibility = View.VISIBLE
                                m.imgPhone.setImageResource(R.drawable.baseline_phone_iphone_24)
                                m.phoneDetails.text = contact

                                m.phoneDetails.setOnClickListener {
                                    config.openDialer(
                                        this@DetailsActivity,
                                        dd.devotional_contact_number!!
                                    )
                                }
                            } else {
                                m.layoutPhone.visibility = View.GONE
                            }
                            m.ab.imgBookmark.setOnClickListener {
                                toggleBookmarkStatus(
                                    id!!,
                                    name.toString(),
                                    city.toString(),
                                    state.toString(),
                                    true
                                )
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                            throw e.message?.let { Exception(it) }!!
                        }

                        Glide.with(this@DetailsActivity)
                            .load(imageUrl)
                            .placeholder(R.drawable.skeleton)
                            .into(m.imageDetails)
                    }
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
                if (!isDestroyed && !isFinishing) {
                    if (snapshot.exists()) {
                        pd = snapshot.getValue(DetailsPlaces::class.java)!!

                        id = placeId
                        name = pd.place_name
                        city = pd.place_city
                        state = pd.place_state
                        postalCode = pd.place_postal_code
                        area = pd.place_area
                        about = pd.place_about
                        email = pd.place_email_address
                        website = pd.place_website_url
                        contact = pd.place_contact_number


                        m.titleDetails.text = name
                        m.descriptionDetails.text = about
                        m.cityDetails.text = city

                        m.areaDetails.text =
                            "${area}, ${city}, ${state}, ${postalCode}"
                        try {
                            if (!TextUtils.isEmpty(email)) {
                                m.layoutEmail.visibility = View.VISIBLE
                                m.imgEmail.setImageResource(R.drawable.baseline_alternate_email_24)
                                m.emailDetails.text = email
                            } else {
                                m.layoutEmail.visibility = View.GONE
                            }
                            if (!TextUtils.isEmpty(website)) {
                                m.layoutWebsite.visibility = View.VISIBLE
                                m.imgWebsite.setImageResource(R.drawable.baseline_link_24)
                                m.websiteDetails.text = website
                                 m.websiteDetails.setOnClickListener {
                                     config.openUrl(this@DetailsActivity, pd.place_website_url!!)
                                 }
                            } else {
                                m.layoutWebsite.visibility = View.GONE
                            }
                            if (!TextUtils.isEmpty(contact)) {
                                m.layoutPhone.visibility = View.VISIBLE
                                m.imgPhone.setImageResource(R.drawable.baseline_phone_iphone_24)
                                m.phoneDetails.text = contact

                                 m.phoneDetails.setOnClickListener {
                                     config.openDialer(
                                         this@DetailsActivity,
                                         pd.place_contact_number!!
                                     )
                                 }
                            } else {
                                m.layoutPhone.visibility = View.GONE
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                            throw e.message?.let { Exception(it) }!!
                        }
                        m.ab.imgBookmark.setOnClickListener {
                            toggleBookmarkStatus(
                                id!!,
                                name.toString(),
                                city.toString(),
                                state.toString(),
                                true
                            )
                        }

                        Glide.with(this@DetailsActivity)
                            .load(imageUrl)
                            .placeholder(R.drawable.skeleton)
                            .into(m.imageDetails)

                    }
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
                if (!isDestroyed && !isFinishing) {
                    if (snapshot.exists()) {
                        fd = snapshot.getValue(DetailsFoods::class.java)!!

                        id = foodId
                        name = fd.food_name
                        city = fd.food_city
                        state = fd.food_state
                        postalCode = fd.food_postal_code
                        area = fd.food_area
                        about = fd.food_about
                        email = fd.food_email_address
                        contact = fd.food_contact_number
                        website = fd.food_website_url

                        m.titleDetails.text = name
                        m.descriptionDetails.text = about
                        m.cityDetails.text = city
                        m.areaDetails.text =
                            "${area}, ${city}, ${state}, ${postalCode}"
                        try {
                            m.layoutEmail.visibility =
                                if (!TextUtils.isEmpty(email)) {
                                    m.imgEmail.setImageResource(R.drawable.baseline_alternate_email_24)
                                    m.emailDetails.text = email
                                    View.VISIBLE
                                } else {
                                    View.GONE
                                }

                            m.layoutWebsite.visibility =
                                if (TextUtils.isEmpty(website)) {
                                    m.imgWebsite.setImageResource(R.drawable.baseline_link_24)
                                    m.websiteDetails.text = website
                                    m.websiteDetails.setOnClickListener {
                                        config.openUrl(this@DetailsActivity, fd.food_website_url!!)
                                    }
                                    View.VISIBLE
                                } else {
                                    View.GONE
                                }

                            m.layoutPhone.visibility =
                                if (!TextUtils.isEmpty(website)) {
                                    m.imgPhone.setImageResource(R.drawable.baseline_phone_iphone_24)
                                    m.phoneDetails.text = website
                                    m.phoneDetails.setOnClickListener {
                                        config.openDialer(
                                            this@DetailsActivity,
                                            fd.food_contact_number!!
                                        )
                                    }
                                    View.VISIBLE
                                } else {
                                    View.GONE
                                }

                            m.ab.imgBookmark.setOnClickListener {
                                toggleBookmarkStatus(
                                    id!!,
                                    name.toString(),
                                    city.toString(),
                                    state.toString(),
                                    true
                                )
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                            throw Exception(e.message ?: "Unknown exception")
                        }


                        Glide.with(this@DetailsActivity)
                            .load(imageUrl)
                            .placeholder(R.drawable.skeleton)
                            .into(m.imageDetails)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }


    private fun toggleBookmarkStatus(
        placeId: String,
        place_name: String,
        place_city: String,
        place_state: String,
        bookmark: Boolean,
    ) {
        val uid = FirebaseAuth.getInstance().uid
        val database = FirebaseDatabase.getInstance()

        val updatedBookmarkIcon =
            if (bookmark) R.drawable.baseline_bookmark_24
            else R.drawable.baseline_bookmark_border_24
        m.ab.imgBookmark.setImageResource(updatedBookmarkIcon)

        if (uid != null) {
            if (bookmark) {
                addToBookmark(uid, placeId, place_name, place_city, place_state,bookmark)
            } else {
                removeFromBookmark(uid, placeId)
            }
        }

        // Update the tag of the bookmark icon to reflect the new status
        // bookmark = newBookmarkStatus
    }

    private fun addToBookmark(
        uid: String,
        placeID: String,
        place_name: String,
        place_city: String,
        place_state: String,
        bookmark: Boolean,
    ) {
        val database = FirebaseDatabase.getInstance()
        val bookmarksRef = database.getReference("Users/Bookmarks/$uid")

        val bookmarkKey = bookmarksRef.push().key
        val bookmarkedPlace = BookmarkedItems(
            devPlaceID = placeID,
            devotional_name = place_name,
            devotional_city = place_city,
            devotional_state = place_state,
            bookmark = bookmark,
        )

        bookmarkKey?.let {
            bookmarksRef.child(it).setValue(bookmarkedPlace)
        }
    }

    private fun removeFromBookmark(uid: String, placeID: String) {
        val database = FirebaseDatabase.getInstance()
        val bookmarksRef = database.getReference("Users/Bookmarks/$uid")

        val query = bookmarksRef.orderByChild("devPlaceId").equalTo(placeID)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (childSnapshot in snapshot.children) {
                    childSnapshot.ref.removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DetailsActivity, "Bookmark Not Removed", Toast.LENGTH_SHORT).show()
            }
        })
    }


}