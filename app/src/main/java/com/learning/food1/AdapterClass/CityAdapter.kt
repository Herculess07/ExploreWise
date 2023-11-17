package com.learning.food1.AdapterClass

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.food1.Model.FamousOfCityModel
import com.learning.food1.R
import com.learning.food1.databinding.BookmarkCardViewDesignBinding

//class HomeAdapter(val context : Context,private val mList: List<FamousOfCityModel>) :
class CityAdapter(
    private val mList: ArrayList<FamousOfCityModel>
) :
    RecyclerView.Adapter<CityAdapter.CityItemsBinding>() {

    // Holds the views for adding it to image and text


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemsBinding {
        val b = BookmarkCardViewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        val imageUrl = "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FDevotionalPlaces%2F$imgId?alt=media"
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.skeleton)
            .into(holder.b.imgTitle)

        holder.b.bookmarkIcon.setOnClickListener {
            if (model.bookmark) {
                holder.b.bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_border_24)
                model.bookmark = false
            } else {
                holder.b.bookmarkIcon.setImageResource(R.drawable.baseline_bookmark_24)
                model.bookmark = true
            }
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    class CityItemsBinding(var b:BookmarkCardViewDesignBinding):RecyclerView.ViewHolder(b.root)


}