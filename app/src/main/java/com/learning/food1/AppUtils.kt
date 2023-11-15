package com.learning.food1

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment


class AppUtils {

    fun replaceFragment(activity: AppCompatActivity, fragment: Fragment, containerId: Int) {
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        transaction.addToBackStack(null) // Optional: Add to back stack for navigation
        transaction.commit()
    }
}