package com.learning.food1.Login

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.learning.food1.Main.MainActivity
import com.learning.food1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var bindingL: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingL = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindingL.root)

        auth = FirebaseAuth.getInstance()

    }

    override fun onStart() {
        super.onStart()
        bindingL.btnLogin.setOnClickListener {
            validPassword()
            loginUser()
        }

        bindingL.tvRedirectSignUp.setOnClickListener {
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

    // login function
    private fun loginUser() {
        val email = bindingL.etLoginEmailAddress.text.toString()
        val pass = bindingL.etLoginPassword.text.toString()
        val sharedPref = getSharedPreferences("com.learning.food1", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        // calling signInWithEmailAndPassword(email, pass)
        // function using Firebase auth object
        // On successful response Display a Toast

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
                if (checkForInternet(this)) {
                    if (it.isSuccessful ) {

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        try {
                            Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
                        }catch (e:Error){
                            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()

                        }
                    }
                } else {
                    Toast.makeText(this, "Internet Not Connected", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }


    }

    private fun validPassword(): String? {
        val passwordText = bindingL.etLoginPassword.text.toString()
        if(passwordText.length < 8) {
            return "Minimum 8 Character Password"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case Character"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case Character"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }

        return null
    }

}
