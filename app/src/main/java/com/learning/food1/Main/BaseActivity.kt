package com.learning.food1.Main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler { _, paramThrowable ->
            Log.e(
                "Error" + Thread.currentThread().stackTrace[2],
                paramThrowable.localizedMessage!!
            )
        }
    }
}