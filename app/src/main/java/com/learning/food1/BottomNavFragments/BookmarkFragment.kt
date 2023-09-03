package com.learning.food1.BottomNavFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.learning.food1.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.food1.AdapterClass.BookmarkAdapter
import com.learning.food1.Model.BookmarkRecyclerVModel


class BookmarkFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // defining views from xml file
        val rootView = inflater.inflate(R.layout.fragment_bookmark, container, false)

        // getting the recyclerview by its id
        val recyclerview = rootView.findViewById<RecyclerView>(R.id.recyclerviewBookmarkF)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(context)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<BookmarkRecyclerVModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(BookmarkRecyclerVModel(R.drawable.sample_food_image, "City/Food Name " + i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = BookmarkAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        return rootView

    }

}