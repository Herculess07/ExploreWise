package com.learning.food1.AdapterClass.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learning.food1.Configs
import com.learning.food1.Interfaces.home.HomeInterface
import com.learning.food1.Model.home.ClassVisitedCitiesHome
import com.learning.food1.R


class HomeAdapter(
    /* private val rvDevInterface: rvdevInterface,*/

    private val mList: ArrayList<ClassVisitedCitiesHome>,
    private val cb: HomeInterface,
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    val config = Configs()
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val cityTitle: TextView = itemView.findViewById(R.id.txtCityName)
        var image: ImageView = itemView.findViewById(R.id.imgCityOrFood)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.feaatured_card_design_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = mList[position]
        holder.cityTitle.text = model.devotional_city
        /*holder.image= model.devotional_image!!*/
        holder.itemView.setOnClickListener {
            cb.onCityClicked(model, position)
        }
    }

    val limit = 10
    override fun getItemCount(): Int {
        return if (mList.size > limit) {
            limit;
        } else {
            mList.size;
        }

    }
}