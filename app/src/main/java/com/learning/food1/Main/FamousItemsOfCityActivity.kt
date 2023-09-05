package com.learning.food1.Main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.learning.food1.Model.ItemRecyclerVModel
import com.learning.food1.databinding.ActivityFamousItemOfCityBinding
import java.util.ArrayList

class FamousItemsOfCityActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFamousItemOfCityBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFamousItemOfCityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("FamousFood")

        // calling method
        // for getting data.
        getdata()
    }

    private fun fetchAndDisplayData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = ArrayList<ItemRecyclerVModel>() // List to store retrieved data

                val recyclerView = binding.recyclerviewFamousItemsOfCity

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
    }

}