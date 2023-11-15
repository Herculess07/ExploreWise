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
import com.learning.food1.AdapterClass.home.DevotionAdapter
import com.learning.food1.AdapterClass.home.FoodAdapter
import com.learning.food1.AdapterClass.home.HomeAdapter
import com.learning.food1.AdapterClass.home.PlacesAdapter
import com.learning.food1.Configs
import com.learning.food1.Interfaces.home.DevotionInterface
import com.learning.food1.Interfaces.home.FoodInterface
import com.learning.food1.Interfaces.home.HomeInterface
import com.learning.food1.Interfaces.home.PlacesInterface
import com.learning.food1.Main.BaseFragment
import com.learning.food1.Main.DetailsActivity
import com.learning.food1.Main.SearchActivity
import com.learning.food1.Model.home.ClassVisitedCitiesHome
import com.learning.food1.Model.home.Devotion
import com.learning.food1.Model.home.Food
import com.learning.food1.Model.home.Places
import com.learning.food1.R
import com.learning.food1.databinding.TryHomeLayoutBinding

class HomeFragment : Fragment() {
    private lateinit var m: TryHomeLayoutBinding

    private lateinit var dbRef: DatabaseReference
    private lateinit var getImg: DatabaseReference
    private lateinit var fdb: DatabaseReference
    private lateinit var sRef: StorageReference

    private var lm: LinearLayoutManager? = null
    private var config: Configs = Configs()

    private lateinit var citiesArrayList: ArrayList<ClassVisitedCitiesHome>
    private lateinit var devotionArrayList: ArrayList<Devotion>
    private lateinit var famPlaceArrayList: ArrayList<Places>
    private lateinit var foodArrayList: ArrayList<Food>
    private val base = BaseFragment(activity)

    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // view binding
        m = TryHomeLayoutBinding.inflate(inflater, container, false)
        /*m = DataBindingUtil.inflate(inflater, R.layout.try_home_layout, container, false)*/
        return m.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
            m.rvCity.txtRvHeader.setOnClickListener { smoothScrollCity() }
            m.rvDevotion.txtRvHeader.setOnClickListener { smoothScrollDevotion() }
            m.rvPlace.txtRvHeader.setOnClickListener { smoothScrollPlace() }
            m.rvFood.txtRvHeader.setOnClickListener { smoothScrollFood() }

            initAdapters()
            initSetUpText()

            m.homeSearchView.setOnClickListener {
                val intent = Intent(requireContext(), SearchActivity::class.java)
                startActivity(intent)
            }


        }
    }

    private fun initAdapters() {
        if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
            m.rvCity.rv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            m.rvCity.rv.setHasFixedSize(true)
            getCitiesData()
        }

        if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
            m.rvDevotion.rv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            m.rvDevotion.rv.setHasFixedSize(true)
            getDevotionalPlacesData()
        }

        if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
            m.rvFood.rv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            m.rvFood.rv.setHasFixedSize(true)
            getDeliciousDelightsData()
        }

        if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
            m.rvPlace.rv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            m.rvPlace.rv.setHasFixedSize(true)
            getFamousPlacesData()
        }

    }

    private fun initSetUpText() {
        m.rvCity.txtRvHeader.text = getString(R.string.most_visited_cities)
        m.rvDevotion.txtRvHeader.text = getString(R.string.most_visited_devotion)
        m.rvPlace.txtRvHeader.text = getString(R.string.most_visited_places)
        m.rvFood.txtRvHeader.text = getString(R.string.delicious_delights)
    }

    private fun showLoading() {
        isLoading = true
        m.pbHome.visibility = View.VISIBLE
        m.rvs.visibility = View.GONE
    }

    private fun hideLoading() {
        isLoading = false
        m.pbHome.visibility = View.GONE
        m.rvs.visibility = View.VISIBLE
    }

    private fun getCitiesData() {
        citiesArrayList = ArrayList()
        showLoading()
        fdb = FirebaseDatabase.getInstance().reference

        dbRef =
            fdb.child("Users/DevotionalPlaces")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    try {
                        for (placeSnapshot in snapshot.children) {
                            val city =
                                placeSnapshot.getValue(ClassVisitedCitiesHome::class.java)
                            citiesArrayList.add(city!!)
                        }
                        val itemsAdapter =
                            HomeAdapter(citiesArrayList, object : HomeInterface {
                                override fun onCityClicked(
                                    model: ClassVisitedCitiesHome,
                                    position: Int,
                                ) {
                                    Toast.makeText(
                                        activity,
                                        "${model.devotional_city}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            })
                        m.rvCity.rv.adapter = itemsAdapter
                        itemsAdapter.notifyItemInserted(citiesArrayList.size - 1)
                    } catch (e: Exception) {
                        Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                        e.printStackTrace()
                    }
                } else {
                    Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show()
                }
                hideLoading()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    // For Devotional Places
    private fun getDevotionalPlacesData() {
        devotionArrayList = ArrayList()
        showLoading()

        fdb = FirebaseDatabase.getInstance().reference
        dbRef = fdb.child("Users/DevotionalPlaces")
        try {
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        try {
                            for (placeSnapshot in snapshot.children) {
                                val place: Devotion? = placeSnapshot.getValue(Devotion::class.java)
                                devotionArrayList.add(place!!)
                            }
                            val itemsAdapter =
                                DevotionAdapter(
                                    requireContext(),
                                    devotionArrayList,
                                    object : DevotionInterface {
                                        override fun onDevPlaceClicked(
                                            model: Devotion,
                                            position: Int,
                                        ) {
                                            /*Toast.makeText(
                                                activity,
                                                "${model.devotional_name}",
                                                Toast.LENGTH_SHORT
                                            ).show()*/
                                            val i = Intent(context, DetailsActivity::class.java)
                                            /*i.putExtra(config.KEY_PLACE, model.place_name)*/
                                            i.putExtra(config.DEVOTION_ID, model.devPlaceId)
                                            requireActivity().startActivity(i)

                                        }
                                    })
                            m.rvDevotion.rv.adapter = itemsAdapter
                            itemsAdapter.notifyItemInserted(devotionArrayList.size - 1)
                        } catch (e: Exception) {
                            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT)
                                .show()
                            e.printStackTrace()
                        }
                    } else {
                        Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show()
                    }
                    hideLoading()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("CATCH", e.message.toString())
        }
    }

    // For Famous Places
    private fun getFamousPlacesData() {
        famPlaceArrayList = ArrayList()
        showLoading()

        fdb = FirebaseDatabase.getInstance().reference
        dbRef = fdb.child("Users/FamousPlaces")

        try {
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.exists()) {
                            for (famPlaceSnapshot in snapshot.children) {
                                val famPlace: Places? = famPlaceSnapshot.getValue(Places::class.java)
                                famPlaceArrayList.add(famPlace!!)
                            }
                            val itemsAdapter =
                                PlacesAdapter(requireContext(), famPlaceArrayList,
                                    object : PlacesInterface {
                                        override fun onPlaceClicked(
                                            model: Places,
                                            position: Int,
                                        ) {
                                            val i = Intent(context, DetailsActivity::class.java)
                                            /*i.putExtra(config.KEY_PLACE, model.place_name)*/
                                            i.putExtra(config.PLACE_ID, model.famPlaceID)
                                            requireActivity().startActivity(i)
                                        }

                                    })
                            m.rvPlace.rv.adapter = itemsAdapter
                            itemsAdapter.notifyItemInserted(famPlaceArrayList.size - 1)
                        } else {
                            Toast.makeText(context, "No data available", Toast.LENGTH_SHORT)
                                .show()
                        }
                        hideLoading()
                    } catch (e: Exception) {
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                        Log.d("CATCH", e.message.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("CATCH", e.message.toString())
        }
    }

    // For Famous Food
    private fun getDeliciousDelightsData() {
        foodArrayList = ArrayList()
        showLoading()
        fdb = FirebaseDatabase.getInstance().reference
        try {
            dbRef =
                fdb.child("Users/FamousFood")
            dbRef
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (famFoodSnapshot in snapshot.children) {
                                val famFood: Food =
                                    famFoodSnapshot.getValue(Food::class.java)!!
                                foodArrayList.add(famFood)
                            }
                            val itemsAdapter =
                                FoodAdapter(
                                    requireContext(),
                                    foodArrayList,
                                    object : FoodInterface {
                                        override fun onFoodClicked(model: Food, position: Int) {
                                            val i = Intent(context, DetailsActivity::class.java)
                                            /*i.putExtra(config.KEY_PLACE, model.place_name)*/
                                            i.putExtra(config.FOOD_ID, model.famFoodID)
                                            requireActivity().startActivity(i)
                                        }

                                    })
                            m.rvFood.rv.adapter = itemsAdapter
                            itemsAdapter.notifyItemInserted(foodArrayList.size - 1)
                        } else {
                            Toast.makeText(context, "No data available", Toast.LENGTH_SHORT)
                                .show()
                        }
                        hideLoading()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
                    }
                })
        } catch (e: Exception) {
            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
            Log.d("CATCH", e.message.toString())

        }
    }


    private fun smoothScrollCity() {
        m.rvCity.rv.smoothScrollToPosition(0)
    }

    private fun smoothScrollDevotion() {
        m.rvDevotion.rv.smoothScrollToPosition(0)
    }

    private fun smoothScrollPlace() {
        m.rvPlace.rv.smoothScrollToPosition(0)
    }

    private fun smoothScrollFood() {
        m.rvFood.rv.smoothScrollToPosition(0)
    }

}