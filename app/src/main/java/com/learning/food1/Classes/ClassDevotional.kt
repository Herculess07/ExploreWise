package com.learning.food1.Classes

import android.widget.ImageView

data class ClassDevotional(
    // created getter and setter methods for all our variables.
    // string variable for storing
    val devPlaceID: String? = null,
    val devotional_name: String? = null,
    val devotional_about: String? = null,
    // val devotional_image: ImageView? = null,
    val devotional_image_name: String? = null,
    val devotional_area: String? = null,
    val devotional_additional_address_info: String? = null,
    val devotional_city: String? = null,
    val devotional_state: String? = null,
    val devotional_postal_code: String? = null,
    val devotional_contact_number: String? = null,
    val devotional_email_address: String? = null,
    val devotional_website_url: String? = null
) {}

