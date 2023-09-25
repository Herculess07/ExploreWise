package com.learning.food1.AdapterClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learning.food1.Classes.ClassDevotional
import com.learning.food1.R

class DeliciousDelightsHomeAdapter(private val mList: List<ClassDevotional>) :
    RecyclerView.Adapter<DeliciousDelightsHomeAdapter.ViewHolder>() {

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textViewHomeDesign)
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.feaatured_card_design_home, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val Items = mList[position]

//        holder.imageView.setImageResource(ItemsViewModel.image)
        holder.textView.text = Items.devotional_name

    }

    override fun getItemCount(): Int {
        return mList.size
    }

}
