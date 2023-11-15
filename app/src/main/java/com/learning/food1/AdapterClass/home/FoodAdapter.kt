package com.learning.food1.AdapterClass.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.food1.Configs
import com.learning.food1.Interfaces.home.FoodInterface
import com.learning.food1.Model.home.Food
import com.learning.food1.databinding.FeaaturedCardDesignHomeBinding

class FoodAdapter(
    val context: Context,
    private val mList: ArrayList<Food>,
    private val cb: FoodInterface,
) :
    RecyclerView.Adapter<FoodAdapter.FoodBinding>() {
    val config = Configs()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodBinding {
        val b = FeaaturedCardDesignHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodBinding(b)
    }

    override fun onBindViewHolder(holder: FoodBinding, position: Int) {
        val model = mList[position]
        holder.b.txtCityName.text = model.food_name

        val imgId = model.famFoodID
        val imageUrl =
            "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2FFamousFood%2F$imgId?alt=media"

        Glide.with(context)
            .load(imageUrl)
            .placeholder(com.learning.food1.R.drawable.skeleton)
            .into(holder.b.imgCityOrFood)

        holder.itemView.setOnClickListener {
            cb.onFoodClicked(model, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position >= mList.size) 0 else 1
    }


    override fun getItemCount(): Int {
        return mList.size
    }


    class FoodBinding(var b: FeaaturedCardDesignHomeBinding) :
        RecyclerView.ViewHolder(b.root)
}