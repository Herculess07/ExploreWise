package com.learning.food1.Login

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.learning.food1.Configs
import com.learning.food1.Main.MainActivity
import com.learning.food1.Main.Validation
import com.learning.food1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var m: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val c = Configs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(m.root)

        auth = FirebaseAuth.getInstance()
        init()
    }

    private fun init() {
        m.btnLogin.setOnClickListener {

            loginUser()
        }

        m.tvRedirectSignUp.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (checkForInternet(this)) {
            Toast.makeText(this, "Internet Connected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Internet not Connected", Toast.LENGTH_SHORT).show()
        }

    }

    // checking for internet connection
    private fun checkForInternet(context: Context): Boolean {
        val connectManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectManager.activeNetwork ?: return false
        val activeNetwork = connectManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

            else -> false
        }
    }

    private fun storeDataInSharedPref() {
        if (!isFinishing && !isDestroyed) {
            val userId = auth.currentUser?.uid
            val sharedPref = getSharedPreferences(c.PREF, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(c.KEY_EMAIL, m.etLoginEmailAddress.text.toString())
            editor.putString(c.USER_ID, userId)
            editor.apply()
            Toast.makeText(this, "Login Data stored in shared pref", Toast.LENGTH_SHORT).show()
        }
    }

    val valid = Validation()

    // login function
    private fun loginUser() {
        val email = m.etLoginEmailAddress.text.toString()
        val pass = m.etLoginPassword.text.toString()

        // Validate email
        val vEmail = valid.validateEmail(email)

        if (vEmail && pass.isNotEmpty() && validPassword() != null) {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
                if (checkForInternet(this)) {
                    if (task.isSuccessful) {
                        val isValidPassword = validPassword()
                        if (isValidPassword != null) {
                            storeDataInSharedPref()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Invalid password after login", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        try {
                            Toast.makeText(
                                this,
                                "Login failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        } catch (e: Error) {
                            Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Internet Not Connected", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }
    }


    private fun validPassword(): String? {
        val passwordText = m.etLoginPassword.text.toString()
        if (passwordText.length < 8) {
            return "Minimum 8 Character Password"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case Character"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case Character"
        }
        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }

        return null
    }

}