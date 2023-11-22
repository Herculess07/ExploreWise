package com.learning.food1.AdapterClass

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.learning.food1.Interfaces.FamousOfCityInterface
import com.learning.food1.Model.BookmarkedItems
import com.learning.food1.Model.FamousOfCityModel
import com.learning.food1.R
import com.learning.food1.databinding.BookmarkCardViewDesignBinding

//class HomeAdapter(val context : Context,private val mList: List<FamousOfCityModel>) :
class CityAdapter(
    val context: Context,
    private val mList: ArrayList<FamousOfCityModel>,
    private val cb: FamousOfCityInterface,

    ) :
    RecyclerView.Adapter<CityAdapter.CityItemsBinding>() {

    // Holds the views for adding it to image and text


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemsBinding {
        val b = BookmarkCardViewDesignBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CityItemsBinding(b)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: CityItemsBinding,
        position: Int,
    ) {
        val model = mList[position]

        holder.b.txtItemName.text = model.devotional_name
        holder.b.txtCityState.text = "${model.devotional_city}, ${model.devotional_state}"

        val imgId = model.devPlaceID
        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FDevotionalPlaces%2F$imgId?alt=media"
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.skeleton)
            .into(holder.b.imgTitle)

        holder.itemView.setOnClickListener {
            cb.onFamousItemsOfCityClicked(model, position)
        }

        holder.b.bookmarkIcon.setOnClickListener {
            if (model.bookmark) {
                cb.onBookmarkClicked(model, position)
                model.bookmark = false

                holder.b.bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_border_24)

            } else {
                val uid = FirebaseAuth.getInstance().uid

                model.bookmark = true

                holder.b.bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_24)
                try {
                    if (uid != null) {
                        addToBookmarks(
                            uid,
                            model.devPlaceID.toString(),
                            model.devotional_name.toString(),
                            model.devotional_city.toString(),
                            model.devotional_state.toString(),
                            model.bookmark
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun addToBookmarks(
        uid: String,
        devPlaceId: String,
        devotional_name: String,
        devotional_city: String,
        devotional_state: String,
        bookmark: Boolean,
    ) {
        val database = FirebaseDatabase.getInstance()
        val bookmarksRef = database.getReference("Users/Bookmarks/$uid")

        val bookmarkKey = bookmarksRef.push().key

        val bookmarkedPlace = BookmarkedItems(
            devPlaceID = devPlaceId,
            devotional_name = devotional_name,
            devotional_city = devotional_city,
            devotional_state = devotional_state,
            bookmark = bookmark,
        )

        bookmarkKey?.let {
            bookmarksRef.child(it).setValue(bookmarkedPlace)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class CityItemsBinding(var b: BookmarkCardViewDesignBinding) : RecyclerView.ViewHolder(b.root)


}