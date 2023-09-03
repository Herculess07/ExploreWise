package com.learning.food1.BottomNavFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.learning.food1.AdapterClass.HomeAdapter
import com.learning.food1.Main.FamousItemsOfCityActivity
import com.learning.food1.Model.ItemRecyclerVModel
import com.learning.food1.R


class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

    //    private lateinit var famousFoodsAdapter: FamousFoodsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // defining views from xml file
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerview = rootView.findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemRecyclerVModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemRecyclerVModel(R.drawable.sample_place_image, "City Name " + i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = HomeAdapter(data)


        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

//        val onItemClickListener = View.OnClickListener { view ->
//            val position = recyclerview.getChildAdapterPosition(view)
//            val item = data[position]
//
//            // Create an intent to start the new activity.
//            val intent = Intent(this@HomeFragment, FamousItemsOfCityActivity::class.java)
//
//
//            // Pass the item to the new activity.
//            intent.putExtra("item", item)
//
//            // Start the new activity.
//            startActivity(intent)
//        }
        return rootView
    }

}

