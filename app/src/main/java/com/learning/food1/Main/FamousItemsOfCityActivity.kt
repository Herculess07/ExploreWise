package com.learning.food1.Main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.learning.food1.AdapterClass.FamousOfCityAdapter
import com.learning.food1.Model.FamousOfCityModel
import com.learning.food1.databinding.ActivityFamousItemOfCityBinding

class FamousItemsOfCityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFamousItemOfCityBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FamousOfCityAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFamousItemOfCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("Users").child("DevotionalPlaces")

        val recyclerView = binding.recyclerviewFamousItemsOfCity
        recyclerView.layoutManager = LinearLayoutManager(this)

        val options: FirebaseRecyclerOptions<FamousOfCityModel> =
            FirebaseRecyclerOptions.Builder<FamousOfCityModel>()
                .setQuery(databaseReference,FamousOfCityModel::class.java)
                .build()

        adapter = FamousOfCityAdapter(options)
        recyclerView.adapter = adapter

        val cityId = intent.getStringExtra("cityId")
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


//    private fun fetchAndDisplayData() {
//        databaseReference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val data = ArrayList<ItemRecyclerVModel>() // List to store retrieved data
//
//                for (snapshot in dataSnapshot.children) {
//                    val place = snapshot.getValue(ItemRecyclerVModel::class.java)
//                    place?.let {
//                        data.add(it) // Add retrieved data to the list
//                    }
//                }
//
//                // Update your RecyclerView adapter with placeList
//                // Notify the adapter of data changes
//                recyclerView?.adapter?.notifyDataSetChanged()
//
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle error
//            }
//        })
//    }

//    private fun getdata() {
//        databaseReference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val value = snapshot.getValue(String::class.java)
//                binding.idTVRetrieveData.text = value
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(
//                    this@FamousItemsOfCityActivity,
//                    "Fail to get data.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
//    }


}