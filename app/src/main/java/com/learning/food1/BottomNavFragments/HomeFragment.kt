package com.learning.food1.BottomNavFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
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
import com.learning.food1.Main.FamousItemsOfCityActivity
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
        m = TryHomeLayoutBinding.inflate(
            inflater,
            container,
            false
        )
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

            m.rvPlace.txtViewAll.setOnClickListener {
                replaceFragment(ViewAllFragment(), requireContext())


            }

            initAdapters()
            initSetUpText()
            autoImageSlider()



            m.homeSearchView.setOnClickListener {
                val intent = Intent(requireContext(), SearchActivity::class.java)
                startActivity(intent)
            }


        }
    }

    private fun replaceFragment(fragment: Fragment, context: Context) {
        if (context is FragmentActivity) {
            val fragmentManager = context.supportFragmentManager
            fragmentManager.beginTransaction()
                .add(R.id.try_home_fragment, fragment)
                .setCustomAnimations(
                    R.anim.anim_enter,
                    R.anim.anim_exit
                )
                .addToBackStack(fragment.javaClass.name)
                .commit()


        } else {
            // Handle the case where the context is not a FragmentActivity
            // This could happen if the context is not the expected type.
        }
    }

    private fun initAdapters() {
        if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
            m.rvCity.rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            m.rvCity.rv.setHasFixedSize(true)
            getCitiesData()
        }

        if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
            m.rvDevotion.rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            m.rvDevotion.rv.setHasFixedSize(true)
            getDevotionalPlacesData()
        }

        if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
            m.rvFood.rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            m.rvFood.rv.setHasFixedSize(true)
            getDeliciousDelightsData()
        }

        if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
            m.rvPlace.rv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
        m.imageSlider.visibility = View.GONE
    }

    private fun hideLoading() {
        isLoading = false
        m.pbHome.visibility = View.GONE
        m.rvs.visibility = View.VISIBLE
        m.imageSlider.visibility = View.VISIBLE
    }

    private fun getCitiesData() {
        citiesArrayList = ArrayList()
        showLoading()
        fdb = FirebaseDatabase.getInstance().reference


        dbRef = fdb.child("Users/DevotionalPlaces")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded && activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
                    if (snapshot.exists()) {
                        try {
                            for (placeSnapshot in snapshot.children) {
                                val city =
                                    placeSnapshot.getValue(ClassVisitedCitiesHome::class.java)
                                citiesArrayList.add(city!!)
                            }
                            val itemsAdapter = HomeAdapter(citiesArrayList, object : HomeInterface {
                                override fun onCityClicked(
                                    model: ClassVisitedCitiesHome,
                                    position: Int,
                                ) {
                                    val i = Intent(
                                        context,
                                        FamousItemsOfCityActivity::class.java
                                    )
                                    i.putExtra(config.KEY_ID, model.devPlaceID)
                                    i.putExtra(config.KEY_CITY, model.devotional_city)
                                    startActivity(i)
                                    /*Toast.makeText(
                                        requireContext(),
                                        "${model.devotional_city}",
                                        Toast.LENGTH_SHORT
                                    ).show()*/
                                }
                            })
                            m.rvCity.rv.adapter = itemsAdapter
                            itemsAdapter.notifyItemInserted(citiesArrayList.size - 1)
                        } catch (e: Exception) {
                            Toast.makeText(
                                requireContext(), e.message.toString(), Toast.LENGTH_SHORT
                            ).show()
                            e.printStackTrace()
                        }
                    } else {
                        Toast.makeText(requireContext(), "No data available", Toast.LENGTH_SHORT)
                            .show()
                    }
                    hideLoading()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.toString(), Toast.LENGTH_SHORT).show()
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
                    if (isAdded && activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
                        if (snapshot.exists()) {
                            try {
                                for (devSnapshot in snapshot.children) {
                                    val place: Devotion? =
                                        devSnapshot.getValue(Devotion::class.java)
                                    devotionArrayList.add(place!!)
                                }
                                val itemsAdapter = DevotionAdapter(requireContext(),
                                    devotionArrayList,
                                    object : DevotionInterface {
                                        override fun onDevPlaceClicked(
                                            model: Devotion,
                                            position: Int,
                                        ) {
                                            val i = Intent(
                                                context,
                                                DetailsActivity::class.java
                                            )/*i.putExtra(config.KEY_PLACE, model.place_name)*/
                                            i.putExtra(config.DEVOTION_ID, model.devPlaceID)
                                            requireActivity().startActivity(i)

                                        }
                                    })
                                m.rvDevotion.rv.adapter = itemsAdapter
                                itemsAdapter.notifyItemInserted(devotionArrayList.size - 1)
                            } catch (e: Exception) {
                                Toast.makeText(
                                    requireContext(), e.message.toString(), Toast.LENGTH_SHORT
                                ).show()
                                e.printStackTrace()
                            }
                        } else {
                            Toast.makeText(
                                requireContext(), "No data available", Toast.LENGTH_SHORT
                            ).show()
                        }
                        hideLoading()
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
                        if (isAdded && activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
                            if (snapshot.exists()) {
                                for (famPlaceSnapshot in snapshot.children) {
                                    val famPlace: Places? =
                                        famPlaceSnapshot.getValue(Places::class.java)
                                    famPlaceArrayList.add(famPlace!!)
                                }
                                val itemsAdapter = PlacesAdapter(
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
                                            )/*i.putExtra(config.KEY_PLACE, model.place_name)*/
                                            i.putExtra(config.PLACE_ID, model.famPlaceID)
                                            requireActivity().startActivity(i)
                                        }

                                    })
                                m.rvPlace.rv.adapter = itemsAdapter
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

    // For Famous Food
    private fun getDeliciousDelightsData() {
        foodArrayList = ArrayList()
        showLoading()
        fdb = FirebaseDatabase.getInstance().reference
        try {
            dbRef = fdb.child("Users/FamousFood")
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (isAdded && activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
                        if (snapshot.exists()) {
                            for (famFoodSnapshot in snapshot.children) {
                                val famFood: Food = famFoodSnapshot.getValue(Food::class.java)!!
                                foodArrayList.add(famFood)
                            }
                            val itemsAdapter = FoodAdapter(requireContext(),
                                foodArrayList,
                                object : FoodInterface {
                                    override fun onFoodClicked(model: Food, position: Int) {
                                        val i = Intent(
                                            context,
                                            DetailsActivity::class.java
                                        )/*i.putExtra(config.KEY_PLACE, model.place_name)*/
                                        i.putExtra(config.FOOD_ID, model.famFoodID)
                                        requireActivity().startActivity(i)
                                    }

                                })
                            m.rvFood.rv.adapter = itemsAdapter
                            itemsAdapter.notifyItemInserted(foodArrayList.size - 1)
                        } else {
                            Toast.makeText(
                                requireContext(), "No data available", Toast.LENGTH_SHORT
                            ).show()
                        }
                        hideLoading()
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

    private fun autoImageSlider() {
        if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
            val imageList = ArrayList<SlideModel>() // Create image list

            val img1 =
                "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/raw%2Fdavide-cantelli-jpkfc5_d-DI-unsplash.jpg?alt=media&token=daa1e98f-40a6-4951-9537-3735b2cfccf0"
            val img2 =
                "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/raw%2Fdeepak-kosta-tlu3Zpcw6nY-unsplash.jpg?alt=media&token=f119c8af-2d14-4b22-b760-bb4f3a760c1a"
            val img3 =
                "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/raw%2Fkabir-cheema-8T9AVksyt7s-unsplash.jpg?alt=media&token=af529f26-1b12-46cf-8762-7126e5cda57a"
            val img4 =
                "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/raw%2Fkoushik-chowdavarapu-mNFlngSAQUg-unsplash.jpg?alt=media&token=dbf0e01c-665e-46e5-954f-dddcf7e3dde7"
            val img5 =
                "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/raw%2Fsk-fY-ArEvk7sc-unsplash.jpg?alt=media&token=4bc8145d-689b-40c9-a195-c33f9b4a7cb3"

            imageList.add(SlideModel(img1))
            imageList.add(SlideModel(img2))
            imageList.add(SlideModel(img3))
            imageList.add(SlideModel(img4))
            imageList.add(SlideModel(img5))

            /*Glide.with(requireContext())
                .load(imageList)
                .placeholder(R.drawable.skeleton)*/


            m.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
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


    companion object {
        const val TAG = "HomeFragment"
    }

}