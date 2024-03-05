package com.turing.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.turing.android.databinding.ActivityMainBinding
import com.turing.android.fragments.PersonListFragment

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
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerId, PersonListFragment.create())
            .commit()
    }
}