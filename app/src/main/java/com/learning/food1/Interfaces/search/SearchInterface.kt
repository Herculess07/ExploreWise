package com.learning.food1.Interfaces.search

import com.learning.food1.Model.search.SearchModel

interface SearchInterface {
    fun onSearchPlaceClicked(model: SearchModel, position: Int)

}