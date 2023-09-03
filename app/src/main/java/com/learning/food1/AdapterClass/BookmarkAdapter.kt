package com.learning.food1.AdapterClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.learning.food1.Model.BookmarkRecyclerVModel
import com.learning.food1.R

class BookmarkAdapter(private val mList: List<BookmarkRecyclerVModel>) : RecyclerView.Adapter<BookmarkAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bookmark_card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val bookmarkViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(bookmarkViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = bookmarkViewModel.text

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = this.itemView.findViewById(R.id.imageViewBookmarkDesign)
        val textView: TextView = this.itemView.findViewById(R.id.textViewBookmarkDesign)
    }
}