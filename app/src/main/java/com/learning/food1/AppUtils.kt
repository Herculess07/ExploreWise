package com.learning.food1

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


class AppUtils {


    fun replaceFragment(fragmentManager: FragmentManager, fragment: Fragment, containerId: Int) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(containerId, fragment)
        fragmentTransaction.commit()
    }
//    object LocationConstants {
//        const val SUCCESS_RESULT = 0
//        const val FAILURE_RESULT = 1
//        const val PACKAGE_NAME = "your package name"
//        const val RECEIVER = PACKAGE_NAME + ".RECEIVER"
//        const val RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY"
//        const val LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA"
//        const val LOCATION_DATA_AREA = PACKAGE_NAME + ".LOCATION_DATA_AREA"
//        const val LOCATION_DATA_CITY = PACKAGE_NAME + ".LOCATION_DATA_CITY"
//        const val LOCATION_DATA_STREET = PACKAGE_NAME + ".LOCATION_DATA_STREET"
//    }
//
//
//    fun hasLollipop(): Boolean {
//        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
//    }
//
//    fun isLocationEnabled(context: Context): Boolean {
//        var locationMode = 0
//        val locationProviders: String
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            try {
//                locationMode = GoogleApi.Settings.Secure.getInt(
//                    context.getContentResolver(),
//                    Settings.Secure.LOCATION_MODE
//                )
//            } catch (e: SettingNotFoundException) {
//                e.printStackTrace()
//            }
//            locationMode != Settings.Secure.LOCATION_MODE_OFF
//        } else {
//            locationProviders = Settings.Secure.getString(
//                context.getContentResolver(),
//                Settings.Secure.LOCATION_PROVIDERS_ALLOWED
//            )
//            !TextUtils.isEmpty(locationProviders)
//        }
//    }
}