package com.learning.food1.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.learning.food1.Main.MainActivity
import com.learning.food1.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    // create Firebase authentication object
    private lateinit var bindingS: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Binding Views
        bindingS = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(bindingS.root)

        // Initialising auth object
        auth = FirebaseAuth.getInstance()
        init()
    }

    private fun init() {
        super.onStart()
        bindingS.btnSSignUp.setOnClickListener {
            signUpUser()
        }

        // switching from signUp Activity to Login Activity
        bindingS.tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signUpUser() {
        val email = bindingS.etSEmailAddress.text.toString()
        val pass = bindingS.etSPassword.text.toString()
        val confirmPass = bindingS.etSConfPassword.text.toString()

        if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
            if (pass == confirmPass) {

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        // if signup success rendering to login
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                        Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                return
            } else {
                Toast.makeText(
                    this,
                    "Password is not matching",
                    Toast.LENGTH_SHORT
                ).show()

            }
        } else {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            return
        }

    }
}