package com.learning.food1.AdapterClass.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.food1.Interfaces.home.HomeInterface
import com.learning.food1.Model.home.ClassVisitedCitiesHome
import com.learning.food1.R
import com.learning.food1.databinding.ItemCitiesCardBinding


class HomeAdapter(
    /* private val rvDevInterface: rvdevInterface,*/

    private val mList: ArrayList<ClassVisitedCitiesHome>,
    private val cb: HomeInterface,
) :
    RecyclerView.Adapter<HomeAdapter.HomeBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBinding {
        val b = ItemCitiesCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeBinding(b)
    }

    override fun onBindViewHolder(holder: HomeBinding, position: Int) {
        val model = mList[position]
        holder.b.txtCity!!.text = model.devotional_city
        holder.b.txtState!!.text = model.devotional_state

        val imgId = model.devPlaceID
        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FDevotionalPlaces%2F$imgId?alt=media"

        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .placeholder(R.drawable.skeleton)
            .into(holder.b.imgCity)

        /*holder.image= model.devotional_image!!*/
        holder.itemView.setOnClickListener {
            cb.onCityClicked(model, position)
        }
    }


    override fun getItemCount(): Int {
        return mList.size - 1
    }

    class HomeBinding(var b: ItemCitiesCardBinding) :
        RecyclerView.ViewHolder(b.root)

}