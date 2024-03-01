package com.turing.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.turing.android.databinding.ActivityMainBinding
import com.turing.android.fragments.PersonListFragment
import com.turing.android.utils.navigateToFragment

/**
 * Основное активити
 *
 * @author Diagorn
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Установка изначального экрана - списка активистов
        navigateToFragment(PersonListFragment.create())
    }
}