package com.learning.food1.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.learning.food1.AppUtils
import com.learning.food1.BottomNavFragments.AddFragment
import com.learning.food1.BottomNavFragments.BookmarkFragment
import com.learning.food1.BottomNavFragments.HomeFragment
import com.learning.food1.BottomNavFragments.SettingsFragment
import com.learning.food1.Login.LoginActivity
import com.learning.food1.R
import com.learning.food1.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : BaseActivity() {

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
        b.botNavView.menu.getItem(2).isEnabled = true
        init()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showDialog()
        }
    }

    private fun showDialog() {
        val dialogM = MaterialAlertDialogBuilder(this)

        dialogM.setTitle("Exit")
        dialogM.setMessage("Are You Sure to Want to Exit ?")
        dialogM.setCancelable(true)
        dialogM.setIcon(R.drawable.baseline_exit_to_app_24)
        dialogM.setPositiveButton("Yes") { _, _ ->
            finish()
            exitProcess(0)
        }
        dialogM.setNegativeButton("No") { _, _ ->

        }

        dialogM.show()

        /*val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Exit")
        dialog.setMessage("Are You Sure to Want to Exit ?")
        dialog.setCancelable(true)
        dialog.setIcon(R.drawable.baseline_exit_to_app_24)
        dialog.setPositiveButton("Yes") { _, _ ->
            finish()
            exitProcess(0)
        }
        dialog.setNegativeButton("No") { _, _ ->

        }
        dialog.show()*/

    }


    private fun init() {
        appUtils.replaceFragment(this, HomeFragment(), R.id.frameLayout)
        fragment()
        userPermissionAccess()
        b.imgAdd.setOnClickListener {
            val bottomSheet: BottomSheetDialogFragment = AddFragment()

            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

    }


    private fun fragment() {
        // Change fragment on click bottom navigation bar icon
        b.botNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.btmIconExplore -> appUtils.replaceFragment(
                    this,
                    HomeFragment(),
                    R.id.frameLayout
                )

                R.id.btmIconBookmark -> appUtils.replaceFragment(
                    this,
                    BookmarkFragment(),
                    R.id.frameLayout
                )

                R.id.btmIconUserSettings -> appUtils.replaceFragment(
                    this,
                    SettingsFragment(),
                    R.id.frameLayout
                )

                else -> {
                    appUtils.replaceFragment(this, HomeFragment(), R.id.frameLayout)
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
                    android.Manifest.permission.ACCESS_FINE_LOCATION, false
                ) -> {
                    Log.i("Permission", "Approximate Location Permission Granted")
                }

                permissions.getOrDefault(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, false
                ) -> {
                    Log.i("Permission", "Precise Location Permission Granted")
                }

                else -> {
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