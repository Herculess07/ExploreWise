package com.learning.food1.Model

class FamousOfCityModel(
    val itemPlaceId:String,
    val image: Int,
    val devotional_name: String? = null,
    val devotional_about: String? = null,
    var bookmark:Boolean,
) : java.io.Serializable