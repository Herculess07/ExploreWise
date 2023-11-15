package com.learning.food1.Main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.learning.food1.AdapterClass.CityAdapter
import com.learning.food1.Configs
import com.learning.food1.Model.FamousOfCityModel
import com.learning.food1.databinding.ActivityFamousItemOfCityBinding

class FamousItemsOfCityActivity : AppCompatActivity() {

    private lateinit var m: ActivityFamousItemOfCityBinding
    private lateinit var fdb: FirebaseDatabase
    private lateinit var dbRef: Query
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CityAdapter
    private lateinit var cityId: String
    private lateinit var cityName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivityFamousItemOfCityBinding.inflate(layoutInflater)
        setContentView(m.root)

        fdb = FirebaseDatabase.getInstance()

        init()
    }

    private fun init() {
        val config = Configs()

        cityId = intent.getStringExtra(config.KEY_ID).toString()
        cityName = intent.getStringExtra(config.KEY_DEVOTION).toString()


        m.toolbarFamousOfCity.title = cityName
        recyclerView = m.recyclerviewFamousItemsOfCity
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        dbRef = fdb.reference.child("Users/DevotionalPlaces")
            .orderByChild("cityId")
            .equalTo(cityId)


        val options: FirebaseRecyclerOptions<FamousOfCityModel> =
            FirebaseRecyclerOptions.Builder<FamousOfCityModel>()
                .setQuery(dbRef, FamousOfCityModel::class.java)
                .build()



        adapter = CityAdapter(options)


        recyclerView.setHasFixedSize(false)
        recyclerView.adapter = adapter


    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


    /*private fun fetchAndDisplayData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = ArrayList<ItemRecyclerVModel>() // List to store retrieved data

                for (snapshot in dataSnapshot.children) {
                    val place = snapshot.getValue(ItemRecyclerVModel::class.java)
                    place?.let {
                        data.add(it) // Add retrieved data to the list
                    }
                }

                // Update your RecyclerView adapter with placeList
                // Notify the adapter of data changes
                recyclerView?.adapter?.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun getdata() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(String::class.java)
                binding.idTVRetrieveData.text = value
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@FamousItemsOfCityActivity,
                    "Fail to get data.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }*/


}