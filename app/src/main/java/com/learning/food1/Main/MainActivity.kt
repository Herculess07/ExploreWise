package com.learning.food1.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.learning.food1.BottomNavFragments.AddFragment
import com.learning.food1.BottomNavFragments.BookmarkFragment
import com.learning.food1.BottomNavFragments.HomeFragment
import com.learning.food1.BottomNavFragments.MapsFragment
import com.learning.food1.BottomNavFragments.SettingsFragment
import com.learning.food1.Login.LoginActivity
import com.learning.food1.R
import com.learning.food1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES); //For night mode theme
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) //For day mode theme

        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        checkLoginStatus()
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(HomeFragment())
        fragment()
    }

    private fun checkLoginStatus() {
        // Check if the user is already signed in.
        if (auth.currentUser != null) {
            // The user is already signed in, so redirect them to the home screen.
        } else {
            // The user is not signed in, so show the login screen.
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fragment() {
        // Change fragment on click bottom navigation bar icon
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.btmIconHome -> replaceFragment(HomeFragment())
                R.id.btmIconLocation -> replaceFragment(MapsFragment())
                R.id.btmIconAddFood -> replaceFragment(AddFragment())
                R.id.btmIconBookmark -> replaceFragment(BookmarkFragment())
                R.id.btmIconUserSettings -> replaceFragment(SettingsFragment())

                else -> {
                }
            }
            true
        }
    }

    // private fun for change fragment
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

}