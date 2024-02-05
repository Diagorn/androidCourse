package com.turing.androidcourse.button_handlers

import android.content.Context
import android.widget.EditText
import android.widget.TextView

/**
 * Обработчик события клика на кнопку умножения
 *
 * @author Diagorn
 */
class MultiplyButtonHandler(
    editTexts: List<EditText>,
    resultTextView: TextView,
    context: Context,
) : AbstractButtonHandler(editTexts, resultTextView, context) {

    override fun doHandle() {
        operationResult = 1.0

        editTexts.forEach { editText ->
            val editTextValue = editText.text.toString().toDouble()
            operationResult *= editTextValue
        }
    }
}