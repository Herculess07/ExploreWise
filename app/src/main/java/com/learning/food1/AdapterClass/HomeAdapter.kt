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
import com.learning.food1.Model.ItemRecyclerVModel
import com.learning.food1.R

//class HomeAdapter(val context : Context,private val mList: List<ItemRecyclerVModel>) :
class HomeAdapter(private val mList: List<ItemRecyclerVModel>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewHomeDesign)
        val textView: TextView = itemView.findViewById(R.id.textViewHomeDesign)
//        val rootGroup: FrameLayout = itemView.findViewById(R.id.rootGroupFragmentHome)

    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_card_view_design, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        // sets the image to the imageview from our itemHolder class
        holder
            .imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder
            .textView.text = ItemsViewModel.text

//        holder.rootGroup.setOnClickListener(){
//            val intent = Intent(context,FamousItemsOfCityActivity::class.java)
//            intet.putExtra()
//        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


}
