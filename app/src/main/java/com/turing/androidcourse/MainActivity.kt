package com.turing.androidcourse

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.turing.androidcourse.button_handlers.AbstractButtonHandler
import com.turing.androidcourse.button_handlers.DivideButtonHandler
import com.turing.androidcourse.button_handlers.MinusButtonHandler
import com.turing.androidcourse.button_handlers.MultiplyButtonHandler
import com.turing.androidcourse.button_handlers.PlusButtonHandler
import com.turing.androidcourse.databinding.ActivityMainBinding

private const val FIRST_EDIT_TEXT_ID = "firstEdit"
private const val SECOND_EDIT_TEXT_ID = "secondEdit"
private const val RESULT_TEXT_ID = "resultText"

/**
 * Главная активити с калькулятором
 *
 * @author Diagorn
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    /**
     * Мапа обработчиков событий кликов
     *
     * Ключ - айдишник кнопки
     * Значение - объект обработчика
     */
    private lateinit var handlersMap: Map<Int, AbstractButtonHandler>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var editTextList = listOf(
            binding.editTextArgument1, binding.editTextArgument2
        )

        handlersMap = mapOf(
            binding.buttonPlus.id to PlusButtonHandler(editTextList, binding.resultTextView, this),
            binding.buttonMinus.id to MinusButtonHandler(editTextList, binding.resultTextView, this),
            binding.buttonMultiply.id to MultiplyButtonHandler(editTextList, binding.resultTextView, this),
            binding.buttonDivide.id to DivideButtonHandler(editTextList, binding.resultTextView, this),
        )

        setOnClickListeners()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putString(FIRST_EDIT_TEXT_ID, binding.editTextArgument1.text.toString())
        outState.putString(SECOND_EDIT_TEXT_ID, binding.editTextArgument2.text.toString())
        outState.putString(RESULT_TEXT_ID, binding.resultTextView.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        binding.editTextArgument1.setText(savedInstanceState.getString(FIRST_EDIT_TEXT_ID))
        binding.editTextArgument2.setText(savedInstanceState.getString(SECOND_EDIT_TEXT_ID))
        binding.resultTextView.text = savedInstanceState.getString(RESULT_TEXT_ID)
    }

    private fun setOnClickListeners() {
        binding.buttonPlus.setOnClickListener {
            handlersMap[binding.buttonPlus.id]?.handle()
        }

        binding.buttonMinus.setOnClickListener {
            handlersMap[binding.buttonMinus.id]?.handle()
        }

        binding.buttonMultiply.setOnClickListener {
            handlersMap[binding.buttonMultiply.id]?.handle()
        }

        binding.buttonDivide.setOnClickListener {
            handlersMap[binding.buttonDivide.id]?.handle()
        }

        binding.aboutButton.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}