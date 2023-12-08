package com.learning.food1.AdapterClass

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.food1.Interfaces.FamousOfCityInterface
import com.learning.food1.Model.FamousOfCityModel
import com.learning.food1.R
import com.learning.food1.databinding.BookmarkCardViewDesignBinding

class CityAdapter(
    val context: Context,
    private val mList: ArrayList<FamousOfCityModel>,
    private val cb: FamousOfCityInterface,

    ) :
    RecyclerView.Adapter<CityAdapter.CityItemsBinding>() {

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

        val initialBookmarkIcon =
            if (model.bookmark) R.drawable.baseline_bookmark_24
            else R.drawable.baseline_bookmark_border_24
        holder.b.bookmarkIcon.setImageResource(initialBookmarkIcon)

        holder.b.bookmarkIcon.setOnClickListener {
            // Toggle bookmark status
            model.bookmark = !model.bookmark

            // Update UI immediately
            /*val updatedBookmarkIcon =
                if (model.bookmark) R.drawable.baseline_bookmark_24
                else R.drawable.baseline_bookmark_border_24
            holder.b.bookmarkIcon.setImageResource(updatedBookmarkIcon)

            // Asynchronously handle background tasks (e.g., save to Firebase)
            val uid = FirebaseAuth.getInstance().uid
            if (uid != null) {
                addToBookmarks(
                    uid,
                    model.devPlaceID.toString(),
                    model.devotional_name.toString(),
                    model.devotional_city.toString(),
                    model.devotional_state.toString(),
                    model.bookmark
                )
            }*/
        }
    }


   /* private fun addToBookmarks(
        uid: String,
        devPlaceId: String,
        devotional_name: String,
        devotional_city: String,
        devotional_state: String,
        bookmark: Boolean
    ) {
        val database = FirebaseDatabase.getInstance()
        val bookmarksRef = database.getReference("Users/Bookmarks/$uid")

        val bookmarkedPlace = BookmarkedItems(
            devPlaceID = devPlaceId,
            devotional_name = devotional_name,
            devotional_city = devotional_city,
            devotional_state = devotional_state,
            bookmark = bookmark,
        )

        bookmarksRef.child(devPlaceId).setValue(bookmarkedPlace)
    }
*/
    override fun getItemCount(): Int {
        return mList.size
    }

    class CityItemsBinding(var b: BookmarkCardViewDesignBinding) : RecyclerView.ViewHolder(b.root)


}