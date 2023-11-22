package com.learning.food1.Interfaces

import com.learning.food1.Model.FamousOfCityModel

interface FamousOfCityInterface {
    fun onFamousItemsOfCityClicked(model:FamousOfCityModel,position: Int)
    fun onBookmarkClicked(model:FamousOfCityModel,position: Int)
}