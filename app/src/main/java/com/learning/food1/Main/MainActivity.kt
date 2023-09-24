package com.learning.food1.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.learning.food1.AppUtils
import com.learning.food1.BottomNavFragments.BookmarkFragment
import com.learning.food1.BottomNavFragments.HomeFragment
import com.learning.food1.BottomNavFragments.SettingsFragment
import com.learning.food1.Login.LoginActivity
import com.learning.food1.R
import com.learning.food1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fab: FloatingActionButton
    private val appUtils = AppUtils()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) //For night mode theme


        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        auth = FirebaseAuth.getInstance()
        checkLoginStatus()

        b.botNavView.background
        b.botNavView.menu.getItem(2).isEnabled = false


    }

    override fun onStart() {
        super.onStart()
        appUtils.replaceFragment(supportFragmentManager,HomeFragment(),R.id.frameLayout)
        fragment()
        userPermissionAccess()

    }

    private fun fragment() {
        // Change fragment on click bottom navigation bar icon
        b.botNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.btmIconExplore -> appUtils.replaceFragment(supportFragmentManager,HomeFragment(),R.id.frameLayout)
                R.id.btmIconBookmark -> appUtils.replaceFragment(supportFragmentManager,BookmarkFragment(),R.id.frameLayout)
                R.id.btmIconUserSettings -> appUtils.replaceFragment(supportFragmentManager,SettingsFragment(),R.id.frameLayout)

                else -> {
                    appUtils.replaceFragment(supportFragmentManager,HomeFragment(),R.id.frameLayout)
                }
            }
            true
        }
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

    private fun userPermissionAccess() {

        val permissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(android.Manifest.permission.CAMERA, false) -> {
                    Log.i("Permission", "Camera Permission Granted")
                }

                permissions.getOrDefault(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE, false
                ) -> {
                    Log.i("Permission", "Storage Permission Granted")
                }

                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, false
                ) -> {
                    // Only approximate location access granted.
                    Log.i("Permission", "Approximate Location Permission Granted")
                }

                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, false
                ) -> {
                    // Only approximate location access granted.
                    Log.i("Permission", "Precise Location Permission Granted")
                }

                else -> {
                    // No location access granted.
                    Log.i("Permission", "No Permission Granted")
                }
            }
        }

        permissionRequest.launch(
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
}