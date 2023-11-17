package com.learning.food1.Main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.learning.food1.AdapterClass.CityAdapter
import com.learning.food1.Configs
import com.learning.food1.Model.FamousOfCityModel
import com.learning.food1.databinding.ActivityFamousItemOfCityBinding

class FamousItemsOfCityActivity : AppCompatActivity() {

    private lateinit var m: ActivityFamousItemOfCityBinding
    private lateinit var fdb: DatabaseReference
    private lateinit var dbRef: DatabaseReference
    private lateinit var dbQ: Query
    private lateinit var adapter: CityAdapter
    private lateinit var cityId: String
    private lateinit var cityName: String
    private lateinit var itemArray: ArrayList<FamousOfCityModel>
    private val config = Configs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivityFamousItemOfCityBinding.inflate(layoutInflater)
        setContentView(m.root)
        init()
    }

    private fun init() {
        initText()
        initAdapter()
    }


    private fun initAdapter() {
        m.rvItemCity.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        m.rvItemCity.setHasFixedSize(false)
        fetchAndDisplayData()
    }

    private fun initText(){
        m.toolbarFamousOfCity.imgBookmark.visibility = View.GONE
        m.toolbarFamousOfCity.imgBack.setOnClickListener {
            if(!isDestroyed && !isFinishing){
                finish()
            }
        }
    }

    private fun fetchAndDisplayData() {
        cityId = intent.getStringExtra(config.KEY_ID).toString().trim()
        cityName = intent.getStringExtra(config.KEY_CITY).toString().trim()
        m.toolbarFamousOfCity.txtTitle.text = cityName


        itemArray = ArrayList()
        fdb = FirebaseDatabase.getInstance().reference

        val query: Query = fdb.child("Users/DevotionalPlaces")
            .orderByChild("devotional_city")
            .equalTo(cityName)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    for (snapshot in dataSnapshot.children) {
                        val place = snapshot.getValue(FamousOfCityModel::class.java)
                        Toast.makeText(
                            this@FamousItemsOfCityActivity,
                            "${place!!.devotional_city}",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        place.let {
                            itemArray.add(it)
                        }
                        adapter = CityAdapter(itemArray)
                        m.rvItemCity.adapter = adapter
                        adapter.notifyItemInserted(itemArray.size - 1)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                finish()
            }
        })
    }
}