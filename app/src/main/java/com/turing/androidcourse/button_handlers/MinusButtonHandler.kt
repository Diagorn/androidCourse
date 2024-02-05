package com.turing.androidcourse.button_handlers

import android.content.Context
import android.widget.EditText
import android.widget.TextView

/**
 * Обработчик события клика на кнопку вычитания
 *
 * @author Diagorn
 */
class MinusButtonHandler(
    editTexts: List<EditText>,
    resultTextView: TextView,
    context: Context,
) : AbstractButtonHandler(editTexts, resultTextView, context) {

    override fun doHandle() {
        operationResult = editTexts[0].text.toString().toDouble()

        for (i in 1 until editTexts.size) {
            operationResult -= editTexts[i].text.toString().toDouble()
        }
    }
}