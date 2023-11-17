package com.learning.food1.AdapterClass.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.food1.Configs
import com.learning.food1.Interfaces.home.PlacesInterface
import com.learning.food1.Model.home.Places
import com.learning.food1.R

class PlacesAdapter(
    val context: Context,
    private val model: ArrayList<Places>,
    val cb: PlacesInterface,
) :
    RecyclerView.Adapter<PlacesAdapter.ViewHolder>() {
    val config = Configs()

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        // val imageView: ImageView = itemView.findViewById(R.id.imageViewHomeDesign)
        val cityTitle: TextView = itemView.findViewById(R.id.txtCityName)
        var image: ImageView = itemView.findViewById(R.id.imgCityOrFood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.feaatured_card_design_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = model[position]
        holder.cityTitle.text = model.place_name
        val imgId = model.famPlaceID
        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FFamousPlaces%2F$imgId?alt=media"

        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.skeleton)
            .into(holder.image)


        holder.itemView.setOnClickListener {
            cb.onPlaceClicked(model, position)

        }
    }
    
    val limit = 10
    override fun getItemCount(): Int {

        return if (model.size > limit) {
            limit;
        } else {
            model.size;
        }

    }

}