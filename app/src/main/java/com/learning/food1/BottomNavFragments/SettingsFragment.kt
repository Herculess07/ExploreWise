package com.learning.food1.BottomNavFragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.learning.food1.R
import com.learning.food1.databinding.FragmentSettingsBinding

class SettingsFragment : PreferenceFragmentCompat() {

    lateinit var m: FragmentSettingsBinding
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

   /* override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        m = FragmentSettingsBinding.inflate(inflater, container, true)

        return m.root
    }*/
}