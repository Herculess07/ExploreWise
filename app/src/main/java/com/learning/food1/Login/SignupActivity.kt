package com.learning.food1.Login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.learning.food1.Configs
import com.learning.food1.Main.MainActivity
import com.learning.food1.Main.Validation
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

    private fun storeDataInSharedPref() {
        val c = Configs()
        if (!isFinishing && !isDestroyed) {
            val userId = auth.currentUser?.uid
            val sharedPref = getSharedPreferences(c.PREF, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(c.KEY_EMAIL, bindingS.etSEmailAddress.text.toString())
            editor.putString(c.USER_ID, userId)
            editor.apply()
            Toast.makeText(this, "Signup Data stored in shared pref", Toast.LENGTH_SHORT).show()
        }
    }


val valid = Validation()
    private fun signUpUser() {
        val email = bindingS.etSEmailAddress.text.toString()
        val pass = bindingS.etSPassword.text.toString()
        val confirmPass = bindingS.etSConfPassword.text.toString()

        val vEmail = valid.validateEmail(email)

        if (vEmail && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
            if (pass == confirmPass) {
                auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            storeDataInSharedPref()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                            Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            if (task.exception is FirebaseAuthUserCollisionException) {
                                Toast.makeText(
                                    this,
                                    "User with this email already exists",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                // Other errors during user creation
                                Toast.makeText(
                                    this,
                                    task.exception?.message ?: "User creation failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

            } else {
                bindingS.etSPassword.error = "Password is not matching"
                bindingS.etSConfPassword.error = "Password is not matching"
                Toast.makeText(
                    this,
                    "Password is not matching",
                    Toast.LENGTH_SHORT
                ).show()

            }
        } else {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
        }
    }
}