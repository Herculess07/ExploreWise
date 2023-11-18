package com.learning.food1.Interfaces.home

import com.learning.food1.Model.home.Food

interface FoodInterface {
    fun onFoodClicked(model: Food, position: Int)
}