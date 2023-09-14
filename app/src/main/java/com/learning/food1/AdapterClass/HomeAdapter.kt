package com.learning.food1.AdapterClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.learning.food1.BottomNavFragments.HomeFragment
import com.learning.food1.Classes.ClassDevotional
import com.learning.food1.R

class HomeAdapter(
    private val mList: ArrayList<ClassDevotional>,
    private val context: HomeFragment,
) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


//    class HomeAdapter(
//        private val mList: ArrayList<ClassDevotional>,
//        private val context: HomeFragment,
//    ) : FirebaseRecyclerAdapter<ClassDevotional,RecyclerView.ViewHolder>
//        {


    private var onListener: OnClickListener? = null

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageViewHomeDesign)
        val textView: TextView = itemView.findViewById(R.id.textViewHomeDesign)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_card_view_design, parent, false)

//        val b = HomeCardViewDesignBinding.inflate(
//            LayoutInflater.from(parent.context),
//            parent, false
//        )

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Items = mList[position]

//        Glide.with(context).load(Items.devotional_image).into(holder.imageView)
        holder.textView.text = Items.devotional_city

        holder.itemView.setOnClickListener {
            Snackbar.make(it, "You clicked on ${Items.devotional_city}", Snackbar.LENGTH_SHORT)
                .show()

            if (onListener != null) {
                onListener!!.onClick(position, Items)
            }
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: ClassDevotional)

    }

}
