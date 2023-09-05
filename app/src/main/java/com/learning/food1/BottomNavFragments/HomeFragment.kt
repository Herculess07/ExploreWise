package com.learning.food1.BottomNavFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.learning.food1.AdapterClass.HomeAdapter
import com.learning.food1.Classes.ClassDevotional
import com.learning.food1.R
import java.util.ArrayList


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

        recyclerView.setHasFixedSize(true)

        placesArrayList = ArrayList<ClassDevotional>() // List to store retrieved data

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
                    recyclerView.adapter = HomeAdapter(placesArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

}

