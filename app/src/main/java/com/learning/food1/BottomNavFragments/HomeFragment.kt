package com.learning.food1.BottomNavFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.learning.food1.AdapterClass.HomeAdapter
import com.learning.food1.AppUtils
import com.learning.food1.Classes.ClassDevotional
import com.learning.food1.Main.FamousItemsOfCityActivity
import com.learning.food1.R
import com.learning.food1.databinding.TryHomeLayoutBinding


class HomeFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var placesArrayList: ArrayList<ClassDevotional>
    private lateinit var b : TryHomeLayoutBinding
    val appUtils = AppUtils()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // view binding
        b = TryHomeLayoutBinding.inflate(inflater,container,false)

        b.homeSearchView.clearFocus()

        b.rcvFamousCitiesHome.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        b.rcvFamousCitiesHome.setHasFixedSize(false)

        b.rcvFamousFoodHome.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        b.rcvFamousFoodHome.setHasFixedSize(false)

        b.rcvFamousPlacesHome.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,true)
        b.rcvFamousFoodHome.setHasFixedSize(true)

        placesArrayList = ArrayList() // List to store retrieved data
        getUserData()

        return b.root
    }

    override fun onStart() {
        super.onStart()
        b.imgAdd.setOnClickListener{
            findNavController().navigate(R.id.goToAddFragment)
        }
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

                    b.rcvFamousCitiesHome.adapter = itemsAdapter

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }

    fun clickListener() {
        val itemsAdapter = HomeAdapter(placesArrayList, this@HomeFragment)

        b.rcvFamousCitiesHome.adapter = itemsAdapter

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


