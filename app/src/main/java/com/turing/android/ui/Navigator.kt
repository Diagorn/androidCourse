package com.turing.android.ui

import androidx.fragment.app.Fragment

/**
 * Интерфейс для активити, позволяющий менять отображаемый фрагмент
 *
 * @author Diagorn
 */
interface Navigator {
    /**
     * Ид контейнера фрагмента
     */
    val containerId: Int

    /**
     * Метод отображения необходимого фрагмента
     */
    fun navigateToFragment(fragment: Fragment)
}