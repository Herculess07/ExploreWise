package com.learning.food1.AdapterClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.learning.food1.Classes.ClassDevotional
import com.learning.food1.R

class HomeAdapter(
    private val context: android.content.Context,
    private val mList: ArrayList<ClassDevotional>,
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(cityId: String?)
    }

    private var onListener: OnItemClickListener? = null


    fun setOnItemClickListener(onClickListener: OnItemClickListener) {
        this.onListener = onClickListener
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        //        val imageView: ImageView = itemView.findViewById(R.id.imageViewHomeDesign)
        val textView: TextView = itemView.findViewById(R.id.textViewHomeDesign)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.feaatured_card_design_home, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Items = mList[position]
        // Glide.with(context).load(Items.devotional_image).into(holder.imageView)
        holder.textView.text = Items.devotional_city
        holder.itemView.setOnClickListener {
            Snackbar.make(it, "You clicked on ${Items.devotional_city}", Snackbar.LENGTH_SHORT)
                .show()
            if (onListener != null) {
                onListener!!.onItemClick(Items.devotional_city)
                onListener!!.onItemClick(Items.devPlaceID)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
