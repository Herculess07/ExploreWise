package com.learning.food1.Interfaces

import com.learning.food1.Model.BookmarkedItems

interface BookmarkInterface {
    fun onBookmarkItemClicked(model:BookmarkedItems,position: Int)
}