package com.learning.food1.BottomNavFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.food1.AdapterClass.BookmarkAdapter
import com.learning.food1.Model.BookmarkRecyclerVModel
import com.learning.food1.R
import com.learning.food1.databinding.FragmentBookmarkBinding


class BookmarkFragment : Fragment() {

    lateinit var m:FragmentBookmarkBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        m = FragmentBookmarkBinding.inflate(inflater,container,false)

        m.recyclerviewBookmarkF.layoutManager = LinearLayoutManager(context)

        val data = ArrayList<BookmarkRecyclerVModel>()

        for (i in 1..20) {
            data.add(BookmarkRecyclerVModel(R.drawable.sample_food_image, "City/Food Name " + i))
        }

        val adapter = BookmarkAdapter(data)

        m.recyclerviewBookmarkF.adapter = adapter

        return m.root

    }

}