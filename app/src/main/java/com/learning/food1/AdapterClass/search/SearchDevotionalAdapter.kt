package com.learning.food1.AdapterClass.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.learning.food1.Interfaces.search.SearchDevotionalInterface
import com.learning.food1.Model.search.SearchDevotionalModel
import com.learning.food1.R
import com.learning.food1.databinding.ItemSearchResultBinding

class SearchDevotionalAdapter(
    private val context: Context,
    private val mList: ArrayList<SearchDevotionalModel>,
    private val cb: SearchDevotionalInterface,
) :
    RecyclerView.Adapter<SearchDevotionalAdapter.SearchBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBinding {
        val b = ItemSearchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchBinding(b)
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: SearchBinding,
        position: Int,
    ) {
        val model = mList[position]
        holder.b.searchTitle.text = model.devotional_name
        holder.b.searchAddress.text = model.devotional_city + ", " + model.devotional_state

        holder.itemView.setOnClickListener {
            cb.onSearchDevClicked(model,position)
        }

        val imgId = model.devPlaceID

        val imageUrl = "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FDevotionalPlaces%2F$imgId?alt=media"

        Glide.with(context)
            .load(imageUrl)
            .thumbnail(0.3f)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.skeleton)
            .into(holder.b.searchImage)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class SearchBinding(var b: ItemSearchResultBinding) : RecyclerView.ViewHolder(b.root)


}