package com.turing.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.turing.android.databinding.ActivityMainBinding
import com.turing.android.fragments.PersonListFragment
import com.turing.android.ui.Navigator

/**
 * Основное активити
 *
 * @author Diagorn
 */
class MainActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Установка изначального экрана - списка активистов
        navigateToFragment(PersonListFragment.create())
    }

    override val containerId: Int
        get() = binding.fragmentContainerId.id

    override fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }
}