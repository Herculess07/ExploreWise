package com.learning.food1.BottomNavFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.learning.food1.AdapterClass.BookmarkAdapter
import com.learning.food1.Configs
import com.learning.food1.Interfaces.BookmarkInterface
import com.learning.food1.Main.DetailsActivity
import com.learning.food1.Model.bookmark.BookmarkedItems
import com.learning.food1.R
import com.learning.food1.databinding.FragmentBookmarkBinding


class BookmarkFragment : Fragment() {

    lateinit var m: FragmentBookmarkBinding
    lateinit var bookmarksRef: DatabaseReference
    lateinit var bookmarkList: ArrayList<BookmarkedItems>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        m = FragmentBookmarkBinding.inflate(inflater, container, false)
        init()
        return m.root

    }

    private fun init() {
        val database = FirebaseDatabase.getInstance()
        val uid = FirebaseAuth.getInstance().uid
        bookmarksRef = database.getReference("Users/Bookmarks/$uid")
        bookmarkList = ArrayList()
        initAdapter()
        getBookmarks()
        m.abBookmark.imgBookmark.visibility = View.GONE
        m.abBookmark.imgBack.visibility = View.GONE
        m.abBookmark.txtTitle.text = getString(R.string.bookmarks)
    }


    private fun initAdapter() {
        m.rvBookmark.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        m.rvBookmark.setHasFixedSize(true)

    }

    private fun getBookmarks() {
        val config = Configs()
        bookmarksRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (activity != null && !requireActivity().isDestroyed && !requireActivity().isFinishing) {
                    if (snapshot.exists()) {
                        for (childSnapshot in snapshot.children) {

                            val bookmarkedPlace =
                                childSnapshot.getValue(BookmarkedItems::class.java)
                            bookmarkList.add(bookmarkedPlace!!)

                        }

                        bookmarkList.reverse()

                        val itemsAdapter = BookmarkAdapter(requireContext(), bookmarkList,
                            object : BookmarkInterface {
                                override fun onBookmarkItemClicked(
                                    model: BookmarkedItems,
                                    position: Int,
                                ) {
                                    val i = Intent(
                                        requireContext(),
                                        DetailsActivity::class.java
                                    )
                                    i.putExtra(config.DEVOTION_ID, model.devPlaceID)
                                    startActivity(i)
                                }
                            })
                        m.rvBookmark.adapter = itemsAdapter
                        itemsAdapter.notifyItemInserted(bookmarkList.size - 1)
                    } else {
                        Toast.makeText(requireContext(), "No Bookmark added", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}