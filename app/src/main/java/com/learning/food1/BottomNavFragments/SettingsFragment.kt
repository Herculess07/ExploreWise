package com.learning.food1.BottomNavFragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.learning.food1.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}