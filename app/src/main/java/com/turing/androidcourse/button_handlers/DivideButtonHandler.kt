package com.turing.androidcourse.button_handlers

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.turing.androidcourse.R

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

        for (i in 1 until editTexts.size) {
            if (editTexts[i].text.toString().toDouble() == 0.0) {
                makeToast(context.getString(R.string.division_error_txt))
                return
            }
            operationResult /= editTexts[i].text.toString().toDouble()
        }
    }
}