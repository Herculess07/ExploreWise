package com.learning.food1

import android.app.Application

open class Configs : Application() {

    val TYPE_0 = "0"
    val TYPE_1 = "1"
    val TYPE_2 = "2"
    val VERTICAL = 1
    val HORIZONTAL = 0

    val FOOD_ID = "id"
    val PLACE_ID = "id"
    val DEVOTION_ID = "id"
    val KEY_ID = "id"
    val KEY_FOOD = "FamousFood"
    val KEY_DEVOTION = "DevotionalPlace"
    val KEY_PLACE = "FamousPlace"
    val KEY_CITY = "FamousCity"
    val KEY_STATE = "FamousState"
    val KEY_BOOKMARK = "Bookmark"
    val PATH_FAMOUS_PLACES = "Users/FamousPlaces"
    val PATH_FAMOUS_FOOD = "Users/FamousFood"
    val PATH_DEVOTION_PLACES = "Users/DevotionalPlaces"
    val BASE_IMAGE_URL = "https://firebasestorage.googleapis.com/v0/b/food-project-395207.appspot.com/o/Users%2F"

}