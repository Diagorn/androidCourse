package com.turing.android.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.turing.android.R

/**
 * Перейти к указанному фрагменту
 *
 * @param fragment - фрагмент, который необходимо отобразить
 */
fun FragmentActivity.navigateToFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.fragmentContainerId, fragment)
        .commitNow()
}