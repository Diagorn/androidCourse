package com.turing.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Основное активити
 *
 * @author Diagorn
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ClickerFragment.create())
            .commit()
    }
}