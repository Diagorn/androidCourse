package com.turing.androidcourse.button_handlers

import android.content.Context
import android.widget.EditText
import android.widget.TextView

/**
 * Обработчик события клика на кнопку сложения
 *
 * @author Diagorn
 */
class PlusButtonHandler(
    editTexts: List<EditText>,
    resultTextView: TextView,
    context: Context,
) : AbstractButtonHandler(editTexts, resultTextView, context) {

    override fun doHandle() {
        editTexts.forEach { editText ->
            val editTextValue = editText.text.toString().toDouble()
            operationResult += editTextValue
        }
    }
}