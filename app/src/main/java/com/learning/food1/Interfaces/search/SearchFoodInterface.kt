package com.learning.food1.Interfaces.search

import com.learning.food1.Model.search.SearchFoodModel

interface SearchFoodInterface {
    fun onSearchFoodClicked(model: SearchFoodModel, position: Int)

}