package com.turing.androidcourse.button_handlers

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService

/**
 * Обработчик кнопки калькулятора
 *
 * @param editTexts - текстовые инпуты, с которых берется значение
 * @param resultTextView - вью на отображение результата операции
 * @param context - контекст активити калькулятора
 *
 * @author Diagorn
 */
abstract class AbstractButtonHandler(
    val editTexts: List<EditText>,
    private val resultTextView: TextView,
    protected val context: Context
) {
    /**
     * Переменная подсчета результата операции
     */
    protected var operationResult: Double = 0.0

    /**
     * Обработка клика
     * Производится проверка на пустоту полей, если все хорошо - считается значение
     * и отображается в resultTextView
     */
    fun handle() {
        editTexts.forEach {
            if (it.text == null || it.text.isEmpty()) {
                makeToast("Соси жопу и введи что-то ебень")
                return
            }

            disableKeyboard(it)
        }

        resultTextView.requestFocus()
        doHandle()
        resultTextView.text = operationResult.toString()
        operationResult = 0.0
    }

    /**
     * Отобразить сообщение об ошибке
     * @param message - текст сообщения
     */
    protected fun makeToast(message: String) {
        Toast
            .makeText(context, message, Toast.LENGTH_SHORT)
            .show()
    }

    /**
     * Непосредственная логика обработки клика
     */
    protected abstract fun doHandle()

    /**
     * Отключить клавиатуру инпута и снять с него фокус
     * @param editText - инпут, с которым работаем
     */
    private fun disableKeyboard(editText: EditText) {
        editText.clearFocus()
        val imm = getSystemService(context, InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}