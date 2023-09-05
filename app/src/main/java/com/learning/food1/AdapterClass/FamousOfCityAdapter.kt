package com.learning.food1.AdapterClass

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learning.food1.Main.FamousItemsOfCityActivity
import com.learning.food1.Model.FamousOfCityModel
import com.learning.food1.R

//class HomeAdapter(val context : Context,private val mList: List<FamousOfCityModel>) :
class FamousOfCityAdapter(private val mList: List<FamousOfCityModel>) :
    RecyclerView.Adapter<FamousOfCityAdapter.ViewHolder>() {

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewFamousOfCityDesign)
        val textView: TextView = itemView.findViewById(R.id.textViewFamousOfCityDesign)

    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.famous_items_of_city_card_view_design, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        // sets the image to the imageview and textview from our itemHolder class
        holder
            .imageView.setImageResource(ItemsViewModel.image)
        holder
            .textView.text = ItemsViewModel.text
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


}