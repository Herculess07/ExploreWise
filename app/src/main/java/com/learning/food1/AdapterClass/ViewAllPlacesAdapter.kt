package com.learning.food1.AdapterClass

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.food1.Interfaces.ViewAllInterface
import com.learning.food1.Model.ViewAllModel
import com.learning.food1.R
import com.learning.food1.databinding.BookmarkCardViewDesignBinding

class ViewAllPlacesAdapter(
    val context: Context,
    private val model: ArrayList<ViewAllModel>,
    private val cb: ViewAllInterface,
) :
    RecyclerView.Adapter<ViewAllPlacesAdapter.ViewAllBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllBinding {
        val b = BookmarkCardViewDesignBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewAllBinding(b)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewAllBinding, position: Int) {
        val model = model[position]

        holder.b.txtItemName.text = model.place_name
        holder.b.txtCityState.text = "${model.place_city}, ${model.place_state}"

        val imgId = model.famPlaceID
        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FFamousPlaces%2F$imgId?alt=media"

        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.skeleton)
            .into(holder.b.imgTitle)

        holder.itemView.setOnClickListener {
            cb.onViewAllPlaceClicked(model, position)

        }


    }

    override fun getItemCount(): Int {
        return model.size
    }

    // Holds the views for adding it to image and text
    class ViewAllBinding(var b: BookmarkCardViewDesignBinding) : RecyclerView.ViewHolder(b.root)

}