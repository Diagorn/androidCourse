package com.turing.androidcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * Активити с информацией о разработчике
 *
 * @author Diagorn
 */
class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}