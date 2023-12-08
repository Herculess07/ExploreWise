package com.learning.food1.Main

import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

open class Validation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validateEdittext(editText: EditText, value: String): Boolean {
        return if (TextUtils.isEmpty(value)) {
            editText.error = "Field can't be empty"
            false
        } else if (value.length < 3) {
            editText.error = "too short"
            false
        } else true

    }

    fun validatePhone(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }

    fun validatePostalCode(postalCode: EditText, value: String): Boolean {
        val regex = "^[1-9][0-9]{5}\$"

        return if (value.isEmpty() || value.length > 6 || !value.matches(regex.toRegex())) {
            postalCode.error = "Invalid postal code"
            false
        } else {
            true
        }
    }

    fun validatePassword(password: EditText, value: String): Boolean {
        val regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$"
        // 1 uppercase, 1 lowercase, 1 number, 1 special character, minimum 8 characters

        return if (value.isEmpty() || !value.matches(regex.toRegex())) {
            password.error = "Invalid password"
            false
        } else {
            true
        }
    }


}