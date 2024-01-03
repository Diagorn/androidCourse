package com.turing.androidcourse.button_handlers

import android.content.Context
import android.widget.EditText
import android.widget.TextView

/**
 * Обработчик события клика на кнопку деления
 *
 * @author Diagorn
 */
class DivideButtonHandler(
    editTexts: List<EditText>,
    resultTextView: TextView,
    context: Context,
) : AbstractButtonHandler(editTexts, resultTextView, context) {

    override fun doHandle() {
        operationResult = editTexts[0].text.toString().toDouble()

        for (editText in editTexts.subList(1, editTexts.size)) {
            if (editText.text.toString().toDouble() == 0.0) {
                makeToast("Ты бля очкошник на ноль мы не делим (мы школота)")
                return
            }
            operationResult /= editText.text.toString().toDouble()
        }
    }
}