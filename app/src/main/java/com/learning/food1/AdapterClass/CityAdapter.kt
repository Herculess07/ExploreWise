package com.learning.food1.AdapterClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.learning.food1.Model.FamousOfCityModel
import com.learning.food1.R

//class HomeAdapter(val context : Context,private val mList: List<FamousOfCityModel>) :
class CityAdapter(
    options: FirebaseRecyclerOptions<FamousOfCityModel>
) :
    FirebaseRecyclerAdapter<FamousOfCityModel, CityAdapter.ViewHolder>(options) {

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewFamousOfCityDesign)
        val title: TextView = itemView.findViewById(R.id.titleFamousOfCityDesign)
        val description: TextView = itemView.findViewById(R.id.descriptionTextView)
        val bookmark: ImageView = itemView.findViewById(R.id.bookmarkFamousOfCityDesign)

    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_famous_item_of_city, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        model: FamousOfCityModel,
    ) {
        // holder.title.setText(model.image)
        holder.imageView.setImageResource(model.image)
        holder.title.text = model.devotional_name
        holder.description.text = model.devotional_about
        holder.bookmark.setOnClickListener {
            if (model.bookmark) {
                holder.bookmark.setImageResource(R.drawable.baseline_bookmark_border_24)
                model.bookmark = false
            } else {
                holder.bookmark.setImageResource(R.drawable.baseline_bookmark_24)
                model.bookmark = true
            }
        }
    }

    // return the number of the items in the list
   /* override fun getItemCount(): Int {
        return itemCount
    }*/


}