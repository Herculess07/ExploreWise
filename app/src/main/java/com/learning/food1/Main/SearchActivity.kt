package com.learning.food1.Main

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.learning.food1.AdapterClass.search.SearchAdapter
import com.learning.food1.AdapterClass.search.SearchDevotionalAdapter
import com.learning.food1.AdapterClass.search.SearchFoodAdapter
import com.learning.food1.Configs
import com.learning.food1.Interfaces.search.SearchDevotionalInterface
import com.learning.food1.Interfaces.search.SearchFoodInterface
import com.learning.food1.Interfaces.search.SearchInterface
import com.learning.food1.Model.search.SearchDevotionalModel
import com.learning.food1.Model.search.SearchFoodModel
import com.learning.food1.Model.search.SearchModel
import com.learning.food1.R
import com.learning.food1.databinding.ActivitySearchBinding


class SearchActivity : AppCompatActivity() {

    private lateinit var m: ActivitySearchBinding
    private val config = Configs()
    var dbRef: DatabaseReference? = null
    var userRef: DatabaseReference? = null
    var userDev: DatabaseReference? = null
    var userFood: DatabaseReference? = null
    var found: Boolean? = null;
    lateinit var searchList: ArrayList<SearchModel>
    lateinit var searchDevList: ArrayList<SearchDevotionalModel>
    lateinit var searchFoodList: ArrayList<SearchFoodModel>
    var exSearch: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(m.root)
        init()
    }

    fun init() {
        m.homeSearchView.requestFocus()
        searchList = ArrayList()
        searchDevList = ArrayList()
        searchFoodList = ArrayList()
        dbRef = FirebaseDatabase.getInstance().reference

        /*m.searchButton.setOnClickListener {
            searchInPlace(exSearch!!)
        }*/
        initAdapter()
        m.homeSearchView.queryHint = getString(R.string.search_text)
        searchText()

    }

    private fun initAdapter() {

        m.rvSearchPlace.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        m.rvSearchPlace.setHasFixedSize(true)

        m.rvSearchDevotional.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        m.rvSearchDevotional.setHasFixedSize(true)

        m.rvSearchFood.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        m.rvSearchFood.setHasFixedSize(true)
    }


    private fun searchText() {
        m.homeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(search: String): Boolean {

                if (search.length >= 3) {
                    exSearch = search
                    searchInPlace(search)
                    searchInDevotional(search)
                    searchInFood(search)
                }
                return true
            }
        });
    }


    private fun searchInPlace(search: String) {
        userRef = dbRef!!.child("Users/FamousPlaces")

        userRef!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isDestroyed && !isFinishing) {
                    if (snapshot.exists()) {
                        searchList.clear()
                        for (snap in snapshot.children) {
                            try {
                                val placeName = snap.getValue(SearchModel::class.java)
                                if (
                                    placeName != null &&
                                    placeName.place_name!!.lowercase()
                                        .contains(search.lowercase()) ||
                                    placeName!!.place_city!!.lowercase()
                                        .contains(search.lowercase()) ||
                                    placeName.place_state!!.lowercase().contains(search.lowercase())

                                ) {
                                    searchList.add(placeName)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        m.rvSearchPlace.adapter =
                            SearchAdapter(this@SearchActivity, searchList, object :
                                SearchInterface {
                                override fun onSearchPlaceClicked(
                                    model: SearchModel,
                                    position: Int,
                                ) {
                                    val i = Intent(
                                        this@SearchActivity,
                                        DetailsActivity::class.java
                                    )
                                    i.putExtra(config.PLACE_ID, model.famPlaceID)
                                    startActivity(i)
                                }
                            })
                        m.rvSearchPlace.adapter!!.notifyItemInserted(searchList.size - 1)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SearchActivity, "Not Found Error", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun searchInDevotional(search: String) {

        userDev = dbRef!!.child("Users/DevotionalPlaces")

        userDev!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isDestroyed && !isFinishing) {
                    if (snapshot.exists()) {
                        searchDevList.clear()
                        for (snap in snapshot.children) {
                            try {
                                val placeName = snap.getValue(SearchDevotionalModel::class.java)
                                if (placeName != null &&
                                    placeName.devotional_name!!.lowercase()
                                        .contains(search.lowercase()) ||
                                    placeName!!.devotional_city!!.lowercase()
                                        .contains(search.lowercase()) ||
                                    placeName.devotional_state!!.lowercase()
                                        .contains(search.lowercase())
                                ) {
                                    searchDevList.add(placeName)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        m.rvSearchDevotional.adapter =
                            SearchDevotionalAdapter(this@SearchActivity, searchDevList, object :
                                SearchDevotionalInterface {
                                override fun onSearchDevClicked(
                                    model: SearchDevotionalModel,
                                    position: Int,
                                ) {
                                    val i = Intent(
                                        this@SearchActivity,
                                        DetailsActivity::class.java
                                    )
                                    i.putExtra(config.DEVOTION_ID, model.devPlaceID)
                                    startActivity(i)
                                }
                            })
                        m.rvSearchDevotional.adapter!!.notifyItemInserted(searchDevList.size - 1)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SearchActivity, "Not Found Error", Toast.LENGTH_SHORT).show()
            }
        })

    }


    private fun searchInFood(search: String) {

        userFood = dbRef!!.child("Users/FamousFood")

        userFood!!.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isDestroyed && !isFinishing) {
                    if (snapshot.exists()) {
                        searchFoodList.clear()
                        for (snap in snapshot.children) {
                            try {
                                val placeName = snap.getValue(SearchFoodModel::class.java)
                                if (placeName != null &&
                                    placeName.food_name!!.lowercase()
                                        .contains(search.lowercase()) ||
                                    placeName!!.food_city!!.lowercase()
                                        .contains(search.lowercase()) ||
                                    placeName.food_state!!.lowercase().contains(search.lowercase())
                                ) {
                                    searchFoodList.add(placeName)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                        m.rvSearchFood.adapter =
                            SearchFoodAdapter(this@SearchActivity, searchFoodList, object :
                                SearchFoodInterface {
                                override fun onSearchFoodClicked(
                                    model: SearchFoodModel,
                                    position: Int,
                                ) {
                                    val i = Intent(
                                        this@SearchActivity,
                                        DetailsActivity::class.java
                                    )
                                    i.putExtra(config.FOOD_ID, model.famFoodID)
                                    startActivity(i)
                                }
                            })
                        m.rvSearchFood.adapter!!.notifyItemInserted(searchFoodList.size - 1)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SearchActivity, "Not Found Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

}