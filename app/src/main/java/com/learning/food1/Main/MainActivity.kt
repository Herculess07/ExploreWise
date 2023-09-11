package com.learning.food1.Main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
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

    private lateinit var b: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var fab: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) //For night mode theme

        setContentView(R.layout.activity_main)

        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        auth = FirebaseAuth.getInstance()
        checkLoginStatus()

        b.botNavView.background
        b.botNavView.menu.getItem(2).isEnabled = false


    }

    override fun onStart() {
        super.onStart()
        replaceFragment(HomeFragment())
        fragment()
        userPermissionAccess()
        fabClick()
    }

    private fun fragment() {
        // Change fragment on click bottom navigation bar icon
        b.botNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.btmIconHome -> replaceFragment(HomeFragment())
                R.id.btmIconLocation -> replaceFragment(MapsFragment())
                R.id.btmIconBookmark -> replaceFragment(BookmarkFragment())
                R.id.btmIconUserSettings -> replaceFragment(SettingsFragment())

                else -> {
                    replaceFragment(HomeFragment())
                }
            }
            true
        }
    }

    fun fabClick() {
        b.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                .setAction("Action", null)
//                .show()
            replaceFragment(AddFragment())
        }
    }

    // private fun for change fragment
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
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