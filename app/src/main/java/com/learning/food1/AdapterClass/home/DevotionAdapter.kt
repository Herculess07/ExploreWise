package com.learning.food1.AdapterClass.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.food1.Interfaces.home.DevotionInterface
import com.learning.food1.Model.home.Devotion
import com.learning.food1.R
import com.learning.food1.databinding.FeaaturedCardDesignHomeBinding
class DevotionAdapter(
    val context: Context,
    var mList: ArrayList<Devotion>,
    var cb: DevotionInterface,
) : RecyclerView.Adapter<DevotionAdapter.DevotionBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevotionBinding {
        val b = FeaaturedCardDesignHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DevotionBinding(b)
    }

    override fun onBindViewHolder(holder: DevotionBinding, position: Int) {
        val model = mList[position]
        holder.b.txtCityName.text = model.devotional_name

        val imgId = model.devPlaceID
        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FDevotionalPlaces%2F$imgId?alt=media"

        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.skeleton)
            .into(holder.b.imgCityOrFood)

        holder.itemView.setOnClickListener {
            cb.onDevPlaceClicked(model, position)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= mList.size) 0 else 1
    }


    override fun getItemCount(): Int {
        return mList.size - 1
    }


    class DevotionBinding(var b: FeaaturedCardDesignHomeBinding) :
        RecyclerView.ViewHolder(b.root)

}