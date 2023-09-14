package com.learning.food1.BottomNavFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.learning.food1.AdapterClass.HomeAdapter
import com.learning.food1.Classes.ClassDevotional
import com.learning.food1.Main.FamousItemsOfCityActivity
import com.learning.food1.R


class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var placesArrayList: ArrayList<ClassDevotional>
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // defining views from xml file
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerview)
        searchView = rootView.findViewById(R.id.homeSearchView)

        searchView.clearFocus()

        recyclerView.layoutManager =
            LinearLayoutManager(context) // this creates a vertical layout Manager
        recyclerView.setHasFixedSize(false)

        placesArrayList = ArrayList() // List to store retrieved data
        getUserData()

        return rootView
    }

    private fun getUserData() {
        databaseReference =
            FirebaseDatabase.getInstance().getReference("Users").child("DevotionalPlaces")
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (placeSnapshot in snapshot.children) {

                        val place = placeSnapshot.getValue(ClassDevotional::class.java)
                        placesArrayList.add(place!!)

                    }
                    val itemsAdapter = HomeAdapter(placesArrayList, this@HomeFragment)

                    recyclerView.adapter = itemsAdapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun clickListener() {
        val itemsAdapter = HomeAdapter(placesArrayList, this@HomeFragment)

        recyclerView?.adapter = itemsAdapter

        itemsAdapter.setOnClickListener(object : HomeAdapter.OnClickListener {
            override fun onClick(position: Int, model: ClassDevotional) {
                val intent = Intent(context, FamousItemsOfCityActivity::class.java)

                intent.putExtra(NEXT_SCREEN,placesArrayList)
                startActivity(intent)
            }
        })
    }

    companion object {
        val NEXT_SCREEN = "details_screen"
    }

}


