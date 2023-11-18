package com.learning.food1.BottomNavFragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.learning.food1.AdapterClass.ViewAllPlacesAdapter
import com.learning.food1.Configs
import com.learning.food1.Interfaces.home.PlacesInterface
import com.learning.food1.Main.DetailsActivity
import com.learning.food1.Model.home.ClassVisitedCitiesHome
import com.learning.food1.Model.home.Devotion
import com.learning.food1.Model.home.Food
import com.learning.food1.Model.home.Places
import com.learning.food1.databinding.FragmentViewAllBinding

class ViewAllFragment : Fragment() {

    private lateinit var m: FragmentViewAllBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var fdb: DatabaseReference
    private lateinit var sRef: StorageReference

    private var lm: LinearLayoutManager? = null
    private var config: Configs = Configs()

    private lateinit var citiesArrayList: ArrayList<ClassVisitedCitiesHome>
    private lateinit var devotionArrayList: ArrayList<Devotion>
    private lateinit var famPlaceArrayList: ArrayList<Places>
    private lateinit var foodArrayList: ArrayList<Food>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        m = FragmentViewAllBinding.inflate(
            inflater,
            container,
            false
        )
        return m.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapters()
    }


    private fun initAdapters() {
        if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
            m.rvViewAll.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            // m.rvViewAll.setHasFixedSize(true)
            getFamousPlacesData()
        }
    }


    private fun getFamousPlacesData() {
        famPlaceArrayList = ArrayList()
        showLoading()

        fdb = FirebaseDatabase.getInstance().reference
        dbRef = fdb.child("Users/FamousPlaces")

        try {
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (isAdded && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
                            if (snapshot.exists()) {
                                for (famPlaceSnapshot in snapshot.children) {
                                    val famPlace: Places? =
                                        famPlaceSnapshot.getValue(Places::class.java)
                                    famPlaceArrayList.add(famPlace!!)
                                }
                                val itemsAdapter = ViewAllPlacesAdapter(
                                    requireContext(),
                                    famPlaceArrayList,
                                    object : PlacesInterface {
                                        override fun onPlaceClicked(
                                            model: Places,
                                            position: Int,
                                        ) {
                                            val i = Intent(
                                                context,
                                                DetailsActivity::class.java
                                            )
                                            i.putExtra(config.PLACE_ID, model.famPlaceID)
                                            requireActivity().startActivity(i)
                                        }

                                    })
                                m.rvViewAll.adapter = itemsAdapter
                                itemsAdapter.notifyItemInserted(famPlaceArrayList.size - 1)
                            } else {
                                Toast.makeText(
                                    requireContext(), "No data available", Toast.LENGTH_SHORT
                                ).show()
                            }
                            hideLoading()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("CATCH", e.message.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("CATCH", e.message.toString())
        }
    }

    private fun showLoading(){
        m.pbViewAll.visibility = View.VISIBLE
        m.rvViewAll.visibility = View.GONE
    }

    private fun hideLoading(){
        m.pbViewAll.visibility = View.GONE
        m.rvViewAll.visibility = View.VISIBLE
    }

    companion object {
        const val TAG = "ViewALlFragment"
    }

}