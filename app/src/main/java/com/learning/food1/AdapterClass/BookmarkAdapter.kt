package com.learning.food1.AdapterClass

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.food1.Configs
import com.learning.food1.Interfaces.BookmarkInterface
import com.learning.food1.Model.BookmarkedItems
import com.learning.food1.R

class BookmarkAdapter(
    private val context : Context,
    private val mList: List<BookmarkedItems>,
    private val cb : BookmarkInterface
) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bookmark_card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = mList[position]
        // holder.imageView.setImageResource(model.devotional_city)
        val config = Configs()
        val cityId = model.devPlaceID
        holder.itemName.text = model.devotional_name
        holder.cityName.text = "${model.devotional_city}, ${model.devotional_state}"
        holder.itemView.setOnClickListener {
            cb.onBookmarkItemClicked(model,position)
        }

        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FDevotionalPlaces%2F$cityId?alt=media"


        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.skeleton)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = this.itemView.findViewById(R.id.imgTitle)
        val itemName: TextView = this.itemView.findViewById(R.id.txtItemName)
        val cityName: TextView = this.itemView.findViewById(R.id.txtCityState)
    }
}